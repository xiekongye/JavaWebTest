<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户注册</title>
    
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="xiekongye,servlet,demo">
	<meta http-equiv="description" content="用户注册页面">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="javascripts/common/jquery-3.0.0.js"></script>
	<script src="javascripts/common/jquery.validate.js" type="text/javascript"></script>
	<script src="javascripts/register.js"></script>

  </head>
  
  <body>
  	<h2>请输入你的注册信息：</h2>
  	<h4 id="PromptInfo" style="color:red;display:none"></h4>
  	<div id="RegisterForm">
	  	<div>
	   	<label>UserName:</label>
	   	<input type="text" name="userName" id="userName">
	   	</div>
	   	<div>
	   	<label>Password:</label>
	   	<input type="password" name="password" id="password">
	   	</div>
	   	<div>
	   	<label>Email:</label>
	   	<input type="text" id="userEmail" name="userEmail"/>
	   	</div>
	   	<div>
	   	<button id="cancelButton" value="Cancel">Cancel</button>
	   	<button id="submitButton" value="Submit">Submit</button>
	   	</div>
  	</div>
  </body>
</html>
