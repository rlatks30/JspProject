<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="schedule.ScheduleDAO" %><%--로그인페이지 가져오기 --%>
<%@ page import="java.io.PrintWriter" %><%--자바스크립트 문장을 사용하기 위해 작성 --%>
<% request.setCharacterEncoding("UTF-8"); %><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<jsp:useBean id="schedule" class="schedule.Schedule" scope="page"/><%--자바 빈즈사용 회원정보를 담는 bbs 클래스를 자바빈즈로 사용 ,scope는 현재페이지 안에서만 빈즈사용 --%>
<jsp:setProperty property="scTitle" name="schedule"/><%--write페이지에서 넘겨준 bbsTitle를 받아서 bbs에 넣어준다. --%>
<jsp:setProperty property="scContent" name="schedule"/>
<jsp:setProperty property="scLength" name="schedule"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
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
	else{ //입력이 안될경우
		  if (schedule.getScTitle() == null || schedule.getScContent() == null  ){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()"); //이전 페이지로 돌려보내기
			script.println("</script>");
		  } else{
			  	  ScheduleDAO ScheduleDAO = new ScheduleDAO();//
				  int result = ScheduleDAO.write(schedule.getScTitle(), userID, schedule.getScContent(), schedule.getScLength());//ScheduleDAO.write에 제목,유저ID,내용,목표길이 을 넣고 반환값을 result에 담는다. 
					if (result== -1){ // 글쓰기 실패
						PrintWriter script = response.getWriter(); 
						script.println("<script>");
						script.println("alert('글쓰기에 실패했습니다.')");
						script.println("history.back()");
						script.println("</script>");
					}
					else { //글쓰기 완료ㅇㅇ
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href = 'main.jsp'");
						script.println("</script>");
					}
				  
			  }
	}
	

	  


	
	%>
	
</body>
</html>