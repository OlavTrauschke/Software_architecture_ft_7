package nl.uva.se.ft3impl.pressure;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;

import nl.uva.se.ft3impl.PrintFilter;
import nl.uva.se.ft3impl.RandomUtils;
import src.main.java.pipes.Pipe;

public class Example {
	public void run() {
		// Blood pressure example.
		
		// For this example we'll need:
		//	- a source of blood pressure data, either averaged or instant
		//  - filter which filters pressure data by date
		//  - filter which formats measurements
		//  - display sink
		//  - pipes which buffer data
		
		System.out.println("Blood pressure example");
		
		Collection<Measurement> source = randomMeasurements(100);
		
		Pipe<Measurement> p1 = new Pipe<>();
		Pipe<Measurement> p2 = new Pipe<>();
		Pipe<String> p3 = new Pipe<>();
		
		LastWeekFilter f1 = new LastWeekFilter(p1, p2);
		PressureFormatFilter f2 = new PressureFormatFilter(p2, p3);
		PrintFilter f3 = new PrintFilter("", p3, null);
		
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
	
	protected Collection<Measurement> randomMeasurements(int n) {
		// Generate random measurements over a set period.
		Collection<Measurement> l = new ArrayList<>(n);
		
		final long start = LocalDateTime.now().minusWeeks(4).toEpochSecond(ZoneOffset.UTC);
		final long end = LocalDateTime.now().minusHours(2).toEpochSecond(ZoneOffset.UTC);
		
		for (int i = 0; i < n; i++) {
			long dateSecs = getRandomTimeBetweenTwoDates(start, end);
			LocalDateTime date = LocalDateTime.ofEpochSecond(dateSecs, 0, ZoneOffset.UTC);
			
			BloodPressure p = BloodPressure.random();
			
			// 1/3 chance of average
			if (Math.random() < 0.3) {
				int hours = RandomUtils.randomInt(7) + 1;
				l.add(new AverageMeasurement(p, date, date.plusHours(hours)));
			} else {
				l.add(new Measurement(p, date));
			}
		}
		
		return l;
	}
	
	private long getRandomTimeBetweenTwoDates (long beginTime, long endTime) {
	    long diff = endTime - beginTime + 1;
	    return beginTime + (long) (Math.random() * diff);
	}
}