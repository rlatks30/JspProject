<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="schedule.ScheduleDAO" %><%--로그인페이지 가져오기 --%>
<%@ page import="schedule.Schedule" %><%--로그인페이지 가져오기 --%>
<%@ page import="java.io.PrintWriter" %><%--자바스크립트 문장을 사용하기 위해 작성 --%>
<% request.setCharacterEncoding("UTF-8"); %><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 목표를 향해</title>
</head>
<body>
<%
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID"); //변수 userID에 세션값 담기
	}
	if(userID == null){ // 로그인이 안됐을 경우
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
		script.println("location.href = 'bbs.jsp'"); 
		script.println("</script>");
	}
	Schedule sc = new ScheduleDAO().getSchedule(scID);
	if (!userID.equals(sc.getUserID())){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'scview.jsp'"); 
		script.println("</script>");
	} else{ //입력이 안될경우
		  if (request.getParameter("scTitle") == null || request.getParameter("scContent") == null
		  || request.getParameter("scTitle").equals("") || request.getParameter("scContent").equals("")){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()"); //이전 페이지로 돌려보내기
			script.println("</script>");
		  } else{
				  ScheduleDAO scheduleDAO = new ScheduleDAO();
				  int result = scheduleDAO.update(scID,request.getParameter("scTitle"),request.getParameter("scContent"));//BbsDAO.write에 제목,유저ID,내용을 넣고 반환값을 result에 담는다. 
					if (result== -1){ // 글쓰기 실패
						PrintWriter script = response.getWriter(); 
						script.println("<script>");
						script.println("alert('글수정에 실패했습니다.')");
						script.println("history.back()");
						script.println("</script>");
					}
					else { //글쓰기 완료
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href = 'scview.jsp'");
						script.println("</script>");
					}
				  
			  }
	}
%>
	
</body>
</html>