package vehiclesurvey;


public class VehicleSurveyRecord {

	private char sensor;
	private long timeMark;
	
	public VehicleSurveyRecord(char sensor, long timeMark) {
		this.sensor = sensor;
		this.timeMark = timeMark;
	}
	
	public static VehicleSurveyRecord createInstance(String recordStr) {
		return new VehicleSurveyRecord(recordStr.charAt(0), Long.parseLong(recordStr.substring(1)));
	}
	
	public boolean appearLaterThan(VehicleSurveyRecord record) {
		return this.timeMark > record.timeMark;
	}
	
	public boolean isSameSensor(VehicleSurveyRecord record) {
		return this.sensor == record.sensor;
	}
	
	public long timeDifferences(VehicleSurveyRecord record) {
		return Math.abs(this.timeMark - record.timeMark);
	}
	
	public char getSensor() {
		return sensor;
	}
	public long getTimeMark() {
		return timeMark;
	}
}