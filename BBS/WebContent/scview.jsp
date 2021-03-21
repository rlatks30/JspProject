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
<style type="text/css">
	a, a:hover {
		color : #000000;
		text-decoration: none;
	}
</style>
</head>
<body>
	<%@include file="header.jsp" %>
	<%
		int pageNumber = 1;
		if (request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'"); 
			script.println("</script>");
		}
	%>
	<h2 style=text-align:center;>오늘의 스케줄 내 목표보기</h2> 
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>
						<th style="background-color: #eeeeee; text-align: center;">목표의 길이</th>
					</tr>
				</thead>
				<tbody>
					<%
								ScheduleDAO scheduleDAO = new ScheduleDAO();
								ArrayList<Schedule> list = scheduleDAO.getList(pageNumber, userID);
								
								try{
									for(Schedule schedule : list) { // 이  list에 Schedule 객체가 0 index 부터 쌓여있는데 [1개 목표, 1개 목표]
										if(schedule.getScLength() == 1) {
											schedule.setScLengthKr("장기목표");
										}	else if(schedule.getScLength() == 2) { 
											schedule.setScLengthKr("단기목표");
										}	else if(schedule.getScLength() == 3) { 
											schedule.setScLengthKr("일일목표");
										} 
					%>					
					<tr>
						<td><%= schedule.getScID() %></td>
						<td><a href="scviewinfo.jsp?scID=<%= schedule.getScID() %>"><%= schedule.getScTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></a></td>
						<td><%= schedule.getUserID() %></td>
						<td><%= schedule.getScDate().substring(0, 11)+ schedule.getScDate().substring(11,13) + "시" + schedule.getScDate().substring(14, 16) + "분" %></td>
						<td><%= schedule.getScLengthKr() %></td>
					</tr>
					<%
									}
					%>

				</tbody>
			</table>
			<%
				if(pageNumber != 1){
			%>
				<a href="scview.jsp?pageNumber=<%= pageNumber - 1 %>" class = "btn btn-success btn-arrow-Left">이전</a>
			<%
				} if(scheduleDAO.nextPage(pageNumber + 1)){
			%>
				<a href="scview.jsp?pageNumber=<%= pageNumber + 1 %>" class = "btn btn-success btn-arrow-Left">다음</a>
			<%
				}
								}catch (Exception e) {
									e.printStackTrace();
								}
			%>
		</div>
	</div>
	 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp" %>
</body>
</html>