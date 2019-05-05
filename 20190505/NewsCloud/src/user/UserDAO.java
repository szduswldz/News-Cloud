package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {
    
    private Connection conn;
    private PreparedStatement pstmt; //해킹방어수단
    private ResultSet rs;
 
    public UserDAO(){
        try {
            String dbURL = "jdbc:mysql://localhost:3306/newscloud?useSSL=false&useUnicode=true&characterEncoding=euckr"; 
            //bbs데이터베이스에 접속하는 3306포트(=내컴퓨터의 mysql포트)
            String dbID = "root"; 
            String dbPassword="0000";//자신이 설정한 root 비밀번호 입력
            Class.forName("org.gjt.mm.mysql.Driver"); //Driver는 매개체역할 Library
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);//데이터베이스에 접속하는부분
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int login(String userID, String userPassword) {
        String SQL = "SELECT userPassword FROM USER WHERE userID=?";
        //데이터 베이스에서 입력 받은 userID에 맞는 userPasswor가 있는지 확인하는 과정
        try {
            pstmt = conn.prepareStatement(SQL);//conn으로 데이터베이스에 접속
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if(rs.getString(1).equals(userPassword)) {
                    return 1; //로그인 성공
                }
                else
                    return 0;//비밀번호 불일치
            }
            return -1; //아이디 없음
        }catch(Exception e) {
            e.printStackTrace();
        }
        return -2; //데이터베이스 오류
    }
    public int join(User user) {
        String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)"; //INSERT문은 반드시 0이상값이 반환된다
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserPhoneNumber());
            pstmt.setString(5, user.getUserEmail());
            return pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return -1; //데이터베이스 오류
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