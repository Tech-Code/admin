<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
</head>

<body>
	<form action="${ctx }/user/login" method="post">
		姓名：<input name="username"><br /> 密码：<input type="password"
			name="password"><br /> <input type="submit" value="登陆">
	</form>
</body>
</html>
