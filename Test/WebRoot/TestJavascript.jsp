<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'TestJavascript.jsp' starting page</title>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/test.css" rel="stylesheet" type="text/css" href="styles.css">
	<script src="javascripts/common/jquery-3.0.0.js"></script>
	<script src="javascripts/test.js"></script>
  </head>
  <jsp:useBean id="person" class="com.xiekongye.javabean.Person" scope="page"/>
  <%
  	person.setName("xiekongye");
  	person.setAge(27);
   %>
  <body>
  <%
  pageContext.setAttribute("key1", "val1");
   %>
   JavaBean测试：<br>
   姓名：<%=person.getName() %><br>
   年龄：<%=person.getAge() %>
  <!-- 隐藏域 -->
  <input type="hidden" id="input1" value="隐藏域的值" />
    <div class="one" id="one">
    	id为one,class为one的div
    </div>
    <div class="one" id="two" title="test">
    	id为two,class为one,title为test的div
    	<div class="mini" title="other">class为mini,title为other</div>
    	<div class="mini" title="test">class为mini,title为test</div>
    </div>
    <div class="one">
    	<div class="mini">class为mini</div>
    	<div class="mini">class为mini</div>
    	<div class="mini">class为mini</div>
    	<div class="mini"></div>
    </div>
    <div class="one">
    	<div class="mini">class为mini</div>
    	<div class="mini">class为mini</div>
    	<div class="mini">class为mini</div>
    	<div class="mini" title="tesst">class为mini,title为tesst</div>
    </div>
    <div style="display:none;" class="one">
    	style的display为"none"的div
    </div>
    <div class="hide">class为"hide"的div</div>
    <div>
    	包含input的type为"hidden"的div
    	<input type="hidden" size="8"></input>
    </div>
    <span id="mover">正在执行动画的span元素</span>
  </body>
</html>
