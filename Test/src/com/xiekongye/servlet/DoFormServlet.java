package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DoFormServlet
 */
@WebServlet("/DoFormServlet")
public class DoFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoFormServlet() {
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
		PrintWriter outWriter = response.getWriter();
		Boolean isRepeat = false;
		isRepeat = isRepeatSubmit(request);
		if(isRepeat){
			outWriter.println("重复提交了");
			return;
		}
		outWriter.println("处理用户请求......删除Token.......");
		request.getSession().removeAttribute("Token");
		
		outWriter.flush();
		outWriter.close();
	}

	/**
	 * @author xiekongye
	 * */
	private Boolean isRepeatSubmit(HttpServletRequest request){
		//客户端存储的Token
		String clientTokenString = request.getParameter("Token");
		if(clientTokenString == null){
			return true;
		}
		
		//服务端的Token，从Session中取
		String serverTokenString = (String) request.getSession().getAttribute("Token");
		if(serverTokenString == null){
			return true;
		}
		
		//客户端与服务端Token不同，则重复提交了表单
		if(!clientTokenString.equals(serverTokenString)){
			return true;
		}
		return false;
	}
}
