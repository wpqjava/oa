<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr>
<td>用户ID</td><td>用户名</td><td>用户昵称</td><td>用户类型</td><td>E-MAIL</td><td>部门</td><td>操作</td>
</tr>
<tr>
<td>${id }</td><td>${username }</td><td>${nickname }</td>
<td>
<s:if test="type==0">管理员</s:if>
<s:else>普通用户</s:else>
</td>
<td>${email }</td><td>${department.name }</td>
<td>
<a href="user_updateInput?id=${id }">更新</a>&nbsp;
<a href="user_delete?id=${id }">删除</a>&nbsp;
</td>
</tr>
</table>
</body>
</html>