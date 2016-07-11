<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>猜数游戏</title>

	<meta http-equiv="Content-Type" content="utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="javascripts/common/jquery-3.0.0.js"></script>
	<script src="javascripts/guessnumber.js"></script>
  </head>
  
  <body>
  	<div>
  	<!-- 提示信息 -->
  		<h2 id="PromptInfo"></h2>
  	</div>
  	<div>
  	<!-- 用户猜到最终结果的总次数 -->
  		<label>猜测的次数：</label><h3 id="GuessCount"></h3>
  	</div>
  	<div>
		<input type="text" id="GuessNumber">
		<input type="button" id="Submit" value="查看结果">
  	</div>
  	<%
  		int magic = new Random().nextInt(100) + 1;
  		session.setAttribute("ResultNumber", magic);
  		session.setAttribute("GuessCount", 0);//用户猜对时的总次数
  	 %>
  </body>
</html>
