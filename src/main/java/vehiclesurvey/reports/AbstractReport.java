package vehiclesurvey.reports;

import vehiclesurvey.TimePeriod;

public abstract class AbstractReport implements Report {
	
	private static final long MINUTES_OF_DAY = 24 * 60;
	private TimePeriod period;
	
	public AbstractReport(TimePeriod period) {
		this.setPeriod(period);
	}

	@Override
	public String[] getHeaders() {
		int range = getRange();
		String[] headers = new String[range];
		for (int i = 1; i < range; i++) {
			headers[i] = String.format("  #%02d", i);
		}
		headers[0] = "Range";
		return headers;
	}

	@Override
	public String getHeaderFormat() {
		StringBuilder sb = new StringBuilder("%13s");
		for (int i = 1; i < getRange(); i++) {
			sb.append("%5s");
		}
		sb.append("%n");
		return sb.toString();
	}

	protected int getRange() {
		return (int) (MINUTES_OF_DAY / getPeriod().minutesOfPeriod() + 1);
	}

	public TimePeriod getPeriod() {
		return period;
	}

	public void setPeriod(TimePeriod period) {
		this.period = period;
	}
}
