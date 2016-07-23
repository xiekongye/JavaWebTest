package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.enterprise.inject.New;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiekongye.entity.RegisterResultModel;
import com.xiekongye.entity.User;
import com.xiekongye.util.DbManager;
import com.xiekongye.util.JdbcCRUDByPreparedStatement;
import com.xiekongye.util.JdbcCRUDByStatement;
import com.xiekongye.util.JdbcUtil;

/**
 * Servlet implementation class GetRegisterResult
 */
@WebServlet(description = "????????", urlPatterns = { "/GetRegisterResult" })
public class GetRegisterResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//
	private ServletConfig servletConfig;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRegisterResult() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletConfig = config;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		Enumeration<String> headers = request.getHeaderNames();
		if(headers != null ){
			while(headers.hasMoreElements()){
				String header = (String)headers.nextElement();
				String value = request.getHeader(header);
			}
		}
		Log logger = LogFactory.getLog(GetRegisterResult.class.getName());
		logger.info("log4j:开始获取Request参数");
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		try {
			DbManager client = DbManager.getInstance();
			String databaseName = client.getDatabaseName();
			String databaseVersion = client.getDatabaseVersion();
			RegisterResultModel registerResultModel = new RegisterResultModel();
			PrintWriter out = response.getWriter();
			ArrayList<User> users = client.findUserByName(userName);
			if(users != null && !users.isEmpty()){
				registerResultModel.IsSuccess = false;
				registerResultModel.ExistUsers = users;
				
			}else{
				//用户没有被注册，则注册当前用户，并自动登录到用户详细信息页，且设置Cookie
				User user = new User();
				user.setName(userName);
				user.setPassword(password);
				boolean isInsertSuccess = client.insertUser(user);
				registerResultModel.IsSuccess = isInsertSuccess;
			}
			out.print(new Gson().toJson(registerResultModel));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GetRegisterResult出错，错误信息:" + e.getCause().getMessage(), e);
		}
	}

}
