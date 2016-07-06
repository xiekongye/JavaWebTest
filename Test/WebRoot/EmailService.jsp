<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>邮件发送</title>

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="javascripts/common/jquery-3.0.0.js"></script>
	<script src="javascripts/common/jquery.validate.js"></script>
	<script src="javascripts/sendEmail.js"></script>
  </head>
  
  <body>
  	<h2>发送简单文本文件</h2>
    <!-- <div>
    <label>邮件发件人：</label>
    <input type="text" id="mailFrom" name="mailFrom"/>
    </div> -->
    <div>
    <label>邮件收件人：</label>
    <input type="text" id="mailTo" name="mailTo"/>
    </div>
    <div>
    <label>邮件标题：</label>
    <input type="text" id="mailSubject"/>
    </div>
    <div>
    <label>邮件内容：</label>
    <input type="text" id="mailContent"/>
    </div>
    <div>
    <Button id="btnSendSimlpeEmail" name="btnSendSimlpeEmail" value="Send">Send</Button>
    </div>
  </body>
</html>
