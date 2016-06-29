<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP '404Error.jsp' starting page</title>

	<meta http-equiv="ContentType" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/index.jsp">
	<meta http-equiv="Window-target" content="_top">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h2>404错误页</h2><br>
    3秒钟自动跳转到首页，如果没有自动跳转，点击<a href="${pageContext.request.contextPath }/index.jsp">这里</a>
  </body>
</html>
