<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="schedule.ScheduleDAO" %>
<%@ page import="schedule.Schedule" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<%
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'"); 
			script.println("</script>");
		}
		int scID = 0; //현재 글이 유효한 글인지 검사
		if (request.getParameter("scID") != null){
			scID = Integer.parseInt(request.getParameter("scID"));
		}
		if (scID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다..')");
			script.println("location.href = 'scview.jsp'"); 
			script.println("</script>");
		}
		Schedule sc = new ScheduleDAO().getSchedule(scID);
		if (!userID.equals(sc.getUserID())){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'scview.jsp'"); 
			script.println("</script>");
		}
	%>
	<div class="container">
		<div class="row">
		<form method="post" action="scUpdateAction.jsp?scID=<%= scID %>">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글수정 양식</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" class="form-control" placeholder="글 제목" name="scTitle" maxlength="50" value="<%= sc.getScTitle()%>"></td>
					</tr>
					<tr>
						<td><textarea class="form-control" placeholder="글 내용" name="scContent" maxlength="2048" style="height: 350px;" ><%= sc.getScContent() %></textarea></td>
					</tr>
				</tbody>
			</table>
			<input type="submit" class="btn btn-primary pull-right" value="글수정">
		</form>	

		</div>
	</div>
	 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp" %>
</body>
</html>