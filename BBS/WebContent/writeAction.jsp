<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %><%--로그인페이지 가져오기 --%>
<%@ page import="java.io.PrintWriter" %><%--자바스크립트 문장을 사용하기 위해 작성 --%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.io.File" %>
<% request.setCharacterEncoding("UTF-8"); %><%--건너오는 모든 데이터를 UTF-8로 받을수있음 --%>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/><%--자바 빈즈사용 회원정보를 담는 bbs 클래스를 자바빈즈로 사용 ,scope는 현재페이지 안에서만 빈즈사용 --%>
<jsp:setProperty property="bbsTitle" name="bbs"/><%--write페이지에서 넘겨준 bbsTitle를 받아서 bbs에 넣어준다. --%>
<jsp:setProperty property="bbsContent" name="bbs"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%	
	String getTitle = "";
	String getContent = "";
	try{
		String directory = application.getRealPath("/upload/");
		int maxsize = 1024*1024*100;
		String encoding = "utf-8";
		MultipartRequest multipartRequest = new MultipartRequest(request,directory,maxsize,encoding,
				new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		String fileRealName = multipartRequest.getFilesystemName("file");
		new BbsDAO().upload(fileName,fileRealName);
		getTitle = multipartRequest.getParameter("bbsTitle");
		getContent = multipartRequest.getParameter("bbsContent");
	}catch(Exception e){
		e.printStackTrace();
	}
	
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
		  if (getTitle == null || getContent == null  ){
			PrintWriter script = response.getWriter();
			System.out.print(getTitle);
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()"); //이전 페이지로 돌려보내기
			script.println("</script>");
		  } else{
				  BbsDAO bbsDAO = new BbsDAO();
				  int result = bbsDAO.write(getTitle, userID, getContent);//BbsDAO.write에 제목,유저ID,내용을 넣고 반환값을 result에 담는다. 
					if (result== -1){ // 글쓰기 실패
						PrintWriter script = response.getWriter(); 
						script.println("<script>");
						script.println("alert('글쓰기에 실패했습니다.')");
						script.println("history.back()");
						script.println("</script>");
					}
					else { //글쓰기 완료
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href = 'bbs.jsp'");
						script.println("</script>");
					}
				  
			  }
	}
	

	  


	
	%>
	
</body>
</html>