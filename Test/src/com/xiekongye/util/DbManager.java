/**
 * 
 */
package com.xiekongye.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import com.xiekongye.entity.User;

/**
 * 数据库管理类，单例模式
 * @author xiekongye
 * @version 1.0
 */
public class DbManager {
	//private数据域
	private static String guid = null;//唯一标识符
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static Connection conn = null;
	
	private static DbManager _instance = null;
	
	//构造函数私有化，不能通过构造函数创建实例
	private DbManager(){
		try {
			//生成数据库连接类唯一标识符
			UUID uuid = UUID.randomUUID();
			guid = uuid.toString().replace("-", "");
			//加载配置文件
			Properties prop = new Properties();
			InputStream in = DbManager.class.getResourceAsStream("/com/javawebtest/conf/db.properties");
			prop.load(in);
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("userName");
			password = prop.getProperty("password");
			//1.加载数据库驱动
			Class.forName(driver);
			//2.建立并获取数据库连接
			conn = DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前数据库连接的实例
	 * @author xiekongye
	 * @return static DbManager 唯一的数据库连接实例
	 * */
	public static DbManager getInstance() {
		if(_instance == null){
			synchronized (DbManager.class) {
				if(_instance == null){
					_instance = new DbManager();
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
		return guid;
	}
	
	/**
	 * 获取数据库连接Connection
	 * @author xiekongye
	 * @return 数据库连接
	 * */
	public Connection getConnection() {
		return conn;
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
}
