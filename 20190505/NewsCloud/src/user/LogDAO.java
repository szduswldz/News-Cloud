package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LogDAO {
    private Connection conn;
       private PreparedStatement pstmt; //해킹방어수단
       private ResultSet rs;

public LogDAO(){
   
    try {
         String dbURL = "jdbc:mysql://localhost:3306/A?serverTimezone=UTC"; 
         //bbs데이터베이스에 접속하는 3306포트(=내컴퓨터의 mysql포트)
         String dbID = "root";
         String dbPassword="0000";//자신이 설정한 root 비밀번호 입력
         Class.forName("com.mysql.jdbc.Driver"); //Driver는 매개체역할 Library
         conn = DriverManager.getConnection(dbURL, dbID, dbPassword);//데이터베이스에 접속하는부분
         
         
     }catch(Exception e){
         e.printStackTrace();
     }
}
   
   public int logging(String userID, String press, String category, String subCategory, String dateTime) {
      String SQL = "insert into log VALUES (?, ?, ?, ?, ?)";

      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         pstmt.setString(2, press);
         pstmt.setString(3, category);
         pstmt.setString(4, subCategory);
         pstmt.setString(5, dateTime);
         return pstmt.executeUpdate(); // 0이상의 숫자가 반환

      } catch(Exception e) {
         e.printStackTrace();
      }
      return -1; // 데이터베이스 오류
   }

   public ArrayList<String> getPress(String userID, String order) {
      String SQL = "select l.press, count(*) from rprj.log l where userID = ? group by l.press having count(*)="
            + "(SELECT max(count) FROM "
            + "(select l.press, count(*) as count from rprj.log l where userID = ? group by l.press) as temp) - " + order + ";";
      ArrayList<String> press = new ArrayList<String>();
      try {
         PreparedStatement pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         pstmt.setString(2, userID);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            press.add(rs.getString(1));
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      return press; 
   }
   
   public String getCategory(String userID) {
      String SQL = "select l.category, count(*) from rprj.log l where userID = ? group by l.press having count(*)="
            + "(SELECT max(count) FROM "
            + "(select l.press, count(*) as count from rprj.log l where userID = ? group by l.press) as temp);";
      ArrayList<String> cate = new ArrayList<String>();
      try {
         PreparedStatement pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         pstmt.setString(2, userID);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            cate.add(rs.getString(1));
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      return cate.get((int) (Math.random()*cate.size())); 
   }
   
   public String getSubCategory(String userID, String cate) {
      String SQL = "select l.subCategory, count(*) from rprj.log l where userID = ? and category = ? group by l.press having count(*)="
            + "(SELECT max(count) FROM "
            + "(select l.press, count(*) as count from rprj.log l where userID = ? and category = ? group by l.press) as temp);";
      ArrayList<String> subCate = new ArrayList<String>();
      try {
         PreparedStatement pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         pstmt.setString(2, cate);
         pstmt.setString(3, userID);
         pstmt.setString(4, cate);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            subCate.add(rs.getString(1));
         } 

      } catch (Exception e) {
         e.printStackTrace();
      }
      return subCate.get((int) (Math.random()*subCate.size())); 
   }
   
   public int getLogCount(String userID) {
      String SQL = "select count(*) as count from rprj.log l where userID = ?;";
      int count = 0;
      try {
         PreparedStatement pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            count = rs.getInt(1);
         } 

      } catch (Exception e) {
         e.printStackTrace();
      }
      return count; 
   }

}