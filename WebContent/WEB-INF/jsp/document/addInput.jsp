<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信件详情</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/xhed/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhed/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhed/xheditor_lang/zh-cn.js"></script>
<script type="text/javascript">
	$(function(){
		var ab = document.getElementById("addAttach");
		var ac = document.getElementById("attachContainer")
		ab.onclick = function(){
			var node = "<span><br/><input type='file' name='attach'/><input type='button' onclick='remove(this)' value='移除'/></span>";
			ac.innerHTML = ac.innerHTML+node;
		}
	});
	
	function remove(obj){
		var ac = document.getElementById("attachContainer");
		ac.removeChild(obj.parentNode);
	} 
	
</script>
</head>
<body>
<jsp:include page="inc.jsp"/>
<s:fielderror/>
<form action="document_add" method="post" enctype="multipart/form-data">
<table class="ct" align="center" cellpadding="0" cellspacing="0" border="1" width="800">
<tr>
<td>标题</td><td><input type="text" name="title" ></td>
</tr>
<tr>
<td colspan="2">收文部门:</td>
</tr>
<tr>
<td colspan="2">
<s:if test="#canSendDeps.size()<1">没有可发送的部门</s:if>
<s:else><s:checkboxlist theme="simple" list="#canSendDeps" listKey="id" listValue="name" name="depIds"/></s:else>
</td>
</tr>
<tr>
<td colspan="2">
添加附件
</td>
</tr>
<tr>
<td colspan="2">
<input type="button" value="添加附件" id="addAttach"><br>
<div id="attachContainer">
<input type="file" name="attach"/>
</div>
</td>
</tr>
<tr>
<td colspan="2">
<textarea rows="20" cols="100" class="xheditor" name="content"></textarea>
</td>
</tr>
<tr><td colspan="2"><input type="submit" value="发送"/></td></tr>
</table>
</form>
</body>
</html>