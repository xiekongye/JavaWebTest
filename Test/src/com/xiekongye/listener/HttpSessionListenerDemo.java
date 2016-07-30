/**
 * 
 */
package com.xiekongye.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author xiekongye
 *
 */
public class HttpSessionListenerDemo implements HttpSessionListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// TODO Auto-generated method stub
		System.out.println("HttpSession Created ! HttpSession information:" + httpSessionEvent.getSource());
		System.out.println("EventSource information:" + httpSessionEvent.getSource());
		System.out.println("Event HttpSession information:" + httpSessionEvent.getSession());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		// TODO Auto-generated method stub
		System.out.println("HttpSession Destroyed ! HttpSession information:" + httpSessionEvent.getSource());
	}

}
