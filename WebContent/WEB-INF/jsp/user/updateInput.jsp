<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<s:form action="user_update" method="post">
	<s:hidden name="id"/>
	<s:textfield name="nickname" label="用户昵称"/>
	<s:textfield name="email" label="EMAIL"/>
	<s:radio list="#{'0':'管理员','1':'普通用户'}" name="type" value="%{type}"/>
	<s:select list="#deps" listKey="id" listValue="name" name="depId" value="%{department.id}"/>
	<s:submit value="提交"/>
</s:form>
</body>
</html>