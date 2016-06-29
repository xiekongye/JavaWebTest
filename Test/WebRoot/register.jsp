<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'register.jsp' starting page</title>
    
    <meta http-equiv="Content-Types" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="xiekongye,servlet,demo">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="javascripts/common/jquery-3.0.0.js"></script>
	<script src="javascripts/register.js"></script>

  </head>
  
  <body>
  	Please enter your UserName and Password
    <form action="${pageContext.request.contextPath }/servlet/RegisterServlet" method="get">
    	<div>
    	UserName:<input type="text" name="userName" id="userName">
    	</div>
    	<div>
    	Password:<input type="password" name="password" id="password">
    	</div>
    	<div>
    	<input type="reset" value="Cancel" name="cancel" id="cancelButton">
    	<input type="submit" value="Submit" name="submit" id="submitButton">
    	</div>
    </form>
  </body>
  <script type="text/javascript" src="/register.js">
  </script>
</html>
