package vehiclesurvey.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vehiclesurvey.SpeedCalculator;
import vehiclesurvey.TimePeriod;
import vehiclesurvey.VehicleDirection;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleDriveByRecordList;
import vehiclesurvey.VehicleSurveyRecordList;

public class DistanceReportService implements ReportService {

	private SpeedCalculator speedCalculator;

	public DistanceReportService() {
		this.speedCalculator = new SpeedCalculator();
	}
	
	@Override
	public Report[] generateReport(VehicleSurveyRecordList surveyRecordList) {
		Map<TimePeriod, Long[]> result = calculateDistanceInPeriod(processData(surveyRecordList));
		List<Report> reports = new ArrayList<>();
		for(TimePeriod period : TimePeriod.values()) {
			Long[] distances = result.get(period);
			reports.add(new DistanceReport(period, distances));
		}
		return reports.toArray(new Report[0]);
	}
	
	private List<Map<TimePeriod, VehicleDriveByRecordList[]>> processData(VehicleSurveyRecordList surveyRecordList) {
		VehicleSurveyRecordList[] surveyRecordInDays = surveyRecordList.groupByDays();
		List<Map<TimePeriod, VehicleDriveByRecordList[]>> data = new ArrayList<>();
		for (VehicleSurveyRecordList list : surveyRecordInDays) {
			VehicleDriveByRecordList driveByRecords = list.generateDriveByRecords();
			Map<TimePeriod, VehicleDriveByRecordList[]> driveByRecordsByPeriod = new HashMap<>();
			for (TimePeriod period : TimePeriod.values()) {
				VehicleDriveByRecordList[] driveByRecordLists = driveByRecords.groupByPeriod(period);
				driveByRecordsByPeriod.put(period, driveByRecordLists);
			}
			data.add(driveByRecordsByPeriod);
		}
		return data;
	}
	
	private Map<TimePeriod, Long[]> calculateDistanceInPeriod(List<Map<TimePeriod, VehicleDriveByRecordList[]>> driverRecordInDays) {
		Map<TimePeriod, Long[]> distancesInPeriod = new HashMap<>();
		for (TimePeriod period : TimePeriod.values()) {
			List<Long> resultForPeriod = new ArrayList<>();
			for (Map<TimePeriod, VehicleDriveByRecordList[]> map : driverRecordInDays) {
				VehicleDriveByRecordList[] vehicleDriveByRecordLists = map.get(period);
				List<Long> result = new ArrayList<>();
				for (int i = 0; i < vehicleDriveByRecordLists.length; i++) {
					Map<VehicleDirection, VehicleDriveByRecordList> directionMap = vehicleDriveByRecordLists[i].groupByDirection();
					result.add(
							(calculateAverageDistance(directionMap.get(VehicleDirection.NORTH_BOUND)) + 
							calculateAverageDistance(directionMap.get(VehicleDirection.SOUTH_BOUND))) / 2);
				}
				addUpResult(resultForPeriod, result);
			}
			for (int i = 0; i < resultForPeriod.size(); i++) {
				Long r = resultForPeriod.get(i);
				resultForPeriod.set(i, r / driverRecordInDays.size());
			}
			distancesInPeriod.put(period, resultForPeriod.toArray(new Long[0]));
		}
		return distancesInPeriod;
	}
	
	private long calculateAverageDistance(VehicleDriveByRecordList list) {
		long distance = 0;
		for (int i = 1; i < list.size(); i++) {
			VehicleDriveByRecord previous = list.get(i - 1);
			VehicleDriveByRecord current = list.get(i);
			distance += speedCalculator.distanceBetween(previous, current);
		}
		return list.size() <= 1 ? 0 : distance / list.size();
	}
	
	private void addUpResult(List<Long> resultForPeriod, List<Long> result) {
		if (resultForPeriod.isEmpty()) {
			resultForPeriod.addAll(result);
			return;
		}
		for(int i = 0; i < resultForPeriod.size(); i++) {
			resultForPeriod.set(i, resultForPeriod.get(i) + result.get(i));
		}
	}
}