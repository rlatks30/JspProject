<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %><%--로그인페이지 가져오기 --%>
<%@ page import="java.io.PrintWriter" %><%--자바스크립트 문장을 사용하기 위해 작성 --%>
<% request.setCharacterEncoding("UTF-8"); %><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<jsp:useBean id="user" class="user.User" scope="page"/><%--자바 빈즈사용 회원정보를 담는 user 클래스를 자바빈즈로 사용 ,scope는 현재페이지 안에서만 빈즈사용 --%>
<jsp:setProperty property="userID" name="user"/><%--login페이지에서 넘겨준 userid를 받아서 userID에 넣어준다. --%>
<jsp:setProperty property="userPassword" name="user"/>
<jsp:setProperty property="userName" name="user"/>
<jsp:setProperty property="userGender" name="user"/>
<jsp:setProperty property="userEmail" name="user"/>
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
	if(userID != null){ // 이미 로그인 됐을경우
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인 되었습니다.')");
		script.println("location.href='main.jsp'"); //이전 페이지(로그인페이지)로 돌려보내기
		script.println("</script>");
	}
	  if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null || user.getUserGender() == null || user.getUserEmail() == null ){
		  PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다..')");
			script.println("history.back()"); //이전 페이지(로그인페이지)로 돌려보내기
			script.println("</script>");
	  } 
	  else{
		  UserDAO userDAO = new UserDAO();
		  int result = userDAO.join(user);//--실제로 입력된 값을 join함수에 넣어서 실행한다.
			if (result== -1){ // 이미존재, 동일
				PrintWriter script = response.getWriter(); 
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else { //회원가입 완룐
				session.setAttribute("userID", user.getUserID()); //세션 할당하기
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp'");
				script.println("</script>");
			}
		  
	  }

	
	%>
	
</body>
</html>