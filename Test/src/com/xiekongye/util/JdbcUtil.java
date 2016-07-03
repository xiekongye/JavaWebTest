/**
 * 
 */
package com.xiekongye.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Properties;

import com.xiekongye.entity.User;

/**
 * @author xiekongye
 *
 */
public class JdbcUtil {

	//数据库连接相关
	private static String driverString = null;
	private static String urlString = null;
	private static String userNameString = null;
	private static String passwordString = null;
	
	static{
		try {
			//读取配置文件
			InputStream in = JdbcUtil.class.getResourceAsStream("/com/javawebtest/conf/db.properties");
			Properties properties = new Properties();
			properties.load(in);
			
			driverString = properties.getProperty("driver");
			urlString = properties.getProperty("url");
			userNameString = properties.getProperty("userName");
			passwordString = properties.getProperty("password");
			
			Class.forName(driverString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	//获取数据库连接
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(urlString, userNameString, passwordString);
	}
	
	//释放资源
	public static void releaseResource(Connection connection,Statement statement,ResultSet resultSet) {
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				connection = null;
			}
		}
		if(statement != null){
			try {
				statement.close();
			} catch (Exception e) {
				// TODO: handle exception
			} finally{
				statement = null;
			}
		}
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				resultSet = null;
			}
		}
	}
}
