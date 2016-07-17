/**
 * 
 */
package com.xiekongye.entity;

import java.sql.Date;


/**
 * 用户信息
 * @author xiekongye
 */
public class User {

	private int id;
	private String password;
	private String name;
	private String email;
	private Date birthday;
	
	public int getID(){
		return this.id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
