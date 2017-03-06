<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr><td colspan="7">
<form action="user_list" method="get">
<s:select theme="simple" list="deps" listKey="id" listValue="name" name="depId" headerKey="0" headerValue="全部用户"/><s:submit theme="simple" value="筛选"/>
</form>
</td></tr>
<tr>
<td>用户ID</td><td>用户名</td><td>用户昵称</td><td>用户类型</td><td>E-MAIL</td><td>部门</td><td>操作</td>
</tr>
<s:iterator value="#pager.datas" >
<tr>
<td>${id }</td><td><a href="user_show?id=${id }">${username }</a></td><td>${nickname }</td>
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
</s:iterator>
<tr><td colspan="7">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="${pager.totalRecord }" name="tr"/>
	<jsp:param value="user_list" name="url"/>
	<jsp:param value="depId" name="params"/>
</jsp:include></td>
</tr>
</table>
</body>
</html>