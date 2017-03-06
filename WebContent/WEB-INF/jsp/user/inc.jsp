<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<s:if test="#session.loginUser.type==0">
<a href="user_addInput">添加用户</a>&nbsp;
<a href="user_list">用户列表</a>&nbsp;
</s:if>