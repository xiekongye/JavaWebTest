<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>JavaBean测试页面</title>

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
  <form action="/Test/servlet/Customer" method="post">
  	<table>
  		<tr><td>客户名：</td><td><input type="text" name="custName"/></td></tr>
  		<tr><td>电子邮件：</td><td><input type="text" name="email"/></td></tr>
  		<tr><td>电话：</td><td><input type="text" name="phone"/></td></tr>
  		<tr><td><input type="submit" value="确定"/></td>
  		<td><input type="reset" value="重置"/></td></tr>
  	</table>
  </form>
  </body>
</html>
