<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%
		session.invalidate(); // 세션 뺏기
	%>
	<script>
		location.href = 'main.jsp';
	</script>
</body>
</html>