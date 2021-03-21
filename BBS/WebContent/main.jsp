<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="schedule.ScheduleDAO"%>
<%@ page import="schedule.Schedule"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/slider.css">
<title>JSP 목표를 향해</title>
<link type="text/css" rel="stylesheet" href="css/styles.css">
<script src="js/slider.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
setInterval(function(){ 
	$('#slide1>ul').delay(2500); 
	$('#slide1>ul').animate({marginLeft: "-1200px"});
	$('#slide1>ul').delay(2500); 
	$('#slide1>ul').animate({marginLeft: "-1800px"});
	$('#slide1>ul').delay(2500); 
	$('#slide1>ul').animate({marginLeft: "0px"}) });
</script>
</head>
<body>
	<%@include file="header.jsp"%>
	<div id="index_01"> 
		<div id="slide1"> 
			<ul> 
				<li><a href="#"><img src="img/slide1.jpg" alt="슬라이드1"></a></li> 
				<li><a href="#"><img src="img/slide2.jpg" alt="슬라이드2"></a></li> 
				<li><a href="#"><img src="img/slide1.jpg" alt="슬라이드3"></a></li> 
			</ul> 
		</div> 
	</div>

	<%
		int pageNumber = 1;
	ScheduleDAO scheduleDAO = new ScheduleDAO();
	ArrayList<Schedule> list = scheduleDAO.getList(pageNumber, userID);
	%>
	<div class="container">
		<div class="jumbotron">
			<div class="containoer">
				<h1>장기 목표</h1>
				<b>이 웹사이트는 스케줄을 간단히 정리하기 위해 만들어졌습니다.</b><br>
				<%
					try {
					for (Schedule schedule : list) { // 이  list에 Schedule 객체가 0 index 부터 쌓여있는데 [1개 목표, 1개 목표]
						if (schedule.getScAvailable() == 2){
							
						}
						if (schedule.getScLength() == 1) {
				%>
				<b><%=schedule.getScTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
		.replaceAll("\n", "<br>")%>
				</b><br>
					<%
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				%>
				<a class="btn btn-primory btn-pull" role="botton"></a>
			</div>
		</div>
		<div class="jumbotron">
			<div class="containoer">
				<h1>단기 목표</h1>
				<b>이 웹사이트는 스케줄을 간단히 정리하기 위해 만들어졌습니다.</b><br>
				<%
					try {
					for (Schedule schedule : list) { // 이  list에 Schedule 객체가 0 index 부터 쌓여있는데 [1개 목표, 1개 목표]
						if (schedule.getScLength() == 2) {
				%>
				<b><%=schedule.getScTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
		.replaceAll("\n", "<br>")%>
				</b><br>
					<%
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				%>
				<a class="btn btn-primory btn-pull" role="botton"></a>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp"%>
</body>
</html>