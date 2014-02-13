package vehiclesurvey;

public enum TimePeriod {
	
	HOUR(60, "Per Hour"), 
	HALF_HOUR(30, "Per Half Hour"), 
	ONE_THIRD_HOUR(20, "Per 20 minutes"), 
	QUARTER(15, "Per 15 minutes");
	
	private long minutes;
	private String description;

	TimePeriod(long minutes, String description) {
		this.minutes = minutes;
		this.description = description;
	}
	
	public long minutesOfPeriod() {
		return minutes;
	}
	
	public long secondsOfPeriod() {
		return minutesOfPeriod() * 60;
	}
	
	public long millisOfPeriod() {
		return secondsOfPeriod() * 1000;
	}
	
	public String description() {
		return this.description;
	}
}