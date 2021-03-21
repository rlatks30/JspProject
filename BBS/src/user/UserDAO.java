package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;


public class UserDAO { //데이터베이스 접근객체 , DB의 회원정보 부르기, DB회원정보 넣기
	
	private Connection conn; //Connection DB에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() { //자동으로 데이터베이스 커넥션을 이루게해줌
		try {//예외처리해주는 try catch try는 예외가 없으면 정상실행 catch는 예외시 처리
			String dbURL = "jdbc:mysql://localhost:3306/BBS?useSSL=false"; // localhost:3306 내 pc에 설치된 mysql서버를 의미 BBS란 DB접속하기
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver"); // mysql 드라이버(mysql에 접속할수 있도록 매개체 역할을 해주는 라이브러리) 찾기
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // dbURL에 dbID로 dbPw를 이용해서 접속할수있도록 함
		} catch (Exception e) {
			e.printStackTrace(); //오류를 출력
		}
	}
	
	public int login(String userID, String userPassword) { //실제로 로그인을 시도하는 함수 id와 pw를 받아서 처리할수있다.
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //실제로 DB에 입력할 명령어를 SQL문장으로 만듬 , USER 테이블에서 PW가져오기
		try {
			pstmt = conn.prepareStatement(SQL); //preparestatment에 어떤 정해진 문장을 DB에 삽임
			pstmt.setString(1, userID	); //중요한부분 * SQL인젝션 해킹기법 방어하기위한 방법 
			rs = pstmt.executeQuery(); //결과를 담을수있는 객체에 실행한 결과 담기
			if (rs.next()) { // 결과가 존재하는지 판별 실행했을때 회원이 실제로 존재하지 않으면 return시킨다.
				if(rs.getString(1).equals(userPassword)) { //입력받은 Pw와 저장된Pw가 동일하다면 로그인시킨다. 
					return 1; //로그인 성공
				}
				else
					return 0; //로그인 실패 , 비밀번호 불일치
			}
			return -1; // 아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
