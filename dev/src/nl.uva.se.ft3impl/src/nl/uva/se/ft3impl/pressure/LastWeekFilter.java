package nl.uva.se.ft3impl.pressure;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class LastWeekFilter extends Filter<Measurement,Measurement> {

	public LastWeekFilter(Pipe<Measurement> in, Pipe<Measurement> out) {
		super(in, out);
	}

	@Override
	public Measurement transformOne(Measurement obj) {
		// You would never actually make such a filter, perhaps a generic date filter,
		// but it is what is is.
		
		LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusWeeks(1);
		if (obj.getTimestamp().isBefore(start))
			return null;
		
		return obj;
	}

}
