<%@page import="java.util.logging.ConsoleHandler"%>
<%@page import="java.io.Console"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Exception错误页面</title>

	<meta http-equiv="ContentType" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="Exception错误页面">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    对不起，网页出错了！ <br>
    <h2 style="color:greed;">错误信息:</h2><br>
    <%
    	String exceptionString = (String)request.getAttribute("Exception"); 
    %>
    <%= exceptionString %>
  </body>
</html>
