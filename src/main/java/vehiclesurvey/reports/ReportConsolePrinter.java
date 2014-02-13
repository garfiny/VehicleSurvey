package vehiclesurvey.reports;

public class ReportConsolePrinter implements ReportPrinter {

	@Override
	public void print(Report report) {
		System.out.format("%-100s%n", report.getTitle());
		System.out.format(report.getHeaderFormat(), (Object[])report.getHeaders());
		for(Object[] row : report.getRows()) {
			System.out.format(report.getRowFormat(), row);
		}
		System.out.println();
	}
}