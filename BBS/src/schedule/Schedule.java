package schedule;

public class Schedule {

	private int scID;
	private String scTitle;
	private String userID;
	private String scDate;
	private String scContent;
	private int scAvailable;
	private int scLength;
	private String scLengthKr;
	
	public int getScID() {
		return scID;
	}
	public void setScID(int scID) {
		this.scID = scID;
	}
	public String getScTitle() {
		return scTitle;
	}
	public void setScTitle(String scTitle) {
		this.scTitle = scTitle;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getScDate() {
		return scDate;
	}
	public void setScDate(String scDate) {
		this.scDate = scDate;
	}
	public String getScContent() {
		return scContent;
	}
	public void setScContent(String scContent) {
		this.scContent = scContent;
	}
	public int getScAvailable() {
		return scAvailable;
	}
	public void setScAvailable(int scAvailable) {
		this.scAvailable = scAvailable;
	}	
	public int getScLength() {
		return scLength;
	}
	public void setScLength(int scLength) { 
		this.scLength = scLength;
	}
	public String getScLengthKr() {
		return scLengthKr;
	}
	public void setScLengthKr(String scLengthKr) {
		this.scLengthKr = scLengthKr;
	}
	
}
