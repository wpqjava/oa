<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公文详情</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="inc.jsp"/>
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" >
<tr>
<td>标题</td><td>${title }</td>
</tr>
<tr>
<td>发件时间</td><td><s:date name="createDate" format="yyyy-MM-dd HH:mm"/>
</td>
</tr>
<tr>
<td colspan="2">所发送的部门:</td>
</td>
</tr>
<tr>
<td colspan="2">
<s:iterator value="#sendedDeps">
${name }&nbsp;
</s:iterator>
</td>
</tr>
<tr>
<td>内容</td><td><textarea rows="10" cols="20">${content }</textarea></td>
</tr>
<s:if test="#attaches.size()>0">
<tr><td colspan="2">附件:</td></tr>
<s:iterator value="#attaches">
	<tr><td colspan="2"><a href="<%=request.getContextPath()%>/upload/${newName}">${oldName }</a></td></tr>
</s:iterator>
</s:if>
</table>
${contend }
</body>
</html>