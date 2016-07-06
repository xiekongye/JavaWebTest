package com.xiekongye.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xiekongye.util.MailUtil;

/**
 * Servlet implementation class SendEmailServlet
 */
@WebServlet(description = "????", urlPatterns = { "/SendEmailServlet" })
public class SendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmailServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//String mailFrom = request.getParameter("mailFrom");
		String mailTo = request.getParameter("mailTo");
		String mailSubject = request.getParameter("mailSubject");
		String mailContent = request.getParameter("mailContent");
		Boolean isSendSuccess = MailUtil.sendSimpleEmail(mailTo, mailSubject, mailContent);
		PrintWriter out = response.getWriter();
		out.print(new Gson().toJson(isSendSuccess));
		out.flush();
		out.close();
	}

}
