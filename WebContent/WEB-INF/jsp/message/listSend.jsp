<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已发送的信件</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr><td colspan="3">
<form action="message_listSend" method="get">
<input type="hidden" name="isRead" value="${isRead }">
<s:textfield theme="simple" name="con"/>&nbsp;<s:submit theme="simple" value="筛选"/>
</form>
</td></tr>
<tr>
<td>标题</td><td>发送时间</td><td>操作</td>
</tr>
<s:iterator value="#pager.datas" >
<tr>
<td><a href="message_showSend?id=${id }">${title }</a></td><td><s:date name="createDate" format="yyyy-MM-dd HH:mm" /></td>
<td>
<a href="message_deleteSend?id=${id }">删除</a>&nbsp;
</td>
</tr>
</s:iterator>
<tr><td colspan="3">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="${pager.totalRecord }" name="tr"/>
	<jsp:param value="message_listSend" name="url"/>
	<jsp:param value="con" name="params"/>
</jsp:include></td>
</tr>
</table>
</body>
</html>