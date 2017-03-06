<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加部门</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr>
<td>部门ID</td><td>部门名称</td><td>操作</td>
</tr>
<tr>
<td>${id }</td><td>${name }</td>
	<td>
		<a href="department_updateInput?id=${id }">更新</a>&nbsp;
		<a href="department_delete?id=${id }">删除</a>&nbsp;
		<a href="department_setDepScopeInput?id=${id }">设置发文部门</a>&nbsp;
	</td>
</tr>
<tr>
<td colspan="3">可发文部门:</td>
</tr>
<tr>
<td colspan="3">
<s:if test="#ls==null">无</s:if>
<s:else>
<s:iterator value="#ls">${name },&nbsp;</s:iterator>
</s:else>
</td>
</tr>
</table>
</body>
</html>