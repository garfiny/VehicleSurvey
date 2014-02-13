package vehiclesurvey.reports;

import vehiclesurvey.TimePeriod;

public class PeakVolumeTimesReport extends AbstractReport implements Report {

	private static final String TITLE = "Peak Volume Times Report";
	private static final int TABLE_COLUMN_NUM = 5;
	private int day;
	private PeakVolumnTime morning;
	private PeakVolumnTime evening;

	public PeakVolumeTimesReport(
			TimePeriod period, int day, 
			PeakVolumnTime morning, 
			PeakVolumnTime evening) {
		super(period);
		this.day = day;
		this.morning = morning;
		this.evening = evening;
	}

	@Override
	public String[] getHeaders() {
		String[] headers = new String[TABLE_COLUMN_NUM];
		headers[0] = "";
		headers[1] = String.format("%18s", "Northbound-Time");
		headers[2] = String.format("%18s", "Northbound-Count");
		headers[3] = String.format("%18s", "Southbound-Time");
		headers[4] = String.format("%18s", "Southbound-Count");
		return headers;
	}
	
	@Override
	public String getHeaderFormat() {
		return "%10s%18s%18s%18s%18s%n";
	}
	
	@Override
	public String[][] getRows() {
		String[][] rows = new String[2][];
		rows[0] = generateRow(this.morning, "Morning");
		rows[1] = generateRow(this.evening, "Evening");
		return rows;
	}

	private String[] generateRow(PeakVolumnTime volumn, String rowLable) {
		String[] row = new String[TABLE_COLUMN_NUM];
		row[0] = rowLable;
		row[1] = volumn.getNorthboundTime();
		row[2] = String.format("%d", volumn.getNorthboundCount());
		row[3] = volumn.getSouthboundTime();
		row[4] = String.format("%d", volumn.getSouthboundCount());
		return row;
	}

	@Override
	public String getRowFormat() {
		return this.getHeaderFormat();
	}

	@Override
	public String getTitle() {
		return TITLE + " " + getPeriod().description() + " (Day " + day + ")";
	}
}
