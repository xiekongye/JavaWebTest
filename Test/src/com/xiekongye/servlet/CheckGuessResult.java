package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.xiekongye.entity.GuessNumberResponseModel;

public class CheckGuessResult extends HttpServlet {
	
	//private int guessCount = 0;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		int resultNumber = (int)session.getAttribute("ResultNumber");//随机数答案
		int guessNumber = Integer.parseInt(request.getParameter("guessNumber"));//用户猜测的答案
		int guessCount = (int)session.getAttribute("GuessCount");//用户猜的总次数,基于Session
		GuessNumberResponseModel resultModel = new GuessNumberResponseModel();
		guessCount++;
		session.setAttribute("GuessCount", guessCount);
		if(resultNumber == guessNumber){
			//用户答对了
			session.removeAttribute("GuessCount");
			session.invalidate();//会话失效
			resultModel.ResultDesc = "RIGHT";
			resultModel.GuessCount = guessCount;
			//String guessResultStr = "RIGHT";
			out.print(new Gson().toJson(resultModel));
			
			//out.print("RIGHT");
			out.flush();
			out.close();
			//request.getRequestDispatcher("/GuessNumber.jsp");
		}else if (guessNumber < resultNumber) {
			//猜小了
			resultModel.ResultDesc = "SMALL";
			resultModel.GuessCount = guessCount;
			//String guessResultStr = "RIGHT";
			out.print(new Gson().toJson(resultModel));
			//out.print("SMALL");
			out.flush();
			out.close();
		}else {
			//猜大了
			resultModel.ResultDesc = "BIG";
			resultModel.GuessCount = guessCount;
			//String guessResultStr = "RIGHT";
			out.print(new Gson().toJson(resultModel));
			//out.print("BIG");
			out.flush();
			out.close();
		}
		
		
	}

}
