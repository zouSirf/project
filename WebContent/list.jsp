<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>我是list页面</h1>
	<shiro:hasRole name="admin">
	<a href="admin.jsp">admin page</a>
	</shiro:hasRole>
	<br/><br/>
	<shiro:hasRole name="user">
	<a href="user.jsp">user page</a>
	</shiro:hasRole>
	<br/><br/>
	<a href="shiro/testShiroAnnotation">test ShiroAnnotation</a>
	<br/><br/>
	<a href="shiro/logout">Logout</a>
</body>
</html>