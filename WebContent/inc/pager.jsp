<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<pg:pager items="${param.tr }" maxPageItems="15" export="curPage=pageNumber" url="${url }">

<c:forEach items="${param.params }" var="p">
<pg:param name="${p }"/>
</c:forEach>
	
	<pg:last>
	共${param.tr }条信息，共${pageNumber }页，当前第${curPage }页
	</pg:last>
	<pg:first>
		<a href="${pageUrl }">首页</a>
	</pg:first>
	<pg:prev>
		<a href="${pageUrl }">上一页</a>
	</pg:prev>
	<pg:pages>
		<c:if test="${curPage eq pageNumber }">${pageNumber }</c:if>
		<c:if test="${curPage ne pageNumber }"><a href="${pageUrl }">${pageNumber }</a></c:if>
	</pg:pages>
	<pg:next>
		<a href="${pageUrl }">下一页</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl }">尾页</a>
	</pg:last>
</pg:pager>