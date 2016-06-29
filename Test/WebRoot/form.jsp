<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'form.jsp' starting page</title>

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
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
    This is my JSP page. <br>
    <form action="${pageContext.request.contextPath }/servlet/DoFormServlet" method="post">
    	<!-- 存储Token的值 -->
    	<input type="hidden" value="<%=session.getAttribute("Token") %>>"></input>
    	<!-- <input type="hidden" name="token" value="${token }"/> -->
    	用户名<input type="text" name="userName"></input>
    	<input type="submit" value="提交"/>
    </form>
  </body>
</html>
