package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;


public class UserDAO { //�����ͺ��̽� ���ٰ�ü , DB�� ȸ������ �θ���, DBȸ������ �ֱ�
	
	private Connection conn; //Connection DB�� �����ϰ� ���ִ� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() { //�ڵ����� �����ͺ��̽� Ŀ�ؼ��� �̷������
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
	
	public int login(String userID, String userPassword) { //������ �α����� �õ��ϴ� �Լ� id�� pw�� �޾Ƽ� ó���Ҽ��ִ�.
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //������ DB�� �Է��� ��ɾ SQL�������� ���� , USER ���̺��� PW��������
		try {
			pstmt = conn.prepareStatement(SQL); //preparestatment�� � ������ ������ DB�� ����
			pstmt.setString(1, userID	); //�߿��Ѻκ� * SQL������ ��ŷ��� ����ϱ����� ��� 
			rs = pstmt.executeQuery(); //����� �������ִ� ��ü�� ������ ��� ���
			if (rs.next()) { // ����� �����ϴ��� �Ǻ� ���������� ȸ���� ������ �������� ������ return��Ų��.
				if(rs.getString(1).equals(userPassword)) { //�Է¹��� Pw�� �����Pw�� �����ϴٸ� �α��ν�Ų��. 
					return 1; //�α��� ����
				}
				else
					return 0; //�α��� ���� , ��й�ȣ ����ġ
			}
			return -1; // ���̵� ����
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽� ����
	
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
		return -1; // �����ͺ��̽� ����
	}
}
