package user;

public class User {

	private String UserID;
	private String UserPassword;
	private String UserName;
	private String UserPhoneNumber;
	private String UserEmail;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPhoneNumber() {
		return UserPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		UserPhoneNumber = userPhoneNumber;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
}