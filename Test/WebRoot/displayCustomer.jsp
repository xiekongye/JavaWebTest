<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>JavaBean测试</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%@include file="JspFragments/head.jspf" %>
    <jsp:useBean id="customer" type="com.xiekongye.javabean.Person" scope="session">
    <!-- 下面的代码为customer的所有属性赋值，所以request中的参数名必须与Bean中的名相匹配 -->
    	<jsp:setProperty name="customer" property="*"></jsp:setProperty>
    </jsp:useBean>
    <h4>The customer information:</h4>
    <table>
    	<tr>
    		<td>客户名：</td>
    		<td><jsp:getProperty name="customer" property="name"/></td>
    	</tr>
    	<tr>
    		<td>Email：</td>
    		<td><jsp:getProperty name="customer" property="email"/></td>
    	</tr>
    	<tr>
    		<td>Phone：</td>
    		<td><jsp:getProperty name="customer" property="phone"/></td>
    	</tr>
    </table>
    <%@include file="JspFragments/foot.jspf" %>
  </body>
</html>
