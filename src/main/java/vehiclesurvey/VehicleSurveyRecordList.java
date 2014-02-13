package vehiclesurvey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VehicleSurveyRecordList extends ArrayList<VehicleSurveyRecord> {
	
	private static final long serialVersionUID = 4898984540949171496L;
	
	public VehicleSurveyRecordList() {
	}

	public VehicleSurveyRecordList(String filepath) throws FileNotFoundException, IOException {
		this();
		load(filepath);
	}

	private void load(String filepath) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line = br.readLine();
			while (line != null) {
				this.add(VehicleSurveyRecord.createInstance(line));
				line = br.readLine();
			}
		}
	}

	public VehicleSurveyRecordList[] groupByDays() {
		List<VehicleSurveyRecordList> buckets = new ArrayList<>();
		Iterator<VehicleSurveyRecord> iterator = this.iterator();
		VehicleSurveyRecord previousRecord = null;
		int bucketCount = 0;
		buckets.add(bucketCount, new VehicleSurveyRecordList());
		while (iterator.hasNext()) {
			VehicleSurveyRecord record = iterator.next();
			if (previousRecord == null || record.appearLaterThan(previousRecord)) {
				previousRecord = record;
			}else {
				previousRecord = null;
				bucketCount++;
				buckets.add(bucketCount, new VehicleSurveyRecordList());
			}
			buckets.get(bucketCount).add(record);
		}
		return buckets.toArray(new VehicleSurveyRecordList[0]);
	}

	public VehicleDriveByRecordList generateDriveByRecords() {
		VehicleDriveByRecordList driveByRecords = new VehicleDriveByRecordList();
		VehicleDriveByRecord driveByRecord = null;
		for (VehicleSurveyRecord surveyRecord : this) {
			if (driveByRecord == null || !driveByRecord.isSameVehicle(surveyRecord)) {
				driveByRecord = new VehicleDriveByRecord();
				driveByRecords.add(driveByRecord);
			}
			driveByRecord.addSurveyCode(surveyRecord);
		}
		print(driveByRecords);
		return driveByRecords;
	}
	
	private void print(VehicleDriveByRecordList driveByRecords) {
		for (VehicleDriveByRecord record : driveByRecords) {
			if (record.getSurveyRecords().size() % 2 != 0) {
				System.out.println(record.getDirection() + "  " + record.getSurveyRecords().size());
				for (VehicleSurveyRecord surveyRecord : record.getSurveyRecords()) {
					System.out.println(surveyRecord.getSensor() + "" + surveyRecord.getTimeMark());
				}
			}
		}
	}
}