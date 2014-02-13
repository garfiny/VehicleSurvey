package vehiclesurvey.reports;

import java.util.ArrayList;
import java.util.List;

import vehiclesurvey.TimePeriod;
import vehiclesurvey.VehicleDirection;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleDriveByRecordList;
import vehiclesurvey.VehicleSurveyRecordList;

public class TotalVehicleReportService implements ReportService {


	public TotalVehicleReportService() {
	}

	@Override
	public Report[] generateReport(VehicleSurveyRecordList surveyRecordList) {
		VehicleSurveyRecordList[] surveyRecordsInDays = surveyRecordList.groupByDays();
		List<Report> reports = new ArrayList<>();
		for (TimePeriod period : TimePeriod.values()) {
			for (int i = 0; i < surveyRecordsInDays.length; i++) {
				VehicleDriveByRecordList driveByRecords = surveyRecordsInDays[i].generateDriveByRecords();
				reports.add(new TotalVehicleReport(period, countVehicles(driveByRecords, period), (i+1)));
			}
		}
		return reports.toArray(new Report[0]);
	}
	
	private VehicleCount[] countVehicles(VehicleDriveByRecordList driveByRecords, TimePeriod period) {
		VehicleDriveByRecordList[] lists = driveByRecords.groupByPeriod(period);
		VehicleCount[] result = new VehicleCount[lists.length];
		for (int i = 0; i < lists.length; i++) {
			result[i] = countByDirection(lists[i]);
		}
		return result;
	}

	private VehicleCount countByDirection(List<VehicleDriveByRecord> list) {
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
		vehicleCount.setNorthboundCount(ncount);
		vehicleCount.setSouthboundCount(scount);
		return vehicleCount;
	}
}