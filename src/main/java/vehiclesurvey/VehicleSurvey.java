package vehiclesurvey;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import vehiclesurvey.reports.AverageTotalVehicleReportService;
import vehiclesurvey.reports.DistanceReportService;
import vehiclesurvey.reports.PeakVolumnTimesReportService;
import vehiclesurvey.reports.Report;
import vehiclesurvey.reports.ReportConsolePrinter;
import vehiclesurvey.reports.ReportPrinter;
import vehiclesurvey.reports.ReportService;
import vehiclesurvey.reports.SpeedDistributionReportService;
import vehiclesurvey.reports.TotalVehicleReportService;

public class VehicleSurvey {

	private ReportService reportService;

	public VehicleSurvey(ReportService reportService) {
		this.reportService = reportService;
	}

	public void report() {
		Report[] reports = reportService.generateReport(loadResource());
		ReportPrinter printer = new ReportConsolePrinter();
		for (Report report : reports) {
			printer.print(report);
		}
	}

	private VehicleSurveyRecordList loadResource() {
		String file = Paths.get("").toAbsolutePath().toString()
				+ "/src/main/resources/sample_data.txt";
		VehicleSurveyRecordList recordList = null;
		try {
			recordList = new VehicleSurveyRecordList(file);
		} catch (IOException e) {
		}
		return recordList;
	}

	public static void main(String[] args) {
		printOptions();
		int option = readSelection();
		if (option < 1 || option > 5) {
			System.exit(0);
		}
		ReportService reportService = null;
		switch (option) {
		case 1:
			reportService = new TotalVehicleReportService();
			break;
		case 2:
			reportService = new AverageTotalVehicleReportService();
			break;
		case 3:
			reportService = new PeakVolumnTimesReportService();
			break;
		case 4:
			reportService = new SpeedDistributionReportService();
			break;
		case 5:
			reportService = new DistanceReportService();
			break;
		}
		VehicleSurvey vehicleSurvey = new VehicleSurvey(reportService);
		vehicleSurvey.report();
	}

	private static void printOptions() {
		System.out.println("Please select report service: (1-5, other to exit)");
		System.out.println(" 1) Total Vehicle Count Report");
		System.out.println(" 2) Total Vehicle Count Report(Average Accross Days)");
		System.out.println(" 3) Peak Volume Times Report");
		System.out.println(" 4) Speed Distribution Report");
		System.out.println(" 5) Cars Distance Report");
	}

	private static int readSelection() {
		try (InputStreamReader reader = new InputStreamReader(System.in)) {
			int input = reader.read();
			return Integer.parseInt(String.valueOf((char) input));
		} catch (IOException io) {
			io.printStackTrace();
			return 0;
		}
	}
}
