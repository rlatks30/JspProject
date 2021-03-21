<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%><%--로그인페이지 가져오기 --%>
<%@ page import="java.io.PrintWriter"%><%--자바스크립트 문장을 사용하기 위해 작성 --%>
<%
	request.setCharacterEncoding("UTF-8");
%><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<jsp:useBean id="user" class="user.User" scope="page" /><%--자바 빈즈사용 회원정보를 담는 user 클래스를 자바빈즈로 사용 ,scope는 현재페이지 안에서만 빈즈사용 --%>
<jsp:setProperty property="userID" name="user" /><%--login페이지에서 넘겨준 userid를 받아서 userID에 넣어준다. --%>
<jsp:setProperty property="userPassword" name="user" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%
		String userID = null;
	if (session.getAttribute("userID") != null) { //세션을 확인해서 null이 아닐경우
		userID = (String) session.getAttribute("userID"); //변수 userID에 세션값 담기
	}
	if (userID != null) { // 이미 로그인 됐을경우
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인 되었습니다.')");
		script.println("location.href='main.jsp'"); //이전 페이지(로그인페이지)로 돌려보내기
		script.println("</script>");
	}
	UserDAO userDAO = new UserDAO();
	int result = userDAO.login(user.getUserID(), user.getUserPassword());//--실제로 입력된 값을 login함수에 넣어서 실행한다. -2~1까지 result에 담김.
	if (result == 1) { // 로그인
		session.setAttribute("userID", user.getUserID()); //세션 할당하기
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'main.jsp'"); //로그인에 성공시 이동
		script.println("</script>");
	} else if (result == 0) { //비밀번호 틀릴때 0
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호가 틀립니다.')");
		script.println("history.back()"); //이전 페이지(로그인페이지)로 돌려보내기
		script.println("</script>");
	} else if (result == -1) { //아이디가 존재하지 않을떄 -1
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('존재하지 않는 아이디입니다.')");
		script.println("history.back()"); //이전 페이지(로그인페이지)로 돌려보내기
		script.println("</script>");
	} else if (result == -2) { //db오류일경우 -2
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터베이스 오류가 발생했습니다..')");
		script.println("history.back()"); //이전 페이지(로그인페이지)로 돌려보내기
		script.println("</script>");
	}
	%>

</body>
</html>