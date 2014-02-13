package vehiclesurvey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import vehiclesurvey.VehicleSurveyRecordList;

public class VehicleSurveyRecordListTest {

	private static String dataFilepath;
	static { 
		dataFilepath = Paths.get("").toAbsolutePath().toString() + "/src/test/resources/sample_data.txt";
	}
	
	@Test
	public void testConstructorWithFilepath() {
		try {
			new VehicleSurveyRecordList(dataFilepath);
		} catch (IOException e) {
			fail("fail to load data file.");
		}
	}

	@Test
	public void testFailedToLoadData() {
		try {
			new VehicleSurveyRecordList("wrong file path");
			fail("fail to load data file.");
		} catch (IOException e) {
		}
	}
	
	@Test
	public void testGroupByDays() throws FileNotFoundException, IOException {
		VehicleSurveyRecordList surveyRecordList = new VehicleSurveyRecordList(dataFilepath);
		VehicleSurveyRecordList[] lists = surveyRecordList.groupByDays();
		assertEquals(5, lists.length);
		for (VehicleSurveyRecordList list : lists) {
			assertFalse(list.isEmpty());
		}
	}
	
	@Test
	public void testGenerateDriveByRecords() {
		VehicleSurveyRecordList surveyRecordList = new VehicleSurveyRecordList();
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A268981"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A269123"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A604957"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B604960"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A605128"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B605132"));
		List<VehicleDriveByRecord> driveByRecords = surveyRecordList.generateDriveByRecords();
		assertEquals(2, driveByRecords.size());
	}
}