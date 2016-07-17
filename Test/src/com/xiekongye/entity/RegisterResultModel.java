/**
 * 
 */
package com.xiekongye.entity;

import java.util.ArrayList;

/**
 * 新用户注册结果模型
 * @author xiekongye
 *
 */
public class RegisterResultModel {
	//是否注册成功
	public boolean IsSuccess;
	//已经存在的用户信息
	public ArrayList<User> ExistUsers;
}
