package nl.uva.se.ft3impl.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.uva.se.ft3impl.PrintFilter;
import nl.uva.se.ft3impl.RandomUtils;
import src.main.java.pipes.Pipe;

public class Example {
	public static final int LOWEST_PRIO = 5;
	
	public void run() {
		// Logging information for service engineer
		
		// We'll need the following:
		//  - source of log files
		//  - filter which filters messages relevant to service engineers
		//  - sink which 'sends' something to the engineer.
		//  - three pipes which buffer and function as intermediates.
		
		System.out.println("Log example");
		
		Collection<LogMessage> source = randomMessages(100);
		
		Pipe<LogMessage> p1 = new Pipe<>();
		Pipe<LogMessage> p2 = new Pipe<>();
		Pipe<String> p3 = new Pipe<>();
		
		ServiceEngineerFilter f1 = new ServiceEngineerFilter(p1, p2);
		LogFormatFilter f2 = new LogFormatFilter(p2, p3);
		PrintFilter f3 = new PrintFilter("to service engineer: ", p3, null);
		
		f1.start();
		f2.start();
		f3.start();
		
		p1.addAll(source);
		
		// Keep processing until all pipes are empty, then exit worker threads.
		while (!p1.isEmpty() || !p2.isEmpty() || !p3.isEmpty()) {
			try {
				Thread.sleep(10); // ms
			} catch (InterruptedException e) { }
		}
		
		f1.exitWhenDone();
		f2.exitWhenDone();
		f3.exitWhenDone();
		
		System.out.println("---------------------");
	}
	
	protected Collection<LogMessage> randomMessages(int n) {
		List<LogMessage> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Site s = RandomUtils.randomEnum(Site.class);
			String msg = RandomUtils.randomString();
			int prio = RandomUtils.randomInt(LOWEST_PRIO);
			
			l.add(new LogMessage(msg, s, prio));
		}
		return l;
	}
}
