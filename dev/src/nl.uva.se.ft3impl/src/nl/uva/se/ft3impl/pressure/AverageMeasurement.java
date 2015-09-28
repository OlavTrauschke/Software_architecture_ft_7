package nl.uva.se.ft3impl.pressure;

import java.time.Duration;
import java.time.LocalDateTime;

public class AverageMeasurement extends Measurement {
	
	public AverageMeasurement(BloodPressure pressure, LocalDateTime timestamp, LocalDateTime end) {
		super(pressure, timestamp);
		this.end = end;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	protected LocalDateTime end;

	@Override
	public String toString() {
		Duration duration = Duration.between(timestamp, end);
		return timestamp.getDayOfWeek() + " - " + timestamp.toLocalTime() + " (avg " + duration + ") -> " + pressure;
	}
}