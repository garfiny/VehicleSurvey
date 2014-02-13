package vehiclesurvey.reports;

import java.util.Arrays;
import java.util.Map;

public class SpeedDistributionReport implements Report {
	
	private static final String TITLE = "Speed Distribution Report";
	public static final int MIN_SPEED = 10;
	public static final int MAX_SPEED = 80;
	private Map<Integer, Integer> speedDistributions;

	public SpeedDistributionReport(Map<Integer, Integer> speedDistributions) {
		this.speedDistributions = speedDistributions;
	}

	@Override
	public String[] getHeaders() {
		Integer[] range = sortedRange();
		String[] headers = new String[range.length + 1];
		int i = 1;
		for (Integer integer : range) {
			headers[i++] = String.format(" %2d~%2d(km/h)", integer.intValue() - 10, integer.intValue());
		}
		headers[0] = "Speed";
		return headers;
	}
	
	public String getHeaderFormat() {
		StringBuilder sb = new StringBuilder("%10s");
		for (int i = 0; i < speedDistributions.keySet().size(); i++) {
			sb.append("%12s");
		}
		sb.append("%n");
		return sb.toString();
	}

	@Override
	public String[][] getRows() {
		String[][] rows = new String[1][];
		Integer[] range = sortedRange();
		rows[0] = new String[range.length + 1];
		rows[0][0] = "Times";
		int i = 1;
		for (Integer integer : range) {
			Integer count = speedDistributions.get(integer);
			rows[0][i++] = count.toString();
		}
		return rows;
	}
	
	@Override
	public String getRowFormat() {
		return this.getHeaderFormat();
	}
	
	@Override
	public String getTitle() {
		return TITLE;
	}
	
	private Integer[] sortedRange() {
		Integer[] range = speedDistributions.keySet().toArray(new Integer[0]);
		Arrays.sort(range);
		return range;
	}
}