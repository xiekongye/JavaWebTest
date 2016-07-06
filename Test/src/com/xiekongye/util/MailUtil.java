/**
 * 
 */
package com.xiekongye.util;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;


/**
 * @author xiekongye
 *
 */
public class MailUtil {
	
	//private static

	/**
	 * 发送简单的文本邮件
	 * @author xiekongye
	 * @param mailTo 收件人邮箱
	 * @param mailSubject 邮件主题
	 * @param mailContent 邮件内容
	 * @return boolean 邮件是否发送成功
	 * */
	public static boolean sendSimpleEmail(String mailTo,String mailSubject,String mailContent) {

		boolean isSendSuccess = false;
		try{
			//读取配置文件
			Properties prop = new Properties();
			InputStream in = MailUtil.class.getResourceAsStream("/com/javawebtest/conf/mail.properties");
			prop.load(in);
			
			//1.获取Session实例
			Session session = Session.getInstance(prop);
			session.setDebug(true);
			
			//2.使用Session获取Transport对象
			Transport transport = session.getTransport();
			
			//3.连接SMTP服务器
			transport.connect(prop.getProperty("sohu.mail.host"), "xiekongye@sohu.com", "sohu900905ye");
			
			//4.创建邮件对象
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop.getProperty("sohu.mail.from")));
			message.setRecipient(RecipientType.TO, new InternetAddress(mailTo));
			message.setSubject(mailSubject);
			message.setContent(mailContent,"text/html;charset=utf-8");
			
			//5.邮件发送
			transport.sendMessage(message, message.getAllRecipients());
			
			//6.关闭
			transport.close();
			
			isSendSuccess = true;
		}catch(Exception e){
			e.printStackTrace();
			isSendSuccess = false;
		}finally{
		}
		return isSendSuccess;
	}
}
