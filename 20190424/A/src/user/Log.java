package user;

public class Log {
	private String UserID;
	private String Press;
	private String Category;
	private String SubCategory;
	private String Datetime;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getPress() {
		return Press;
	}
	public void setPress(String press) {
		Press = press;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getSubCategory() {
		return SubCategory;
	}
	public void setSubCategory(String subCategory) {
		SubCategory = subCategory;
	}
	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
}