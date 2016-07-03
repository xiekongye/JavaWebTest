/**
 * 
 */
package com.xiekongye.javabean;

/**
 * @author xiekongye
 *
 */
public class Person {

	//封装的私有属性
	private String name;
	private String sex;
	private int age;
	private Boolean married;
	
	//无参构造方法
	public Person() {
		// TODO Auto-generated constructor stub
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
}
