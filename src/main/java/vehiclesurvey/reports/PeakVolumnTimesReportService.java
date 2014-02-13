package vehiclesurvey.reports;

import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vehiclesurvey.TimePeriod;
import vehiclesurvey.VehicleDirection;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleDriveByRecordList;
import vehiclesurvey.VehicleSurveyRecordList;

public class PeakVolumnTimesReportService implements ReportService {

	@Override
	public Report[] generateReport(VehicleSurveyRecordList surveyRecordList) {
		VehicleSurveyRecordList[] surveyRecordsInDays = surveyRecordList
				.groupByDays();
		List<Report> reports = new ArrayList<>();
		for (TimePeriod period : TimePeriod.values()) {
			for (int i = 0; i < surveyRecordsInDays.length; i++) {
				VehicleDriveByRecordList driveByRecords = surveyRecordsInDays[i]
						.generateDriveByRecords();
				VehicleCount[] result = countVehicles(driveByRecords, period);
				VehicleCount[] countsInMorning = Arrays.asList(result)
						.subList(0, result.length / 2)
						.toArray(new VehicleCount[0]);
				VehicleCount[] countsInEvening = Arrays.asList(result)
						.subList(result.length / 2, result.length)
						.toArray(new VehicleCount[0]);
				reports.add(new PeakVolumeTimesReport(period, i + 1,
						findPeakVolumn(countsInMorning, period),
						findPeakVolumn(countsInEvening, period)));
			}
		}
		return reports.toArray(new Report[0]);
	}

	private PeakVolumnTime findPeakVolumn(VehicleCount[] list, TimePeriod period) {
		VehicleCount peakNorthbound = Collections.max(Arrays.asList(list),
				new Comparator<VehicleCount>() {

					@Override
					public int compare(VehicleCount o1, VehicleCount o2) {
						return o1.getNorthboundCount()
								- o2.getNorthboundCount();
					}
				});
		VehicleCount peakSouthbound = Collections.max(Arrays.asList(list),
				new Comparator<VehicleCount>() {

					@Override
					public int compare(VehicleCount o1, VehicleCount o2) {
						return o1.getSouthboundCount()
								- o2.getSouthboundCount();
					}
				});
		PeakVolumnTime peakVolumnTime = new PeakVolumnTime();
		peakVolumnTime.setNorthboundCount(peakNorthbound.getNorthboundCount());
		peakVolumnTime.setNorthboundTime(getPeriodString(period,
				peakNorthbound.getIndexOfPeriod()));
		peakVolumnTime.setSouthboundCount(peakSouthbound.getSouthboundCount());
		peakVolumnTime.setSouthboundTime(getPeriodString(period,
				peakSouthbound.getIndexOfPeriod()));
		return peakVolumnTime;
	}

	private String getPeriodString(TimePeriod period, int index) {
		long millis = period.millisOfPeriod() * (index + 1);
		Calendar cal = Calendar.getInstance();
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		cal.setTimeInMillis(cal.getTimeInMillis() + millis);
		return String.format("%02d:%02d", cal.get(HOUR), cal.get(MINUTE));
	}

	private VehicleCount[] countVehicles(
			VehicleDriveByRecordList driveByRecords, TimePeriod period) {
		VehicleDriveByRecordList[] lists = driveByRecords.groupByPeriod(period);
		VehicleCount[] result = new VehicleCount[lists.length];
		for (int i = 0; i < lists.length; i++) {
			result[i] = countByDirection(lists[i], i);
		}
		return result;
	}

	private VehicleCount countByDirection(List<VehicleDriveByRecord> list,
			int indexOfPeriod) {
		VehicleCount vehicleCount = new VehicleCount();
		int ncount = 0;
		int scount = 0;
		for (VehicleDriveByRecord vehicleDriveByRecord : list) {
			if (vehicleDriveByRecord.getDirection() == VehicleDirection.NORTH_BOUND) {
				ncount++;
			} else {
				scount++;
			}
		}
		vehicleCount.setNorthboundCount(ncount);
		vehicleCount.setSouthboundCount(scount);
		vehicleCount.setIndexOfPeriod(indexOfPeriod);
		return vehicleCount;
	}
}
