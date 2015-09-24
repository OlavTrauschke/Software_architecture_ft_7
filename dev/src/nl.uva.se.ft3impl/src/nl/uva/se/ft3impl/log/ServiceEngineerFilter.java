package nl.uva.se.ft3impl.log;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class ServiceEngineerFilter extends Filter<LogMessage, LogMessage> {

	public ServiceEngineerFilter(Pipe<LogMessage> in, Pipe<LogMessage> out) {
		super(in, out);
	}

	@Override
	public LogMessage transformOne(LogMessage msg) {
		// Filter high priority logs relevant to service engineers.
		return msg.priority < 3 && (msg.site == Site.Infrastructure || msg.site == Site.Loadbalancer)
				? msg 
				: null;
	}
}
