package vehiclesurvey;

public class SpeedCalculator {
	
	public static final double VEHICLE_LENGTH = 2.5;
	public static final long SPEED_LIMIT = 10000; // Meters
	
	private static final int MILLIS_PER_SECOND = 1000;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int MINUTES_PER_HOUR = 60;
	private static final long MILLIS_PER_HOUR = MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
	
	public static final long SPEED_COMPLEMENT = 50;
	public static final long SPEED_THRESHOLD;
	static {
		SPEED_THRESHOLD = (long) (1 / ((double)SPEED_LIMIT / (double)MILLIS_PER_HOUR) * VEHICLE_LENGTH) + SPEED_COMPLEMENT;
	}
	
	public long calculateSpeed(long passingTime) {
		return (long) (1 / (passingTime / VEHICLE_LENGTH) * MILLIS_PER_HOUR);
	}

	public long distanceBetween(VehicleDriveByRecord first, VehicleDriveByRecord second) {
		if (first.getDriveByTimeInMillis() < second.getDriveByTimeInMillis()) {
			VehicleDriveByRecord record = first;
			first = second;
			second = record;
		}
		long timeDifference = first.getDriveByTimeInMillis() - second.getDriveByTimeInMillis();
		long speedInMeters = this.calculateSpeed(second.timeUsedInMillis());
		return (long)(speedInMeters * timeInHours(timeDifference));
	}
	
	private double timeInHours(long timeInMillis) {
		return (double)timeInMillis / (double)MILLIS_PER_HOUR;
	}
}