package nl.uva.se.ft3impl.log;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class LogFormatFilter extends Filter<LogMessage, String> {

	public LogFormatFilter(Pipe<LogMessage> in, Pipe<String> out) {
		super(in, out);
	}

	@Override
	public String transformOne(LogMessage obj) {
		return obj.toString();
	}

}
