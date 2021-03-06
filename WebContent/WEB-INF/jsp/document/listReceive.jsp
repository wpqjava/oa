<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已接受的公文</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr><td colspan="4">
<form action="document_listReceive" method="get">
<input type="hidden" name="isRead" value="${isRead }">
<s:textfield theme="simple" name="con"/>&nbsp;
<s:select list="#deps" listKey="id" listValue="name" name="depId" theme="simple" headerKey="0" headerValue="全部"/>
<s:submit theme="simple" value="筛选"/>&nbsp;
</form>
<a href="document_listReceive?isRead=-1">未读公文</a>&nbsp;<a href="document_listReceive?isRead=1">已读公文</a>&nbsp;<a href="document_listReceive">全部公文</a>&nbsp;
</td></tr>
<tr>
<td>标题</td><td>发送人</td><td>发送时间</td><td>是否已读</td>
</tr>
<s:iterator value="#pager.datas" >
<tr>
<td><a href="document_showReceive?id=${document.id }&isRead=${isRead}">${document.title }</a></td><td>${document.user.nickname }[${document.user.department.name }]</td><td><s:date name="document.createDate" format="yyyy-MM-dd HH:mm"/></td>
<s:if test="isRead==-1"><td style="color:RED">未读</td></s:if>
<s:elseif test="isRead==1"><td style="color:GREEN">已读</td></s:elseif>
</tr>
</s:iterator>
<tr><td colspan="4">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="${pager.totalRecord }" name="tr"/>
	<jsp:param value="document_listReceive" name="url"/>
	<jsp:param value="con" name="params"/>
</jsp:include></td>
</tr>
</table>
</body>
</html>