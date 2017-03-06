<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置发文部门</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="department_setDepScope" method="post">
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr>
<td>部门ID</td><td>部门名称</td>
</tr>
<tr>
<td>${id }</td><td>${name }</td>
</tr>
<tr>
<td colspan="2">设置可发文部门:</td>
</tr>
<tr>
<td colspan="2">
<s:checkboxlist theme="simple" list="#allDeps" listKey="id" listValue="name" name="aidd" value="#availableInformDep"/>
<s:hidden name="id"/>
</td>
</tr>
<tr>
<s:submit value="提交"/>
</tr>
</table>
</form>
</body>
</html>