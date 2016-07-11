/**
 * 
 */
package com.xiekongye.filter;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author xiekongye
 *
 */
public class FilterDemo implements Filter {
	//private成员变量
	private String filterName;
	private ServletContext servletContext;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("FilterDemo Destroy执行...");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			System.out.println("FilterDemo执行前...");
			filterChain.doFilter(request, response);//让目标资源执行，放行
			System.out.println("FilterDemo执行后...");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
			//重定向到Exception.jsp页面
			request.setAttribute("Exception", ex.toString());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ErrorPage/ExceptionPage.jsp");
			//dispatcher.include(request, response);
			dispatcher.forward(request, response);//该方法只能在相应没有被提交的情况下才能调用
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("----过滤器初始化----");
		filterName = filterConfig.getFilterName();
		servletContext = filterConfig.getServletContext();
	}

}
