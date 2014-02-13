package vehiclesurvey.reports;

public interface Report {

	String[] getHeaders();
	String getHeaderFormat();
	String[][] getRows();
	String getRowFormat();
	String getTitle();
}