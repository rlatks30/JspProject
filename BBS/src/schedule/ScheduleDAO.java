package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import schedule.Schedule;
import schedule.Rank;

public class ScheduleDAO {
	private Connection conn; //Connection DB�� �����ϰ� ���ִ� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs;
	int point = 0;
	public ScheduleDAO() { //�ڵ����� �����ͺ��̽� Ŀ�ؼ��� �̷������
		try {//����ó�����ִ� try catch try�� ���ܰ� ������ ������� catch�� ���ܽ� ó��
			String dbURL = "jdbc:mysql://localhost:3306/BBS?useSSL=false"; // localhost:3306 �� pc�� ��ġ�� mysql������ �ǹ� BBS�� DB�����ϱ�
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver"); // mysql ����̹�(mysql�� �����Ҽ� �ֵ��� �Ű�ü ������ ���ִ� ���̺귯��) ã��
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // dbURL�� dbID�� dbPw�� �̿��ؼ� �����Ҽ��ֵ��� ��
		} catch (Exception e) {
			e.printStackTrace(); //������ ���
		}
	}
	
	
	public String getDate() { //�ð� ��������
		String SQL = "SELECT NOW()"; //���� �ð��������� ��ɾ�
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // DB����
	}
	
	public int getNext() { //�Խñ��� ��ȣ�� �ٿ���
		String SQL = "SELECT scID FROM SCHEDULE ORDER BY scID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //ù �Խù��� ���
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
	}
	
	public int write(String scTitle, String userID, String scContent, int scLength) {
		String SQL = "INSERT INTO SCHEDULE VALUES (?, ?, ?, ?, ?, ?, ?)"; //���̺� bbs�� 7���� values�� insert��
		try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext());//�� ��ȣ
				pstmt.setString(2, scTitle); //����
				pstmt.setString(3, userID);   //���̵�
				pstmt.setString(4, getDate()); //��¥
				pstmt.setString(5, scContent); //����
				pstmt.setInt(6, 1);    //���� ����
				pstmt.setInt(7, scLength);    //���, �ܱ�, ����  1,2,3
				
				return pstmt.executeUpdate(); //������Ʈ �Ȱ� return 
			}catch (Exception e) {
				e.printStackTrace();
			}
			return -1; // DB����
		}
	
	public ArrayList<Schedule> getList(int pageNumber,String userID){ //��ǥ ���
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 1 AND userID = ? ORDER BY scID DESC LIMIT 10";
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10����
			pstmt.setString(2, userID); //10����
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
		return list; //����Ʈ ��ȯ
		
	}
	
	public ArrayList<Schedule> getComList(int pageNumber,String userID){ //�ϼ��� ��ǥ ���
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 2 AND userID = ? ORDER BY scID DESC LIMIT 10";
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10����
			pstmt.setString(2, userID); //10����
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
		return list; //����Ʈ ��ȯ
	}
	
	public ArrayList<Rank> getRankList(){ //�ϼ��� ��ǥ ���
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
		return list; //����Ʈ ��ȯ
	}
	
	public int getMyPoint(String userID) { //�� �ϼ� ����Ʈ ��������
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
	
	public boolean nextPage(int pageNumber) { //���������� �Լ�
		String SQL = "SELECT * FROM SCHEDULE WHERE scID < ? AND scAvailable = 1 ORDER BY scID DESC LIMIT 10";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false; //����Ʈ ��ȯ
		
	}
	
	public Schedule getSchedule(int scID) { //�� �������Լ�
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
		return null; //����Ʈ ��ȯ
	}
	
	public int update(int scID, String scTitle, String scContent) { //�� ���� �Լ�
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
		return -1; // DB����
	}
	
	public int delete(int scID) {
		String SQL = "UPDATE SCHEDULE SET scAvailable = 0 WHERE scID = ?"; //���� ������ �ƴ� avialble�� 0���� �ٲ�
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
	}
	
	public int complete(int scID) {
		String SQL = "UPDATE SCHEDULE SET scAvailable = 2 WHERE scID = ?"; //�Ϸ�� avilable�� 2�� ����
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, scID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
	}
}
