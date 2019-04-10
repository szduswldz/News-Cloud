package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt; 
	private ResultSet rs;

	public UserDAO(){
        try {  
            String dbURL = " http://localhost:8080/A/log.html"; 
           
            String dbID = "root";
            String dbPassword="zlanek12";//�ڽ��� ������ root ��й�ȣ �Է�
            Class.forName("com.mysql.jdbc.Driver"); //Driver�� �Ű�ü���� Library
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);//�����ͺ��̽��� �����ϴºκ�
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	public int login(String userID, String userPassword) {
		String SQL = "select userPassword from user where userID = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // �α��� ����
				}
				else return 0; // ��й�ȣ ����ġ
			}
			return -1; // ���̵� ����
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; // �����ͺ��̽� ����
	}



public int join(User user) {
	String SQL = "insert into user VALUES (?, ?, ?, ?, ?)";

	try {
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, user.getUserID());
		pstmt.setString(2, user.getUserPassword());
		pstmt.setString(3, user.getUserName());
		pstmt.setString(4, user.getUserPhoneNumber());
		pstmt.setString(5, user.getUserEmail());
		return pstmt.executeUpdate(); // 0�̻��� ���ڰ� ��ȯ

	} catch(Exception e) {
		e.printStackTrace();
	}
	return -1; // �����ͺ��̽� ����
}

public User getUser(String userID) {
	String SQL = "select * from user where userID = ?";
	User user = new User();
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, userID);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			user.setUserID(rs.getString(1));
			user.setUserPassword(rs.getString(2));
			user.setUserName(rs.getString(3));
			user.setUserPhoneNumber(rs.getString(4));
			user.setUserEmail(rs.getString(6));
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return user; 
}
}