<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 목표를 향해</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container">
		<div class="row">
			<form method="post" action="ScheduleWAction.jsp">
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">장기
								목표 설정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control"
								placeholder="목표의 제목" name="scTitle" maxlength="50"></td>
						</tr>
						<tr>
							<td valign="top"><select name="scLength">
									<option>목표를 선택해주세요</option>
									<option value=3>일일목표로 설정하기</option>
									<option value=2>단기목표로 설정하기</option>
									<option value=1>장기목표로 설정하기</option>
							</select></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="내용"
									name="scContent" maxlength="9999" style="height: 350px;"></textarea></td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="저장하기" />
			</form>

		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<%@include file="footer.jsp"%>
</body>
</html>