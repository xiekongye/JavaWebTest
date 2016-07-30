/**
 * 
 */
package com.xiekongye.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author xiekongye
 *
 */
public class ServletContextListenerDemo implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("ServletContext Destroyed ! ServletContext information:"+sce.getSource());
		System.out.println(sce.getServletContext());
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("ServletContext Initialized ! ServletContext information:" + sce.getSource());
	}

}
