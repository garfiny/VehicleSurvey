package vehiclesurvey;

import java.util.LinkedList;
import java.util.List;

public class VehicleDriveByRecord {
	
	private VehicleDirection direction;

	private List<VehicleSurveyRecord> surveyRecords;

	public VehicleDriveByRecord() {
		this.surveyRecords = new LinkedList<>();
	}

	public long timeUsedInMillis() {
		if (this.getDirection() == VehicleDirection.NORTH_BOUND) {
			return surveyRecords.get(0).timeDifferences(surveyRecords.get(1));
		}else {
			return surveyRecords.get(0).timeDifferences(surveyRecords.get(2));
		}
	}

	public boolean isSameVehicle(VehicleSurveyRecord surveyRecord) {
		if (surveyRecords.isEmpty()) return true;
		VehicleSurveyRecord last = ((LinkedList<VehicleSurveyRecord>)surveyRecords).getLast();
		return (last.timeDifferences(surveyRecord) <= SpeedCalculator.SPEED_THRESHOLD);
	}

	public void addSurveyCode(VehicleSurveyRecord surveyRecord) {
		surveyRecords.add(surveyRecord);
	}
	
	public long getDriveByTimeInMillis() {
		return ((LinkedList<VehicleSurveyRecord>)surveyRecords).getLast().getTimeMark();
	}
	
	public VehicleDirection getDirection() {
		if (this.direction == null) {
			this.direction = inferDriection();
		}
		return this.direction;
	}
	
	private VehicleDirection inferDriection() {
		if (surveyRecords.size() == 2 && 
				surveyRecords.get(0).isSameSensor(surveyRecords.get(1))) {
			return VehicleDirection.NORTH_BOUND;
		}else {
			return VehicleDirection.SOUTH_BOUND;
		}
	}

	public List<VehicleSurveyRecord> getSurveyRecords() {
		return surveyRecords;
	}
}