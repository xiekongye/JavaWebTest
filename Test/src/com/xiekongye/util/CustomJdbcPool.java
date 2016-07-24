/**
 * 
 */
package com.xiekongye.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 自定义数据库连接池实现
 * @author xiekongye
 */
public class CustomJdbcPool implements DataSource {
	//数据库连接池
	private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
	private static Log logger = LogFactory.getLog(CustomJdbcPool.class);
	
	static{
		//读取配置文件
		InputStream in = CustomJdbcPool.class.getResourceAsStream("/db.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String username = prop.getProperty("userName");
			String password = prop.getProperty("password");
			int jdbcInitPoolSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
			//加载数据库驱动
			Class.forName(driver);
			
			for(int i = 0;i < jdbcInitPoolSize;i++){
				Connection connection = DriverManager.getConnection(url,username,password);
				logger.debug("获取到了数据库连接:" + connection);
				//添加数据库连接到容器中
				listConnections.add(connection);
			}
		} catch (IOException ioException){
			logger.error("读取配置文件出错", ioException);
		} catch (ClassNotFoundException classNotFoundException) {
			logger.error("加载数据库驱动出错", classNotFoundException);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if(listConnections.size() >= 1){
			final Connection conn = listConnections.removeFirst();
			logger.debug("数据库连接大小：" + listConnections.size());
			/*动态代理技术
			 * proxyConn = (Connection) Proxy.newProxyInstance(this.getClass()
            .getClassLoader(), conn.getClass().getInterfaces(),
            new InvocationHandler() {
        //此处为内部类，当close方法被调用时将conn还回池中,其它方法直接执行
            public Object invoke(Object proxy, Method method,
                      Object[] args) throws Throwable {
                if (method.getName().equals("close")) {
                    pool.addLast(conn);
                    return null;
            }
            return method.invoke(conn, args);
        }
    });
			 * */
			//返回Connection对象的代理对象
			return (Connection)Proxy.newProxyInstance(CustomJdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(), 
					new InvocationHandler() {
						
						@Override
						public Object invoke(Object proxy, Method method, Object[] args)
								throws Throwable {
							if(!method.getName().equals("close")){
								return method.invoke(conn, args);
							}else {
								//如果调用的是Connection对象的close方法，就把conn还给数据库连接池
								listConnections.add(conn);
								logger.debug(conn + "数据库连接对象被返回给了数据库连接池，当前连接池大小为：" + listConnections.size());
								return null;
							}
						}
					});
		}else {
			throw new RuntimeException("对不起，数据库忙");
		}
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
