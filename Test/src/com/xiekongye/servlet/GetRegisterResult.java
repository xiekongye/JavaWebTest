package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiekongye.entity.User;
import com.xiekongye.util.JdbcCRUDByPreparedStatement;
import com.xiekongye.util.JdbcCRUDByStatement;
import com.xiekongye.util.JdbcUtil;

/**
 * Servlet implementation class GetRegisterResult
 */
@WebServlet(description = "????????", urlPatterns = { "/GetRegisterResult" })
public class GetRegisterResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRegisterResult() {
        super();
        // TODO Auto-generated constructor stub
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
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		//是否已经存在用户
		Boolean isExistUser = false;
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		try {
			ArrayList<User> users = new JdbcCRUDByPreparedStatement().findUser(user);
			if(users != null && users.size() >= 1){
				isExistUser = true;
			}else {
				new JdbcCRUDByPreparedStatement().insertUser(user);
			}
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			String jsonResult = gson.toJson(users);
			out.println(jsonResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			
		}
	}

}
