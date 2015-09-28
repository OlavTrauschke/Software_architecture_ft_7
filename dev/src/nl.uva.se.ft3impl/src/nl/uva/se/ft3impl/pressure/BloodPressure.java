package nl.uva.se.ft3impl.pressure;

public class BloodPressure {

	public BloodPressure(double systolic, double diastolic) {
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	protected double systolic;
	protected double diastolic;

	public double getSystolic() {
		return systolic;
	}

	public double getDiastolic() {
		return diastolic;
	}

	@Override
	public String toString() {
		return String.format("%.0f/%.0f mm Hg", systolic, diastolic);
	}

	public static BloodPressure random() {
		final double min = 60;
		final double max = 160;

		double s = min + (max - min) * Math.random();
		double d = min + (max - min) * Math.random();

		return new BloodPressure(s, d);
	}
}
