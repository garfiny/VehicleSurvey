package vehiclesurvey.reports;

import vehiclesurvey.VehicleSurveyRecordList;

public interface ReportService {
	
	Report[] generateReport(VehicleSurveyRecordList surveyRecordList);
}