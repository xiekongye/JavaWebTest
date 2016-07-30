/**
 * 
 */
package com.xiekongye.job;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpSession;

/**
 * 定时销毁创建时间超过一定范围的Session
 * @author xiekongye
 *
 */
public class MyTimerTask extends TimerTask {
	//Session集合
	private Set<HttpSession> sessionSet = new HashSet<>();
	//Lock
	private Object locker;
	//private
	private String uuid;
	private long createdTime;
	private String taskName;
	/**
	 * 构造函数
	 * @author xiekongye
	 * @param sessionSet Session集合
	 * @param locker 锁
	 * */
	public MyTimerTask(Set<HttpSession> sessionSet,Object locker,String taskName) {
		this.sessionSet = sessionSet;
		this.locker = locker;
		this.taskName = taskName;
		this.uuid = UUID.randomUUID().toString();
		this.createdTime = System.currentTimeMillis();
	}

	/**
	 * 销毁过期的HttpSession
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("销毁过期Session定时任务启动");
		System.out.println("任务名：" + this.taskName + ",任务创建时间:" + new Date(this.createdTime) + ",任务唯一标识符:" + this.uuid);
		synchronized (locker) {
			for (HttpSession httpSession : sessionSet) {
				if((System.currentTimeMillis() - httpSession.getLastAccessedTime()) > 1000*30){
					httpSession.invalidate();
					sessionSet.remove(httpSession);
				}
			}
		}
	}

}
