package vehiclesurvey.reports;

import java.util.Arrays;

import vehiclesurvey.TimePeriod;

public class TotalVehicleReport extends AbstractReport implements Report {

	private static final String TITLE = "Total Vehicle Counts Report -";

	private VehicleCount[] result;
	private int day;

	public TotalVehicleReport(TimePeriod period, VehicleCount[] count, int day) {
		super(period);
		this.result = count;
		this.day = day;
	}

	@Override
	public String[] getHeaders() {
		int range = getRange() / 2 + 1;
		String[] headers = new String[range];
		for (int i = 1; i < range; i++) {
			headers[i] = String.format("  #%02d", i);
		}
		headers[0] = "Range";
		return headers;
	}
	
	@Override
	public String getHeaderFormat() {
		StringBuilder sb = new StringBuilder("%10s");
		int range = getRange() / 2 + 1;
		for (int i = 1; i < range; i++) {
			sb.append("%10s");
		}
		sb.append("%n");
		return sb.toString();
	}
	
	@Override
	public String[][] getRows() {
		String[][] rows = new String[2][];
		rows[0] = morningRow(this.result);
		rows[1] = eveningRow(this.result);
		return rows;
	}

	private String[] morningRow(VehicleCount[] vehicleCounts) {
		VehicleCount[] counts = Arrays.asList(vehicleCounts)
				.subList(0, vehicleCounts.length / 2)
				.toArray(new VehicleCount[0]);

		return generateRow(counts, "Morning");
	}

	private String[] eveningRow(VehicleCount[] vehicleCounts) {
		VehicleCount[] counts = Arrays.asList(vehicleCounts)
				.subList(vehicleCounts.length / 2, vehicleCounts.length)
				.toArray(new VehicleCount[0]);
		return generateRow(counts, "Evening");
	}
	
	private String[] generateRow(VehicleCount[] vehicleCounts, String rowLable) {
		String[] row = new String[vehicleCounts.length + 1];
		row[0] = rowLable;
		for (int i = 1; i <= vehicleCounts.length; i++) {
			VehicleCount count = vehicleCounts[i - 1];
			row[i] = String.format("%d/%d", count.getNorthboundCount(), count.getSouthboundCount());
		}
		return row;
	}

	@Override
	public String getRowFormat() {
		return this.getHeaderFormat();
	}

	@Override
	public String getTitle() {
		return TITLE + " " + getPeriod().description() + 
				" (Day " + day + ")" +
				" (Northbound/Southbound)";
	}
}