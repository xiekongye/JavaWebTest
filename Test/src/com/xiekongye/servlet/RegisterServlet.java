package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiekongye.util.TokenProcessor;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(description = "??????", urlPatterns = { "/servlet/RegisterServlet" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//MySql数据库连接串
	private static String DATABASE_CONNECTION_STR = 
			"jdbc:mysql://localhost:3306/jdbcStudy?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	//数据库查询串，查询当前所有用户
	private static String QUERY_USER_STR = "select * from user";
	private String userName;
	private String password;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
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
		//request为JSP页面，页面采用UTF-8编码方式
		//String encodeStyleString = request.getCharacterEncoding();
		//设置Request的编码方式，因为客户端使用的是UTF-8编码，这里使用同样的编码方式才能在读取客户端传递的参数时不是乱码
		request.setCharacterEncoding("UTF-8");
		//设置Response的编码方式，客户端只有使用相同的编码方式才能获取到不是乱码的服务端输出参数
		response.setContentType("text/html;charset=UTF-8");
		/*
		 * getWriter()与getOutputStream()只能使用其一
		 * */
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.println("远端客户端IP地址:" + request.getRemoteAddr());
		
		//String reqJsonString = JSONObject.valueToString(request);
		//Logger.getGlobal().log(Level.INFO, reqJsonString);
		
		userName = request.getParameter("userName");
		password = request.getParameter("password");
		
		outPrintWriter.println("UserName:" + userName);
		outPrintWriter.println("Password:" + password);
		outPrintWriter.println("Session,UserName:" + request.getSession().getAttribute("userName"));
		outPrintWriter.println("Session,Password:" + request.getSession().getAttribute("password"));

		if(userName != null && password != null){
			if(userName.equals("谢孔叶") && password.equals("900905")){
				//以下两种方式都可以
				//getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
		//Cookie设置
		//cookie跟随Request一同发送给服务器
		Cookie[] cookies = request.getCookies();//获取Request请求中的cookie
		if(cookies != null){
			for(int i = 0;i<cookies.length;i++){
				if(cookies[i].getName().equals("lastAccessTime")){
					Long lastAccessTime = Long.parseLong(cookies[i].getValue());
					outPrintWriter.println("你上次的访问时间是:");
					outPrintWriter.println(new Date(lastAccessTime));
				}
			}
		}else{
			outPrintWriter.println(request.getRemoteUser());
			outPrintWriter.println(",这是你第一次访问本网站");
		}
		Cookie cookie = new Cookie("lastAccessTime", String.valueOf(System.currentTimeMillis()));
		cookie.setComment("Record the last time to access this website");
		cookie.setMaxAge(120);//过期时间
		response.addCookie(cookie);
		
		//Session设置
		//一次会话中有效，一次会话指浏览器打开到浏览器完全退出为止
		HttpSession session = request.getSession();
		String tokenString = TokenProcessor.getInstance().makeToken();//获取Token
		session.setAttribute("Token", tokenString);
		session.setAttribute("userName", userName);
		session.setAttribute("password", password);
		String sessionIdString = session.getId();
		if(session.isNew()){
			outPrintWriter.println("创建新的Session,SessionId = " + sessionIdString);
		}else {
			outPrintWriter.println("已有Session，SessionId = " + sessionIdString);
		}
		
		if(userName.equals("xiekongye")){
			outPrintWriter.println("UserName为:" + userName + ",session立即失效，请刷新查看！");
			session.invalidate();
		}
		
		outPrintWriter.flush();
		outPrintWriter.close();
		
		//是否已经存在用户
		Boolean isExistUser = false;
		//1.加载MySQL数据库驱动
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        //2.获取与数据库的连接,Client
	        Connection conn = DriverManager.getConnection(DATABASE_CONNECTION_STR,"root","root");
	        //3.获取向数据库发送SQL语句的statement
	        Statement statement = conn.createStatement();
	        ResultSet resSet = statement.executeQuery(QUERY_USER_STR);
	        while(resSet.next()){
	        	String exitsUserName  = (String)resSet.getObject("name");
	        	if(exitsUserName.equals(userName)){
	        		isExistUser = true;
	        		break;
	        	}
	        }
	        if(isExistUser){
	        	//已经存在相同用户名的注册用户
	        	
	        }else {
				//不存在与输入用户名一样的用户
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
