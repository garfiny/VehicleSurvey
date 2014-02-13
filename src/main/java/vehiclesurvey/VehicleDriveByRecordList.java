package vehiclesurvey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VehicleDriveByRecordList extends ArrayList<VehicleDriveByRecord> {

	private static final long serialVersionUID = -5036942193296000575L;
	private static final long MINUTES_OF_DAY = 24 * 60;

	public VehicleDriveByRecordList() {
	}

	public VehicleDriveByRecordList[] groupByPeriod(TimePeriod period) {
		int numberOfBuckets = (int) (MINUTES_OF_DAY / period.minutesOfPeriod());
		VehicleDriveByRecordList[] buckets = new VehicleDriveByRecordList[numberOfBuckets];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new VehicleDriveByRecordList();
		}
		for (VehicleDriveByRecord record : this) {
			int index = calculateBucketIndex(record, period);
			buckets[index].add(record);
		}
		return buckets;
	}
	
	public Map<VehicleDirection , VehicleDriveByRecordList> groupByDirection() {
		Map<VehicleDirection , VehicleDriveByRecordList> map = new HashMap<>();
		map.put(VehicleDirection.NORTH_BOUND, new VehicleDriveByRecordList());
		map.put(VehicleDirection.SOUTH_BOUND, new VehicleDriveByRecordList());
		for (VehicleDriveByRecord record : this) {
			map.get(record.getDirection()).add(record);
		}
		return map;
	}

	private int calculateBucketIndex(VehicleDriveByRecord record, TimePeriod period) {
		return (int) (record.getDriveByTimeInMillis() / period.millisOfPeriod());
	}
}