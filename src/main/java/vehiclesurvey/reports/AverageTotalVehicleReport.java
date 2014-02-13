package vehiclesurvey.reports;

import vehiclesurvey.TimePeriod;

public class AverageTotalVehicleReport extends TotalVehicleReport implements Report {


	private static final String TITLE = "Average Total Vehicle Counts Report -";

	public AverageTotalVehicleReport(TimePeriod period, VehicleCount[] count) {
		super(period, count, 0);
	}

	@Override
	public String getTitle() {
		return TITLE + " " + getPeriod().description() + " (Northbound/Southbound)";
	}
}