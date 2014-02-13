package vehiclesurvey.reports;

import vehiclesurvey.TimePeriod;

public class DistanceReport extends AbstractReport implements Report {

	private static final String TITLE = "Distance between cars Report -";
	
	private Long[] distances;

	public DistanceReport(TimePeriod period, Long[] distances) {
		super(period);
		this.distances = distances;
	}

	@Override
	public String[][] getRows() {
		String[][] rows = new String[1][];
		int range = getRange();
		rows[0] = new String[range];
		rows[0][0] = "Distances(km)";
		for (int i = 1; i < range; i++) {
			rows[0][i] = String.format("%2.2f", distances[i - 1] / 1000.0);
		}
		return rows;
	}

	@Override
	public String getRowFormat() {
		return this.getHeaderFormat();
	}

	@Override
	public String getTitle() {
		return TITLE + " " + getPeriod().description();
	}
}
