package nl.uva.se.ft3impl.pressure;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class PressureFormatFilter extends Filter<Measurement, String> {

	public PressureFormatFilter(Pipe<Measurement> in, Pipe<String> out) {
		super(in, out);
	}

	@Override
	public String transformOne(Measurement obj) {
		return obj.toString(); // done at class level.
	}
	

}
