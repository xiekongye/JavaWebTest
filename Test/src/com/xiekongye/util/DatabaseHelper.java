/**
 * 
 */
package com.xiekongye.util;

import java.sql.*;
import java.text.MessageFormat;

import com.xiekongye.entity.DatabaseType;

/**
 * @author xiekongye
 *
 */
public class DatabaseHelper {

	//static区
	private static String CREATE_TABLE_PATTERN_STR = "create table {0}(" 
			+ "id varchar(10) primary key,"
			+ "password varchar(20) not null,"
			+ "name varchar(50),"
			+ "email varchar(50),"
			+ "birthday date"
			+ ");";
	private static String DATABASE_CONNECTION_STR = 
			"jdbc:mysql://localhost:3306/jdbcStudy?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	
	//private私有数据域
	private String tableName;//表名
	private String createTableStr = MessageFormat.format(CREATE_TABLE_PATTERN_STR,tableName);//建表语句
	
	//构造函数
	public DatabaseHelper(DatabaseType databaseType,String tableName){
		this.tableName = tableName;
		try {
			switch(databaseType){
			case MySql:
				//1.加载MySQL数据库驱动
	            Class.forName("com.mysql.jdbc.Driver");
	            //2.获取与数据库的连接,Client
	            Connection conn = DriverManager.getConnection(DATABASE_CONNECTION_STR,"root","root");
	            //3.获取向数据库发送SQL语句的statement
	            Statement statement = conn.createStatement();
				break;
			case SqlServer:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
