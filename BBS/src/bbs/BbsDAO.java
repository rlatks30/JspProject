package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {


	private Connection conn; //Connection DB에 접근하게 해주는 객체
	private ResultSet rs;

	public BbsDAO() { //자동으로 데이터베이스 커넥션을 이루게해줌 
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
	
	public FileDTO download(int bbsID) {//bbs ID를 입력받아 filename,realname,bbsid를 set해줌 이부
		String SQL = "SELECT * FROM FILE WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				FileDTO fd = new FileDTO();
				fd.setFileName(rs.getString(1));
				fd.setFileRealName(rs.getString(2));
				fd.setBbsID(rs.getInt(3));
				return fd;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null; //리스트 반환
		
	}

	public int upload(String fileName, String fileRealName) {
		String SQL = "INSERT INTO FILE VALUES (?,?,?)"; // 파일 업로드
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setInt(3, getNext());
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // DB오류
	}
	
	
	public int getNext() { //게시글의 번호를 붙여줌
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫 게시물인 경우
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)"; //테이블 bbs에 6개의 values를 insert함
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());//글 번호
			pstmt.setString(2, bbsTitle); //제목
			pstmt.setString(3, userID);   //아이디
			pstmt.setString(4, getDate()); //날짜
			pstmt.setString(5, bbsContent); //내용
			pstmt.setInt(6, 1);    //삭제 여부
			
			return pstmt.executeUpdate(); //업데이트 된걸 return 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	public ArrayList<Bbs> getList(int pageNumber){ //글목록
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10개씩
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list; //리스트 반환
		
	}
	
	public boolean nextPage(int pageNumber) { //다음페이지 함수
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10개씩
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false; //리스트 반환
		
	}
	
	public Bbs getBbs(int bbsID) { //글 블러오기함수
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null; //리스트 반환
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) { //글 수정 함수
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?"; //완전 삭제가 아닌 avialble을 0으로 바꿈
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
}
