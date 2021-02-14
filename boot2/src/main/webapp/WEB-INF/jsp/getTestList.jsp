<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String time = (String)request.getAttribute("time");
	out.print(time);
	out.print("성공");
%>
