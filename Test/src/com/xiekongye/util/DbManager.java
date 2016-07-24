/**
 * 
 */
package com.xiekongye.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xiekongye.entity.User;

/**
 * 数据库管理类，单例模式
 * @author xiekongye
 * @version 1.0
 */
public class DbManager {
	//类数据域
	private static Log logger = LogFactory.getLog(DbManager.class);
	private static boolean hasExistInstance = false;//是否已经存在类的实例
	private static String driver = null;//数据库驱动程序名
	private static String url = null;//数据库URL
	private static String username = null;//数据库用户名
	private static String password = null;//数据库用户密码
	
	//实例数据域
	private String guid = null;//实例唯一标识符
	private Connection conn = null;
	private DatabaseMetaData dbMetaData = null;//数据库元数据对象
	
	private static DbManager _instance = null;
	
	//构造函数私有化，不能通过构造函数创建实例
	private DbManager() throws Exception{
		try {
			Connection connection = null;
			InputStream in = DbManager.class.getResourceAsStream("/appsetting.properties");
			Properties prop = new Properties();
			if(!hasExistInstance){
				//生成数据库连接类唯一标识符
				UUID uuid = UUID.randomUUID();
				guid = uuid.toString().replace("-", "");
				prop.load(in);
				//获取配置文件中的数据库连接方式
				String dbPattern = prop.getProperty("dbPattern");
				switch (dbPattern) {
				case "CustomDbPool":
					//自定义数据库连接池方式
					connection = getConnectionByCustomDbPool();
					break;
				case "JDBC":
					//JDBC方式
					connection = getConnectionByJdbc();
					break;
				case "TomcatPool":
					//Tomcat容器的数据库连接池
					connection = getConnectionByDbPool();
					break;
				case "DBCP":
					//DBCP数据库连接池方式
					connection = getConnectionByDBCP();
					break;
				case "C3p0":
					//C3P0数据库连接池方式
					connection = getConnectionByC3P0();
					break;
				default:
					connection = null;
					break;
				}
				this.conn = connection;
				//获取数据库元数据对象
				if(this.conn != null){
					this.dbMetaData = conn.getMetaData();
				}
			}else {
				throw new Exception("已经存在DbManager类的实例");
			}
		} catch (IOException ioException){
			logger.error("创建DbManager实例过程中读取appsetting配置文件出错", ioException);
			throw new Exception("创建DbManager实例过程中读取配置文件出错", ioException);
		} catch (Exception e) {
			throw new Exception("创建DbManager实例过程出错", e);
		}
	}
	
	/**
	 * 通过Tomcat数据库连接池获取数据库连接对象
	 * @author xiekongye
	 * @return 数据库连接对象，无法获取会返回NULL
	 * */
	private Connection getConnectionByDbPool(){
		Connection result = null;
		try {
			//初始化JNDI容器
			Context context = new InitialContext();
			//获取JNDI容器
			Context envContext = (Context) context.lookup("java:comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/sampleDS");
			result = dataSource.getConnection();
		} catch (NamingException ne) {
			logger.error("获取javax.naming.Context对象异常", ne);
		} catch (SQLException se) {
			logger.error("无法获取数据库连接对象", se);
		}
		return result;
	}
	
	/**
	 * 通过JDBC的方式获取数据库连接对象
	 * @author xiekongye
	 * @return 数据库连接对象，获取不到返回NULL
	 * */
	private Connection getConnectionByJdbc() {
		Connection result = null;
		Properties prop = null;
		InputStream in = null;
		try{
			//加载配置文件
			prop = new Properties();
			in = DbManager.class.getResourceAsStream("/com/javawebtest/conf/db.properties");
			prop.load(in);
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("userName");
			password = prop.getProperty("password");
			//1.加载数据库驱动
			Class.forName(driver);
			//2.建立并获取数据库连接
			result = DriverManager.getConnection(url, username, password);
		}catch(IOException ioException){
			logger.error("通过JDBC获取数据库连接时读取配置文件出错", ioException);
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (ClassNotFoundException classNotFoundException) {
			logger.error("通过JDBC获取数据库连接时加载数据库驱动出错", classNotFoundException);
		}catch (SQLException sqlExceptione) {
			logger.error("通过JDBC获取数据库连接时获取数据库连接对象出错", sqlExceptione);
		}finally{
			
		}
		return result;
	}
	
	/**
	 * 通过自定义的数据库连接池获取数据库连接对象
	 * @author xiekongye
	 * @return Connection 数据库连接对象，失败返回为NULL
	 * */
	private Connection getConnectionByCustomDbPool() {
		Connection result = null;
		try {
			result = new CustomJdbcPool().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 通过DBCP方式获取数据库连接
	 * @author xiekongye
	 * @return Connection 数据库连接对象，失败返回为NULL
	 * */
	private Connection getConnectionByDBCP() {
		Connection result = null;
		try {
			//加载DbcpCOnfig配置文件
			InputStream in = DbManager.class.getResourceAsStream("/dbcpconfig.properties");
			Properties prop = new Properties();
			prop.load(in);
			//创建数据源
			DataSource ds = BasicDataSourceFactory.createDataSource(prop);
			result = ds.getConnection();
		} catch (IOException ioException){
			logger.debug("读取dbcpconfig配置文件出错",ioException);
		} catch (SQLException sqlException) {
			logger.debug("通过DBCP获取数据库连接失败", sqlException);
		} catch (Exception e) {
			
		}
		return result;
	}
	
	/**
	 * 通过C3P0方式获取数据库连接
	 * @author xiekongye
	 * @return Connection 数据库连接对象，失败返回为NULL
	 * */
	private Connection getConnectionByC3P0() {
		Connection result = null;
		try {
			DataSource ds = new ComboPooledDataSource("MySql");
			result = ds.getConnection();
		} catch (SQLException sqlException) {
			logger.error("通过C3P0方式获取数据库连接失败", sqlException);
		}
		return result;
	}
	
	/**
	 * 获取当前数据库连接的实例
	 * @author xiekongye
	 * @return static DbManager 唯一的数据库连接实例
	 * */
	public static DbManager getInstance(){
		if(_instance == null){
			synchronized (DbManager.class) {
				if(_instance == null){
					try {
						_instance = new DbManager();
					} catch (Exception e) {
						//构造函数出现异常时返回NULL，因为此时该实例的数据域可能无法使用
						_instance = null;
					}
				}
			}
		}
		return _instance;
	}

	/**
	 * 获取当前数据库连接的唯一标识符
	 * @author xiekongye
	 * @return 当前数据库连接的唯一标识符
	 * */
	public String getGuid() {
		return this.guid;
	}
	
	/**
	 * 获取数据库连接Connection
	 * @author xiekongye
	 * @return 数据库连接
	 * */
	public Connection getConnection() {
		return this.conn;
	}
	
//	public DatabaseMetaData getDBMetaData() {
//		return this.dbMetaData;
//	}
	
	/**
	 * 获取数据库产品名称
	 * @author xiekongye
	 * @return 数据库产品名称
	 * */
	public String getDatabaseName() {
		try {
			return this.dbMetaData.getDatabaseProductName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取数据库版本号
	 * @author xiekongye
	 * @return 数据库版本号
	 * */
	public String getDatabaseVersion() {
		try {
			return this.dbMetaData.getDatabaseProductVersion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据用户ID获取用户列表信息
	 * @author xiekongye
	 * @param userId 用户ID
	 * @return 符合条件的用户信息列表
	 * */
	public ArrayList<User> findUserById(int userId) {
		ArrayList<User> result = new ArrayList<>();
		PreparedStatement pStatement = null;
		try {
			if(userId > 0){
				String findUserByIdSQL = "select * from user where id=?";
				pStatement = conn.prepareStatement(findUserByIdSQL);
				pStatement.setInt(1, userId);
				ResultSet resultSet = pStatement.executeQuery();
				if(resultSet != null){
					while(resultSet.next()){
						User user = new User();
						user.setID(resultSet.getInt("id"));
						user.setName(resultSet.getString("name"));
						user.setPassword(resultSet.getString("password"));
						user.setEmail(resultSet.getString("email"));
						user.setBirthday(resultSet.getDate("birthday"));
						result.add(user);
					}
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			if(pStatement != null){
				try {
					pStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据用户Name获取用户列表信息
	 * @author xiekongye
	 * @param userId 用户Name
	 * @return 符合条件的用户信息列表
	 * */
	public ArrayList<User> findUserByName(String name) {
		ArrayList<User> result = new ArrayList<>();
		PreparedStatement pStatement = null;
		try {
			if(name != null && !name.isEmpty()){
				String findUserByNameSQL = "select * from user where name=?";
				pStatement = conn.prepareStatement(findUserByNameSQL);
				pStatement.setString(1, name);
				ResultSet resultSet = pStatement.executeQuery();
				if(resultSet != null){
					while(resultSet.next()){
						User user = new User();
						user.setID(resultSet.getInt("id"));
						user.setName(resultSet.getString("name"));
						user.setPassword(resultSet.getString("password"));
						user.setEmail(resultSet.getString("email"));
						user.setBirthday(resultSet.getDate("birthday"));
						result.add(user);
					}
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			if(pStatement != null){
				try {
					pStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据用户Email获取用户列表信息
	 * @author xiekongye
	 * @param email 用户Email
	 * @return 符合条件的用户列表
	 * */
	public ArrayList<User> findUserByEmail(String email) {
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement pStatement = null;
		try {
			String findUserByEmailSql = "select * from user where email=?";
			pStatement = conn.prepareStatement(findUserByEmailSql);
			pStatement.setString(1, email);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet != null){
				while(resultSet.next()){
					User user = new User();
					user.setID(resultSet.getInt("id"));
					user.setEmail(resultSet.getString("email"));
					user.setPassword(resultSet.getString("password"));
					user.setBirthday(resultSet.getDate("birthday"));
					result.add(user);
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 插入新的用户信息
	 * @author xiekongye
	 * @param user 用户信息实体
	 * @return 是否插入成功
	 * */
	public boolean insertUser(User user) {
		boolean isInsertSuccess = false;
		PreparedStatement pStatement = null;
		try {
			ArrayList<User> existUsers = findUserByName(user.getName());
			if(existUsers == null || existUsers.isEmpty()){
				String insertUserSql = "insert into user(password,name,email,birthday,inserttime) values(?,?,?,?,?)";
				pStatement = conn.prepareStatement(insertUserSql);
				pStatement.setString(1, user.getPassword());
				pStatement.setString(2, user.getName());
				pStatement.setString(3, user.getEmail());
				pStatement.setDate(4, user.getBirthday());
				pStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				int num = pStatement.executeUpdate();
				if(num > 0){
					isInsertSuccess = true;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isInsertSuccess;
	}
	
	/**
	 * 更新指定ID的用户信息
	 * @author xiekongye
	 * @param id 指定用户ID
	 * @return 是否更新成功
	 * */
	public boolean updateUserById(int id) {
		boolean isSuccess = false;
		PreparedStatement pStatement = null;
		try {
			ArrayList<User> existUsers = findUserById(id);
			if(existUsers != null && !existUsers.isEmpty()){
				User existUser = existUsers.get(0);
				String updateUserByIdSql = "update user set password=?,name=?,email=?,birthday=?,lastmodifytime=? where id=" + id;
				pStatement = conn.prepareStatement(updateUserByIdSql);
				pStatement.setString(1, existUser.getPassword());
				pStatement.setString(2, existUser.getName());
				pStatement.setString(3, existUser.getEmail());
				pStatement.setDate(4, existUser.getBirthday());
				pStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				int num = pStatement.executeUpdate();
				if(num > 0){
					isSuccess = true;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 更新指定Name的用户信息
	 * @author xiekongye
	 * @param name 指定用户Name
	 * @return 是否更新成功
	 * */
	public boolean updateUserByName(String name) {
		boolean isSuccess = false;
		PreparedStatement pStatement = null;
		try {
			ArrayList<User> existUsers = findUserByName(name);
			if(existUsers != null && !existUsers.isEmpty()){
				User existUser = existUsers.get(0);
				String updateUserByIdSql = "update user set password=?,email=?,birthday=?,lastmodifytime=? where name=" + name;
				pStatement = conn.prepareStatement(updateUserByIdSql);
				pStatement.setString(1, existUser.getPassword());
				pStatement.setString(2, existUser.getEmail());
				pStatement.setDate(3, existUser.getBirthday());
				pStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				int num = pStatement.executeUpdate();
				if(num > 0){
					isSuccess = true;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 更新指定Name的用户信息
	 * @author xiekongye
	 * @param name 指定用户Name
	 * @return 是否更新成功
	 * */
	public boolean updateUserByEmail(String email) {
		boolean isSuccess = false;
		PreparedStatement pStatement = null;
		try {
			ArrayList<User> existUsers = findUserByEmail(email);
			if(existUsers != null && !existUsers.isEmpty()){
				User existUser = existUsers.get(0);
				String updateUserByIdSql = "update user set password=?,name=?,birthday=?,lastmodifytime=? where email=" + email;
				pStatement = conn.prepareStatement(updateUserByIdSql);
				pStatement.setString(1, existUser.getPassword());
				pStatement.setString(2, existUser.getName());
				pStatement.setDate(3, existUser.getBirthday());
				pStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				int num = pStatement.executeUpdate();
				if(num > 0){
					isSuccess = true;
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 释放数据库连接资源
	 * @author xiekongye
	 * */
	public void releaseResource() {
		try {
			if(this.conn != null){
				conn.close();
			}
		} catch (SQLException sqlException) {
			logger.error("关闭数据库连接出错", sqlException);
		} finally{
			
		}
		
	}
}
