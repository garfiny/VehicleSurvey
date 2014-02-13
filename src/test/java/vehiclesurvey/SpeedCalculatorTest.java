package vehiclesurvey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vehiclesurvey.SpeedCalculator;


public class SpeedCalculatorTest {

	private SpeedCalculator speedCalculator;

	@Before
	public void initialize() {
		speedCalculator = new SpeedCalculator();
	}
	
	@Test
	public void testCalculateSpeed() {
		long[] passingTime = {150, 120, 100, 50};
		long[] speed = {60, 75, 90, 180};
		for (int i = 0; i < passingTime.length; i++) {
			assertEquals(speed[i] * 1000, speedCalculator.calculateSpeed(passingTime[i]));
		}
	}
	
	@Test
	public void testDistanceBetween() {
		VehicleSurveyRecordList surveyRecordList = new VehicleSurveyRecordList();
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A1016488"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A1016648"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A1058535"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B1058538"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("A1058659"));
		surveyRecordList.add(VehicleSurveyRecord.createInstance("B1058662"));
		List<VehicleDriveByRecord> driveByRecords = surveyRecordList.generateDriveByRecords();
		long distanceBetween = speedCalculator.distanceBetween(driveByRecords.get(0), driveByRecords.get(1));
		assertTrue(distanceBetween > 0);
	}
}