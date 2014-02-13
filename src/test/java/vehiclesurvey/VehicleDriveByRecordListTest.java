package vehiclesurvey;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class VehicleDriveByRecordListTest {

	private VehicleDriveByRecordList driveByRecords;

	@Before
	public void initialize() {
		VehicleSurveyRecordList surveyRecordList = new VehicleSurveyRecordList();
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A268981"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A269123"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A604957"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B604960"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A605128"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B605132"));
		driveByRecords = surveyRecordList.generateDriveByRecords();
	}
	
	@Test
	public void testGroupByPeriod() {
		VehicleDriveByRecordList[] lists = driveByRecords.groupByPeriod(TimePeriod.HOUR);
		assertEquals(24, lists.length);
		assertEquals(2,  lists[0].size());
	}
	
	@Test
	public void testGroupByDirection() {
		Map<VehicleDirection, VehicleDriveByRecordList> map = driveByRecords.groupByDirection();
		assertEquals(2, map.keySet().size());
		assertEquals(1, map.get(VehicleDirection.NORTH_BOUND).size());
		assertEquals(1, map.get(VehicleDirection.SOUTH_BOUND).size());
	}
}
