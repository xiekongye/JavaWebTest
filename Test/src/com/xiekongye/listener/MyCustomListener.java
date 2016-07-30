/**
 * 
 */
package com.xiekongye.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
//	private static Set<HttpSession> sessionSet = new HashSet<>();
	private static ArrayList<HttpSession> sessions = new ArrayList<HttpSession>();
	//锁
	private static Object locker = new Object();

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
		job.schedule(new MyTimerTask("定期销毁过期的Session"), 1000*5,1000*5);
	}
	
	/**
	 * 获取应用的所有HttpSession
	 * @author xiekongye
	 * @return 所有的httpSession集合
	 * */
//	public static Set<HttpSession> getHttpSessions() {
//		return sessionSet;
//	}
	
	
	public static ArrayList<HttpSession> getHttpSessions() {
		return sessions;
	}

	/**
	 * 获取当前监听器的锁
	 * @author xiekongye
	 * @return 当前监听器维护的锁
	 * */
	public static Object getLocker() {
		return locker;
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
					context.setAttribute("OnlineCount", 1);
				}else {
					onlineCount++;
					context.setAttribute("OnlineCount", onlineCount);
				}
//				sessionSet.add((HttpSession)event.getSession());
				sessions.add((HttpSession)event.getSession());
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
					context.setAttribute("OnlineCount", 0);
				}else{
					onlineCount--;
					if(onlineCount < 0)onlineCount = 0;
					context.setAttribute("OnlineCount", onlineCount);
				}
//				Iterator<HttpSession> iterator = sessionSet.iterator();
//				while(iterator.hasNext() && iterator.next() == event.getSession()){
//					iterator.remove();
//				}
				
				for(HttpSession session : sessions){
					if(session == event.getSession()){
						synchronized (locker) {
							sessions.remove(session);
						}
					}
				}
			}
		}
	}
}
