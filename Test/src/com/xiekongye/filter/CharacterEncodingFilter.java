package com.xiekongye.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
@WebFilter(
		description = "????????????????", 
		urlPatterns = { "/CharacterEncodingFilter" }, 
		initParams = { 
				@WebInitParam(name = "charset", value = "utf-8", description = "????")
		})
/**
 * 代码需要重构，各个Filter使用一个共同的基类
 * */
public class CharacterEncodingFilter implements Filter {
	
	//private域
	private FilterConfig filterConfig = null;
	private String defaultCharset = "UTF-8";

    /**
     * Default constructor. 
     */
    public CharacterEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		try {
			// pass the request along the filter chain
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			//HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			String charset = filterConfig.getInitParameter("charset");
			if(charset == null){
				charset = this.defaultCharset;
			}
			request.setCharacterEncoding(charset);
			response.setCharacterEncoding(charset);
			response.setContentType("text/html;charset=" + charset);
			
			MyCharacterEncodingRequest requestWrapper = new MyCharacterEncodingRequest(httpServletRequest);
			chain.doFilter(requestWrapper, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
			//重定向到Exception.jsp页面
			request.setAttribute("Exception", e.toString());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ErrorPage/ExceptionPage.jsp");
			//dispatcher.include(request, response);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = fConfig;
	}

}
