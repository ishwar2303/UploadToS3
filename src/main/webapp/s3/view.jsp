<%@page import="java.util.ArrayList"%>
<%@page import="com.upload.Object" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	ArrayList<Object> objects = (ArrayList<Object>) request.getAttribute("objects");
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
		for(Object obj : objects) {
	%>
		<div class="img-card">
			<img src="https://<%= bucket %>.s3.ap-south-1.amazonaws.com/<%= obj.getObjectKey() %>" alt="">
			<a href="delete-object?objectKey=<%= obj.getObjectKey() %>" class="delete">
				<i class="fas fa-trash-alt"></i>
			</a>
			<a href="https://<%= bucket %>.s3.ap-south-1.amazonaws.com/<%= obj.getObjectKey() %>" class="download">
				<i class="fas fa-download"></i>
			</a>
			<div class="key"><%= obj.getTitle() %></div>
			<div class="description"><%= obj.getDescription() %></div>
		</div>
	<% 
		}
	%>
	</div>
</body>
</html>