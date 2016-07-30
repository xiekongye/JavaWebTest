/**
 * 
 */
package com.xiekongye.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.xiekongye.job.MyTimerTask;

/**
 * 实现统计在线人数，定时销毁Session的功能
 * @author xiekongye
 */
public class MyCustomListener implements HttpSessionListener,
		ServletContextListener {
	//HttpSession集合
	private Set<HttpSession> sessionSet = new HashSet<>();
	//锁
	private Object locker = new Object();

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Web应用销毁");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Servlet Context initialized!");
		//创建定时器job
		Timer job = new Timer();
		job.schedule(new MyTimerTask(sessionSet,locker,"定期销毁过期的Session"), 1000*5,1000*5);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		if(event != null){
			synchronized (locker) {
				ServletContext context = event.getSession().getServletContext();
				Integer onlineCount = (Integer)context.getAttribute("OnlineCount");//网站在线人数
				if(onlineCount == null){
					context.setAttribute("Onlinecount", 1);
				}else {
					onlineCount++;
					context.setAttribute("Onlinecount", onlineCount);
				}
				sessionSet.add((HttpSession)event.getSession());
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		if(event != null){
			synchronized (locker) {
				ServletContext context = event.getSession().getServletContext();
				Integer onlineCount = (Integer)context.getAttribute("OnlineCount");//网站在线人数
				if(onlineCount == null){
					context.setAttribute("Onlinecount", 0);
				}else{
					onlineCount--;
					if(onlineCount < 0)onlineCount = 0;
					context.setAttribute("Onlinecount", onlineCount);
				}
			}
		}

	}

}
