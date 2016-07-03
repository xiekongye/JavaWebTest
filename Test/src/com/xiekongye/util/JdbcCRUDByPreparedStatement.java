/**
 * 
 */
package com.xiekongye.util;

import java.sql.*;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

import com.xiekongye.entity.User;

/**
 * @author xiekongye
 *
 */
public class JdbcCRUDByPreparedStatement {

	//查找
	public ArrayList<User> findUser(User user){

		Connection connection = null;
		PreparedStatement pStatement = null;

		if(user != null){

			ArrayList<User> users = new ArrayList<User>();
			try {
				if(user.getID() > 0){
					connection = JdbcUtil.getConnection();
					//statement = connection.createStatement();
					//String findUserByIDString = MessageFormat.format("select * from user where id={0}", user.getID());
					String findUserByIDString = "select * from user where id=?";
					pStatement = connection.prepareStatement(findUserByIDString);
					pStatement.setInt(1, user.getID());
					ResultSet resultSet = pStatement.executeQuery();
					
					//ResultSet rSet = statement.executeQuery(findUserByIDString);
					while(resultSet.next()){
						User userTmp = new User();
						userTmp.setID((int)resultSet.getObject("id"));
						userTmp.setPassword((String)resultSet.getObject("password"));
						userTmp.setName((String)resultSet.getObject("name"));
						userTmp.setEmail((String)resultSet.getObject("email"));
						userTmp.setBirthday(Date.valueOf((String)resultSet.getObject("birthday")));
						users.add(userTmp);
					}
				}else if(user.getName() != null){
					connection = JdbcUtil.getConnection();
					String findUserByNameString = "select * from user where name=?";
					pStatement = connection.prepareStatement(findUserByNameString);
					pStatement.setString(1, user.getName());
					ResultSet rSet = pStatement.executeQuery();
					while(rSet.next()){
						System.out.println("查询到了数据!");
						User userTmp = new User();
						userTmp.setID((int)rSet.getObject("id"));
						userTmp.setPassword((String)rSet.getObject("password"));
						userTmp.setName((String)rSet.getObject("name"));
						userTmp.setEmail((String)rSet.getObject("email"));
						//userTmp.setBirthday(Date.valueOf((String)rSet.getObject("birthday")));
						users.add(userTmp);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return users;
		}
		return null;
	}
	
	//插入
	public void insertUser(User user){

		Connection connection = null;
		//Statement statement = null;
		PreparedStatement pStatement = null;
		
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				//statement = connection.createStatement();
				
				String insertUserSqlString = "insert into user(id,password,name,email,birthday) values(?,?,?,?,?)";
				pStatement = connection.prepareStatement(insertUserSqlString);
				pStatement.setInt(1, user.getID());
				pStatement.setString(2, user.getName());
				pStatement.setString(3, user.getName());
				pStatement.setString(4, user.getEmail());
				pStatement.setDate(5, user.getBirthday());
				int num = pStatement.executeUpdate();
				if(num > 0){
					System.out.println("插入成功");
				}else {
					System.out.println("插入失败");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//删除
	public void deleteUser(User user){
		
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement pStatement = null;
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				//statement = connection.createStatement();
				if(user.getID() >= 0){
					String deleteUserByIDString = "delete from user where id=?";
					pStatement = connection.prepareStatement(deleteUserByIDString);
					pStatement.setInt(1, user.getID());
					int num = pStatement.executeUpdate();
					//int num = statement.executeUpdate(deleteUserByIDString);
					if(num > 0){
						System.out.println("删除id为"+ user.getID() + "的用户成功！");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//更新
	public void updateUser(User user) throws SQLException {
		
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement pStatement = null;
		
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				//statement = connection.createStatement();
				if(user.getID() >= 0){
					String updateUserByIDString = "update user set password=?,name=?,email=?,birthday=?";
					pStatement = connection.prepareStatement(updateUserByIDString);
					pStatement.setString(1, user.getPassword());
					pStatement.setString(2, user.getName());
					pStatement.setString(3, user.getEmail());
					pStatement.setDate(4, user.getBirthday());
					int num = pStatement.executeUpdate();
					//int num = statement.executeUpdate(updateUserByIDString);
					if(num > 0){
						System.out.println("插入id为"+ user.getID() + "的用户成功");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
