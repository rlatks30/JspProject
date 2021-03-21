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
<title>오늘의 스케줄</title>
</head>
<body>
	<%@include file="header.jsp" %>
	<%
		int scID = 0;
		if (request.getParameter("scID") != null){
			scID = Integer.parseInt(request.getParameter("scID"));
		}
		if (scID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다..')");
			script.println("location.href = 'bbs.jsp'"); 
			script.println("</script>");
		}
		Schedule sc = new ScheduleDAO().getSchedule(scID);
	%>
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width :20%;">글 제목</td>
						<td colspan="2"><%= sc.getScTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%= sc.getUserID() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%= sc.getScDate().substring(0, 11)+ sc.getScDate().substring(11,13) + "시" + sc.getScDate().substring(14, 16) + "분" %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2" style="min-height : 200px; text-align: Left;"><%= sc.getScContent().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
				</tbody>
			</table>
			<a href="scview.jsp" class="btn btn-primary">목록</a>
			<%
				if(userID != null && userID.equals(sc.getUserID())){
			%>
					<a href="scupdate.jsp?scID=<%= scID %>" class ="btn btn-primary">수정</a>
					<a href="CompliteAction.jsp?scID=<%= scID %>" class ="btn btn-primary">목표달성!</a>
					<a onclick="return confirm('정말로 삭제하시겠습니까')"   href="deleteAction.jsp?scID=<%= scID %>" class ="btn btn-primary">삭제</a>
			<%
				}
			%>
		</div>
	</div>
	 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp" %>
</body>
</html>