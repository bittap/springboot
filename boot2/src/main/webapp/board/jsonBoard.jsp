<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%--contentType를 json으로 변경 --%>
<%@ page import="com.google.gson.Gson" %>     
<%@ page import="java.util.*" %>
<%
	List<Map<String, Object>> boardList = (List<Map<String, Object>>)request.getAttribute("boardList");
	Gson g = new Gson();
	String temp = g.toJson(boardList);
	out.print(temp);
%>