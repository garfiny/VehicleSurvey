package vehiclesurvey;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vehiclesurvey.VehicleDirection;
import vehiclesurvey.VehicleDriveByRecord;
import vehiclesurvey.VehicleSurveyRecord;


public class VehicleDriveByRecordTest {
	
	private VehicleDriveByRecord driveByRecord;

	@Before
	public void initialize() {
		driveByRecord = new VehicleDriveByRecord();
	}

	@Test public void testIsSameVehicleWithEmptySurveyCodes() {
		VehicleSurveyRecord surveyRecord = VehicleSurveyRecord.createInstance("A268981");
		assertTrue(driveByRecord.isSameVehicle(surveyRecord));
	}

	@Test
	public void testIsSameVehicleOfSingleSensor() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A268981");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("A269123");
		driveByRecord.addSurveyCode(surveyRecord1);
		assertTrue(driveByRecord.isSameVehicle(surveyRecord2));
	}
	
	@Test
	public void testIsDifferentVehicleOfSingleSensor() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("A223456");
		driveByRecord.addSurveyCode(surveyRecord1);
		assertFalse(driveByRecord.isSameVehicle(surveyRecord2));
	}
	
	@Test
	public void testIsSameVehicleOfTwoSensors() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A604957");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("B604960");
		VehicleSurveyRecord surveyRecord3 = VehicleSurveyRecord.createInstance("A605128");
		VehicleSurveyRecord surveyRecord4 = VehicleSurveyRecord.createInstance("B605132");
		driveByRecord.addSurveyCode(surveyRecord1);
		assertTrue(driveByRecord.isSameVehicle(surveyRecord2));
		driveByRecord.addSurveyCode(surveyRecord2);
		assertTrue(driveByRecord.isSameVehicle(surveyRecord3));
		driveByRecord.addSurveyCode(surveyRecord3);
		assertTrue(driveByRecord.isSameVehicle(surveyRecord4));
	}
	
	@Test
	public void testIsDifferentVehicleOfTwoSensors() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("B223456");
		driveByRecord.addSurveyCode(surveyRecord1);
		assertFalse(driveByRecord.isSameVehicle(surveyRecord2));
	}

	@Test
	public void testGetDirectionReturnsNorthbound() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("A123457");
		driveByRecord.addSurveyCode(surveyRecord1);
		driveByRecord.addSurveyCode(surveyRecord2);
		assertEquals(VehicleDirection.NORTH_BOUND, driveByRecord.getDirection());
	}

	@Test
	public void testGetDirectionReturnsSouthbound() {
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("B123457");
		VehicleSurveyRecord surveyRecord3 = VehicleSurveyRecord.createInstance("A123458");
		VehicleSurveyRecord surveyRecord4 = VehicleSurveyRecord.createInstance("B123459");
		driveByRecord.addSurveyCode(surveyRecord1);
		driveByRecord.addSurveyCode(surveyRecord2);
		driveByRecord.addSurveyCode(surveyRecord3);
		driveByRecord.addSurveyCode(surveyRecord4);
		assertEquals(VehicleDirection.SOUTH_BOUND, driveByRecord.getDirection());
	}
	
	@Test
	public void testTimeUsedInMillisForNorthbound() {
		long timeMark = 123456;
		long timeDifference = 150;
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A" + timeMark);
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("A" + (timeMark + timeDifference));
		driveByRecord.addSurveyCode(surveyRecord1);
		driveByRecord.addSurveyCode(surveyRecord2);
		assertEquals(timeDifference, driveByRecord.timeUsedInMillis());
	}

	@Test
	public void testTimeUsedInMillisForSouthbound() {
		long timeMark = 123456;
		long timeDifference = 150;
		VehicleSurveyRecord surveyRecord1 = VehicleSurveyRecord.createInstance("A" + timeMark);
		VehicleSurveyRecord surveyRecord2 = VehicleSurveyRecord.createInstance("B" + (timeMark + 3));
		VehicleSurveyRecord surveyRecord3 = VehicleSurveyRecord.createInstance("A" + (timeMark + timeDifference));
		VehicleSurveyRecord surveyRecord4 = VehicleSurveyRecord.createInstance("B" + (timeMark + timeDifference + 3));
		driveByRecord.addSurveyCode(surveyRecord1);
		driveByRecord.addSurveyCode(surveyRecord2);
		driveByRecord.addSurveyCode(surveyRecord3);
		driveByRecord.addSurveyCode(surveyRecord4);
		assertEquals(timeDifference, driveByRecord.timeUsedInMillis());
	}
}