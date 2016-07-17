/**
 * 
 */
package com.xiekongye.javabean;

import java.io.Serializable;

/**
 * @author xiekongye
 *
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7375956968749527837L;
	//封装的私有属性
	private String name;
	private String email;
	private String phone;
	private String sex;
	private int age;
	private Boolean married;
	
	//无参构造方法
	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	public Person(String name,String email,String phone){
		this.name = name;
		this.email=email;
		this.phone=phone;
	}
	
	//getter,setter方法
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Boolean getMarried() {
		return this.married;
	}
	public void setMarried(Boolean married) {
		this.married = married;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
