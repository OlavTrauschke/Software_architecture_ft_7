package nl.uva.se.ft3impl.pressure;

import java.time.LocalDateTime;

public class Measurement {
	
	public Measurement(BloodPressure pressure, LocalDateTime timestamp) {
		this.pressure = pressure;
		this.timestamp = timestamp;
	}
	
	protected BloodPressure pressure;
	protected LocalDateTime timestamp;
	
	public BloodPressure getPressure() {
		return pressure;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return timestamp.getDayOfWeek() + " - " + timestamp.toLocalTime() + " -> " + pressure;
	}
}