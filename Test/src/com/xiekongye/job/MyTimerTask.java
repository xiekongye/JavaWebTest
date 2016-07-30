/**
 * 
 */
package com.xiekongye.job;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.xiekongye.listener.MyCustomListener;

/**
 * 定时销毁创建时间超过一定范围的Session
 * @author xiekongye
 *
 */
public class MyTimerTask extends TimerTask {
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
	public MyTimerTask(String taskName) {
		this.locker = MyCustomListener.getLocker();
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
		System.out.println("销毁过期Session定时任务启动");
		System.out.println("任务名：" + this.taskName + ",任务创建时间:" + new Date(this.createdTime) + ",任务唯一标识符:" + this.uuid);
		
			if(MyCustomListener.getHttpSessions() != null && MyCustomListener.getHttpSessions().size() >= 1){//加入判断，不然出现异常
				Iterator<HttpSession> iterator = MyCustomListener.getHttpSessions().iterator();
				while(iterator.hasNext()){//HashSet只能使用迭代器操作，否则会出现ConcurrentException异常
					if(System.currentTimeMillis() - iterator.next().getLastAccessedTime() > 1000*30){
						iterator.next().invalidate();
						iterator.remove();//使用迭代器删除
					}
				}
			}
		
	}

}
