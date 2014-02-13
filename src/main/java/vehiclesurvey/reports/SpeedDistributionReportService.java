package vehiclesurvey.reports;

import static vehiclesurvey.reports.SpeedDistributionReport.MAX_SPEED;
import static vehiclesurvey.reports.SpeedDistributionReport.MIN_SPEED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vehiclesurvey.SpeedCalculator;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleSurveyRecordList;

public class SpeedDistributionReportService implements ReportService {
	
	private SpeedCalculator speedCalculator;

	public SpeedDistributionReportService() {
		this.speedCalculator = new SpeedCalculator();
	}
	
	@Override
	public Report[] generateReport(VehicleSurveyRecordList surveyRecordList) {
		List<VehicleDriveByRecord> driveByRecords = surveyRecordList.generateDriveByRecords();
		
		Map<Integer, Integer> speedDistributions = new HashMap<>();
		for (VehicleDriveByRecord record : driveByRecords) {
			long speedInKM = speedCalculator.calculateSpeed(record.timeUsedInMillis()) / 1000;
			Integer range = chooseRange(speedInKM);
			Integer rangeCount = speedDistributions.get(range);
			if (rangeCount == null) {
				rangeCount = Integer.valueOf(1);
			}else {
				rangeCount = rangeCount + 1;
			}
			speedDistributions.put(range, rangeCount);
		}
		return new SpeedDistributionReport[] { new SpeedDistributionReport(speedDistributions) };
	}
	
	private int chooseRange(long speedInKM) {
		if(speedInKM <= MIN_SPEED) return MIN_SPEED ;
		if (speedInKM >= MAX_SPEED) return MAX_SPEED;
		return (int) (speedInKM - speedInKM % 10);
	}
}
