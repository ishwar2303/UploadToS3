<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	ArrayList<String> objects = (ArrayList<String>) request.getAttribute("objects");
	String bucket = (String) request.getAttribute("bucket");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bucket | Objects</title>

<link rel="stylesheet" href="assets/main.css">
<script src="https://kit.fontawesome.com/196c90f518.js" crossorigin="anonymous"></script>
</head>
<body>
	<div class="img-wrapper">
	<%
		for(String object : objects) {
	%>
		<div class="img-card">
			<div class="key"><%= object %></div>
			<img src="https://<%= bucket %>.s3.ap-south-1.amazonaws.com/<%= object %>" alt="">
			<a href="delete-object?objectKey=<%= object %>" class="delete">
				<i class="fas fa-trash-alt"></i>
			</a>
		</div>
	<% 
		}
	%>
	</div>
</body>
</html>