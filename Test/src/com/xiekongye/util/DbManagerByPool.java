/**
 * 
 */
package com.xiekongye.util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xiekongye
 *
 */
public class DbManagerByPool {
	//类private数据域
	private static DbManagerByPool _instance = null;
	private static Boolean hasExistInstance = false;
	private static Object locker = new Object();
	
	//实例数据域
	private String uuid = "";
	private DataSource dataSource = null;
	private Connection conn = null;
	private Log logger = null;
	
	private DbManagerByPool() throws Exception{
		this.logger = LogFactory.getLog(DbManagerByPool.class);
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("jdbc:comp/env/jdbc/samplesDS");
			this.conn = dataSource.getConnection();
		} catch (NamingException ne) {
			// TODO: handle exception
			logger.error("获取数据库连接失败", ne);
		}
	}
	
	/**
	 * 获取数据库连接的实例
	 * @author xiekongye
	 * @return DbManagerByPool类的实例
	 * @throws Exception
	 * */
	private static DbManagerByPool getInstance() throws Exception{
		if(!hasExistInstance){
			if(_instance == null){
				synchronized (locker) {
					if(_instance == null){
						try {
							_instance = new DbManagerByPool();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							throw new Exception(e.getCause().getMessage());
						}
					}
				}
			}
		}else {
			throw new Exception("已经存在一个DbManagerByPool的实例，无法创建第二个");
		}
		return _instance;
	}
}
