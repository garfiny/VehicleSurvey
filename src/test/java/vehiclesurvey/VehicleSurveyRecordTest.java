package vehiclesurvey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VehicleSurveyRecordTest {
	
	@Test
	public void testCreateInstance() {
		long timeMark = 268981;
		char sensor   = 'A';
		VehicleSurveyRecord record = VehicleSurveyRecord.createInstance(sensor + String.valueOf(timeMark));
		assertEquals(timeMark, record.getTimeMark());
		assertEquals(sensor, record.getSensor());
	}
	
	@Test
	public void testAppearLaterThan() {
		VehicleSurveyRecord current = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord previous = VehicleSurveyRecord.createInstance("A12345");
		assertTrue(current.appearLaterThan(previous));
	}
	
	@Test
	public void testIsSameSensor() {
		VehicleSurveyRecord sensorA1 = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord sensorA2 = VehicleSurveyRecord.createInstance("A234567");
		assertTrue(sensorA1.isSameSensor(sensorA2));
	}
	
	@Test
	public void testIsDifferentSensor() {
		VehicleSurveyRecord sensorA = VehicleSurveyRecord.createInstance("A123456");
		VehicleSurveyRecord sensorB = VehicleSurveyRecord.createInstance("B234567");
		assertFalse(sensorA.isSameSensor(sensorB));
	}
	
	@Test
	public void testTimeDifferenceWhenGivenRecordAppearAfterCurrent() {
		long timeMark1 = 12345;
		long timeMark2 = 123456;
		VehicleSurveyRecord record1 = VehicleSurveyRecord.createInstance("A" + timeMark1);
		VehicleSurveyRecord record2 = VehicleSurveyRecord.createInstance("A" + timeMark2);
		assertEquals(timeMark2 - timeMark1, record1.timeDifferences(record2));
	}

	@Test
	public void testTimeDifferenceWhenCurrentRecordAppearAfterGivenRecord() {
		long timeMark1 = 12345;
		long timeMark2 = 123456;
		VehicleSurveyRecord record1 = VehicleSurveyRecord.createInstance("A" + timeMark1);
		VehicleSurveyRecord record2 = VehicleSurveyRecord.createInstance("A" + timeMark2);
		assertEquals(timeMark2 - timeMark1, record2.timeDifferences(record1));
	}
}