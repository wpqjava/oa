<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定邮箱</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<s:fielderror/>
<s:form action="user_addUe" method="post">
<s:hidden name="id" value="%{id}"/>
	<s:textfield name="ue.host" label="邮箱主机" />
	<s:textfield name="ue.username" label="邮箱用户名"/>
	<s:password name="ue.password" label="邮箱密码"/>
	<s:submit value="提交"/>
</s:form>
</body>
</html>