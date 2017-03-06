<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %> 
<%@taglib prefix="s" uri="/struts-tags" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title/></title>
<decorator:head/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body>
<s:if test="#session.loginUser!=null">
<s:if test="#session.loginUser.type==0">
<a href="department_list">部门管理</a>&nbsp;<a href="user_list">用户管理</a>&nbsp;
</s:if>
<a href="document_listReceive">公文管理</a>&nbsp;
<a href="message_listReceive">私人信件</a>&nbsp;
<a href="user_showSelf?id=${session.loginUser.id }">个人信息查看</a>&nbsp;<a href="user_updateSelfInput?id=${session.loginUser.id }">修改个人信息</a>&nbsp;<a href="login_logout">退出系统</a>&nbsp;
</s:if>
<s:else>
<a href="login_loginInput">用户登录</a>&nbsp;
</s:else>
<hr/>
<decorator:body/>
<hr/>
<div align="center">
copyRight@2016-2020
</div>
</body>
</html>