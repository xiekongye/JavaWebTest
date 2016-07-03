/**
 * 
 */
package com.xiekongye.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

import com.xiekongye.entity.User;

/**
 * @author xiekongye
 *
 */
public class JdbcCRUDByStatement {

	//查找
	public ArrayList<User> findUser(User user){

		Connection connection = null;
		Statement statement = null;

		if(user != null){

			ArrayList<User> users = new ArrayList<User>();
			try {
				connection = JdbcUtil.getConnection();
				statement = connection.createStatement();
				if(user.getID() > 0){
					String findUserByIDString = MessageFormat.format("select * from user where id={0}", user.getID());
					ResultSet rSet = statement.executeQuery(findUserByIDString);
					while(rSet.next()){
						User userTmp = new User();
						userTmp.setID((int)rSet.getObject("id"));
						userTmp.setPassword((String)rSet.getObject("password"));
						userTmp.setName((String)rSet.getObject("name"));
						userTmp.setEmail((String)rSet.getObject("email"));
						userTmp.setBirthday(Date.valueOf((String)rSet.getObject("birthday")));
						users.add(userTmp);
					}
				}else if(user.getName() != null){
					String findUserByNameString = MessageFormat.format("select * from user where name=''{0}''", user.getName());
					ResultSet rSet = statement.executeQuery(findUserByNameString);
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
		Statement statement = null;
		
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				statement = connection.createStatement();
				String insertUserSqlString = MessageFormat.format("insert into user(id,password,name,email) values({0},''{1}'',''{2}'',''{3}'')", 
						user.getID(),user.getPassword(),user.getName(),user.getEmail());
				int num = statement.executeUpdate(insertUserSqlString);
				if(num > 0){
					System.out.println("插入成功！");
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
		Statement statement = null;
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				statement = connection.createStatement();
				if(user.getID() >= 0){
					String deleteUserByIDString = MessageFormat.format("delete from user where id={0}", user.getID());
					int num = statement.executeUpdate(deleteUserByIDString);
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
		Statement statement = null;
		
		if(user != null){
			try {
				connection = JdbcUtil.getConnection();
				statement = connection.createStatement();
				if(user.getID() >= 0){
					String updateUserByIDString = MessageFormat.format("update user set password=''{0}'',name=''{1}'',email=''{2}'',birthday=''{3}''",
							user.getPassword(),user.getName(),user.getEmail(),user.getBirthday().toString());
					int num = statement.executeUpdate(updateUserByIDString);
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
