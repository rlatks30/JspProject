package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {


	private Connection conn; //Connection DB�� �����ϰ� ���ִ� ��ü
	private ResultSet rs;

	public BbsDAO() { //�ڵ����� �����ͺ��̽� Ŀ�ؼ��� �̷������ 
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
	
	public FileDTO download(int bbsID) {//bbs ID�� �Է¹޾� filename,realname,bbsid�� set���� �̺�
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
		return null; //����Ʈ ��ȯ
		
	}

	public int upload(String fileName, String fileRealName) {
		String SQL = "INSERT INTO FILE VALUES (?,?,?)"; // ���� ���ε�
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setInt(3, getNext());
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
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
		return ""; // DB����
	}
	
	
	public int getNext() { //�Խñ��� ��ȣ�� �ٿ���
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
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
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)"; //���̺� bbs�� 6���� values�� insert��
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());//�� ��ȣ
			pstmt.setString(2, bbsTitle); //����
			pstmt.setString(3, userID);   //���̵�
			pstmt.setString(4, getDate()); //��¥
			pstmt.setString(5, bbsContent); //����
			pstmt.setInt(6, 1);    //���� ����
			
			return pstmt.executeUpdate(); //������Ʈ �Ȱ� return 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
	}
	
	public ArrayList<Bbs> getList(int pageNumber){ //�۸��
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); //10����
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
		return list; //����Ʈ ��ȯ
		
	}
	
	public boolean nextPage(int pageNumber) { //���������� �Լ�
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
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
	
	public Bbs getBbs(int bbsID) { //�� �������Լ�
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
		return null; //����Ʈ ��ȯ
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) { //�� ���� �Լ�
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
		return -1; // DB����
	}
	
	
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?"; //���� ������ �ƴ� avialble�� 0���� �ٲ�
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB����
	}
	
}
