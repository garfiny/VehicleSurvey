package vehiclesurvey.reports;

public class PeakVolumnTime {

	private String northboundTime;
	private int northboundCount;
	private String southboundTime;
	private int southboundCount;
	
	public String getNorthboundTime() {
		return northboundTime;
	}
	public void setNorthboundTime(String northboundTime) {
		this.northboundTime = northboundTime;
	}
	public int getNorthboundCount() {
		return northboundCount;
	}
	public void setNorthboundCount(int northboundCount) {
		this.northboundCount = northboundCount;
	}
	public String getSouthboundTime() {
		return southboundTime;
	}
	public void setSouthboundTime(String southboundTime) {
		this.southboundTime = southboundTime;
	}
	public int getSouthboundCount() {
		return southboundCount;
	}
	public void setSouthboundCount(int southboundCount) {
		this.southboundCount = southboundCount;
	}

}
