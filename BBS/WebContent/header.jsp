<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="schedule.ScheduleDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		String userID = null; //로그인이 되있으면 id담기고 아니라면 null
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	%>
	<nav class="navbar navbar-inverse" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false"></button>
			<a class="navbar-brand" href="main.jsp">오늘의 스케줄</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li><a href="bbs.jsp">커뮤니티</a></li>
				<li class="nav-item dropdown">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">메뉴<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="schedule.jsp">목표 추가</a></li>
								<li><a href="scview.jsp">목표 보기</a></li>
								<li><a href="scComView.jsp">달성한 목표 보기</a></li>
							</ul></li>
					</ul>
				</li>
				<li><a href="rankview.jsp">랭킹보기</a></li>
			</ul>
			<%
				if (userID == null) { //로그인이 되어있지 않다면,세션이 담겨있지 않다면
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul></li>
			</ul>
			<%
				} else { //로그인이 되어있다면
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul></li>
			</ul>
			<%}%>
		</div>
	</nav>
</body>
</html>