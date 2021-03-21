<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%@include file="header.jsp" %>
	<div class="container">
		<div class="row">
		<form method="post" action="writeAction.jsp" enctype="multipart/form-data">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글쓰기 양식</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" class="form-control" placeholder="글 제목" name="bbsTitle" maxlength="50"></td>
					</tr>
					<tr>
						<td><textarea class="form-control" placeholder="글 내용" name="bbsContent" maxlength="2048" style="height: 350px;"></textarea></td>
					</tr>
				</tbody>
			</table>
			<input type="file" name="file">
			<input type="submit" class="btn btn-primary pull-right" value="글쓰기"/>
		</form>	

		</div>
	</div>
	 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp" %>
</body>
</html>