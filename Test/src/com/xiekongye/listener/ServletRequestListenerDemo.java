/**
 * 
 */
package com.xiekongye.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @author xiekongye
 *
 */
public class ServletRequestListenerDemo implements ServletRequestListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// TODO Auto-generated method stub
		System.out.println("ServletRequest Destoryed...Servlet Request event object on which this event occured" + event.getSource());
		System.out.println("ServletRequest Destoryed...Servlet Request information:" + event.getServletRequest());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestInitialized(ServletRequestEvent event) {
		// TODO Auto-generated method stub
		System.out.println("ServletRequest Initialized...Servlet Request event object on which this event occured" + event.getSource());
		System.out.println("ServletRequest Initialized...Servlet Request information:" + event.getServletRequest());
	}

}
