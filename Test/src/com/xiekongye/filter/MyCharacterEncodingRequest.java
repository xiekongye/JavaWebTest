/**
 * 
 */
package com.xiekongye.filter;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author xiekongye
 *
 */
public class MyCharacterEncodingRequest extends HttpServletRequestWrapper {
	
	//request对象是需要被增强的对象
	private HttpServletRequest request = null;

	public MyCharacterEncodingRequest(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		this.request = request;
	}

	//需要被增强的方法
	@Override
	public String getParameter(String name){
		try {
			String value = this.request.getParameter(name);
			if(value == null){
				return null;
			}
			if(!request.getMethod().equalsIgnoreCase("GET")){
				return value;
			}else {
				value = new String(value.getBytes("ISO8859-1"), this.request.getCharacterEncoding());
				return value;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
