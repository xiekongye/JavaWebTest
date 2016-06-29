package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet2
 */
@WebServlet(description = "??ServletContext", urlPatterns = { "/Servlet2" })
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		this.config = config;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		ServletContext context = this.config.getServletContext();
		String value = (String)context.getAttribute("data");
		
		//全局初始化配置参数
		Enumeration<String> globalInitNamesEnumeration = context.getInitParameterNames();
		String authorNameString = (String)context.getInitParameter("Author");
		String appDescString = (String)context.getInitParameter("AppDesc");
		while (globalInitNamesEnumeration.hasMoreElements()) {
			String initNameString = (String) globalInitNamesEnumeration.nextElement();
			out.println("全局初始化参数:" + initNameString + ",值:" + context.getInitParameter(initNameString));
		}
		out.println("接收到的参数为：" + value);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
