package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import schedule.Schedule;
import schedule.Rank;

public class ScheduleDAO {
	private Connection conn; //Connection DB에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs;
	int point = 0;
	public ScheduleDAO() { //자동으로 데이터베이스 커넥션을 이루게해줌
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
	
	
	public String getDate() { //시간 가져오기
		String SQL = "SELECT NOW()"; //현재 시간가져오는 명령어
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
		String SQL = "SELECT scID FROM SCHEDULE ORDER BY scID DESC";
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
	
	public int write(String scTitle, String userID, String scContent, int scLength) {
		String SQL = "INSERT INTO SCHEDULE VALUES (?, ?, ?, ?, ?, ?, ?)"; //테이블 bbs에 7개의 values를 insert함
		try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext());//글 번호
				pstmt.setString(2, scTitle); //제목
				pstmt.setString(3, userID);   //아이디
				pstmt.setString(4, getDate()); //날짜
				pstmt.setString(5, scContent); //내용
				pstmt.setInt(6, 1);    //삭제 여부
				pstmt.setInt(7, scLength);    //장기, 단기, 일일  1,2,3
				
				return pstmt.executeUpdate(); //업데이트 된걸 return 
			}catch (Exception e) {
				e.printStackTrace();
			}
			return -1; // DB오류
		}
	
	public ArrayList<Schedule> getList(int pageNumber,String userID){ //목표 목록
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 1 AND userID = ? ORDER BY scID DESC LIMIT 10";
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10개씩
			pstmt.setString(2, userID); //10개씩
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setScID(rs.getInt(1));
				schedule.setScTitle(rs.getString(2));
				schedule.setUserID(rs.getString(3));
				schedule.setScDate(rs.getString(4));
				schedule.setScContent(rs.getString(5));
				schedule.setScAvailable(rs.getInt(6));
				schedule.setScLength(rs.getInt(7));
				list.add(schedule);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list; //리스트 반환
		
	}
	
	public ArrayList<Schedule> getComList(int pageNumber,String userID){ //완성한 목표 목록
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 2 AND userID = ? ORDER BY scID DESC LIMIT 10";
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10개씩
			pstmt.setString(2, userID); //10개씩
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setScID(rs.getInt(1));
				schedule.setScTitle(rs.getString(2));
				schedule.setUserID(rs.getString(3));
				schedule.setScDate(rs.getString(4));
				schedule.setScContent(rs.getString(5));
				schedule.setScAvailable(rs.getInt(6));
				schedule.setScLength(rs.getInt(7));
				list.add(schedule);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list; //리스트 반환
	}
	
	public ArrayList<Rank> getRankList(){ //완성한 목표 목록
		String SQL = "select sc.userid, count(*) as cnt FROM (select * from schedule where scAvailable = 2) sc group by sc.userid order by cnt desc limit 5";
		ArrayList<Rank> list = new ArrayList<Rank>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Rank rank = new Rank();
				rank.setUserID(rs.getString(1));
				rank.setPoint(rs.getInt(2));
				list.add(rank);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list; //리스트 반환
	}
	
	public int getMyPoint(String userID) { //내 완성 포인트 가져오기
		String SQL = "SELECT COUNT(*) FROM SCHEDULE WHERE scAvailable = 2 AND userID = ?";
		try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, userID);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			point = rs.getInt(1);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return point;
	}
	
	public boolean nextPage(int pageNumber) { //다음페이지 함수
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 1 ORDER BY scID DESC LIMIT 10";
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
	
	public Schedule getSchedule(int scID) { //글 블러오기함수
		String SQL = "SELECT * FROM SCHEDULE WHERE scID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Schedule sc = new Schedule();
				sc.setScID(rs.getInt(1));
				sc.setScTitle(rs.getString(2));
				sc.setUserID(rs.getString(3));
				sc.setScDate(rs.getString(4));
				sc.setScContent(rs.getString(5));
				sc.setScAvailable(rs.getInt(6));
				sc.setScLength(rs.getInt(7));
				return sc;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null; //리스트 반환
	}
	
	public int update(int scID, String scTitle, String scContent) { //글 수정 함수
		String SQL = "UPDATE SCHEDULE SET scTitle = ?, scContent = ? WHERE scID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, scTitle);
			pstmt.setString(2, scContent);
			pstmt.setInt(3, scID);
			
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	public int delete(int scID) {
		String SQL = "UPDATE SCHEDULE SET scAvailable = 0 WHERE scID = ?"; //완전 삭제가 아닌 avialble을 0으로 바꿈
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
	
	public int complete(int scID) {
		String SQL = "UPDATE SCHEDULE SET scAvailable = 2 WHERE scID = ?"; //완료시 avilable을 2로 수정
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB오류
	}
}
