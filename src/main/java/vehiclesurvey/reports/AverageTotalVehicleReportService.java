package vehiclesurvey.reports;

import java.util.ArrayList;
import java.util.List;

import vehiclesurvey.TimePeriod;
import vehiclesurvey.VehicleDirection;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleDriveByRecordList;
import vehiclesurvey.VehicleSurveyRecordList;

public class AverageTotalVehicleReportService implements ReportService {


	public AverageTotalVehicleReportService() {
	}

	@Override
	public Report[] generateReport(VehicleSurveyRecordList surveyRecordList) {
		VehicleDriveByRecordList driveByRecords = surveyRecordList.generateDriveByRecords();
		int numberOfDays = numberOfDays(surveyRecordList);
		List<Report> reports = new ArrayList<>();
		for (TimePeriod period : TimePeriod.values()) {
			reports.add(
					new AverageTotalVehicleReport(
							period, countVehicles(driveByRecords, period, numberOfDays)));
		}
		return reports.toArray(new Report[0]);
	}
	
	private int numberOfDays(VehicleSurveyRecordList surveyRecordList) {
		return surveyRecordList.groupByDays().length;
	}
	
	private VehicleCount[] countVehicles(
			VehicleDriveByRecordList driveByRecords, TimePeriod period, int numberOfDays) {
		VehicleDriveByRecordList[] lists = driveByRecords.groupByPeriod(period);
		VehicleCount[] result = new VehicleCount[lists.length];
		for (int i = 0; i < lists.length; i++) {
			result[i] = countByDirection(lists[i], numberOfDays);
		}
		return result;
	}

	private VehicleCount countByDirection(List<VehicleDriveByRecord> list, int numberOfDays) {
		VehicleCount vehicleCount = new VehicleCount();
		int ncount = 0;
		int scount = 0;
		for (VehicleDriveByRecord vehicleDriveByRecord : list) {
			if (vehicleDriveByRecord.getDirection() == VehicleDirection.NORTH_BOUND) {
				ncount++;
			}else {
				scount++;
			}
		}
		vehicleCount.setNorthboundCount(ncount / numberOfDays);
		vehicleCount.setSouthboundCount(scount / numberOfDays);
		return vehicleCount;
	}
}