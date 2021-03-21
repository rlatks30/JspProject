<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="schedule.ScheduleDAO" %>
<%@ page import="schedule.Schedule" %>
<%@ page import="java.util.ArrayList" %>
<%
	request.setCharacterEncoding("UTF-8");
%><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID"); //변수 userID에 세션값 담기
	}
	if (userID == null) { // 이미 로그인 됐을경우
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 하세요.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	}
	int scID = 0;
	if (request.getParameter("scID") != null) {
		scID = Integer.parseInt(request.getParameter("scID"));
	}
	if (scID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다..')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	}
	Schedule sc = new ScheduleDAO().getSchedule(scID);
	if (!userID.equals(sc.getUserID())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	} else {
		ScheduleDAO scDAO = new ScheduleDAO();
		int result = scDAO.complete(scID);//--실제로 입력된 값을 delete함수에 넣어서 실행한다.
		if (result == -1) { // 글삭제 실패
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('목표 달성 완료에 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else { //글삭제 완료
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'scview.jsp'");
			script.println("alert('목표 달성 완료!!! 축하 드립니다.')");
			script.println("</script>");
		}
	}
	%>

</body>
</html>