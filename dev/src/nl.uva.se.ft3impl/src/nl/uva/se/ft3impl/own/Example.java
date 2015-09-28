package nl.uva.se.ft3impl.own;

import src.main.java.pipes.Pipe;

public class Example {
	public void run() {
		// We'll start with implementing two new filters, a predicate 
		// and a lambda filter for easier configuration. The system
		// doesn't support splits explicitly, but it does allow
		// threading, which means we could have multiple threads 
		// hooked up to the same source competing for attention.
		
		// This is different from a split in the sense that only one
		// pipe gets the resource.
		
		// To create a feedback loop, as we did in our initial submit,
		// we could make 2 filters:
		//  - one which raises and lowers an input num by a factor
		//  - one which tests if the num is within range, and if so prints it
		
		// The idea was nice but this doesnt work.
		
		System.out.println("Feedback loop example");
		
		float goal = 2.5f;
		float maxDev = 0.1f;
		float raiseFactor = 1.333f;
		float lowerFactor = 0.333f;
		
		Pipe<Float> input = new Pipe<>();
		LambdaFilter<Float, Float> f1 = new LambdaFilter<>(
				f -> {
					System.out.printf("%.1f ", f);
					
					if (Math.abs(goal - f) > maxDev) {
						if (f > goal) {
							return f * lowerFactor;
						} else {
							return f * raiseFactor;
						}
					}
					
					return f;
				}, input, input);
		
		PredicateFilter<Float> f2 = new PredicateFilter<>(
				f -> {
					if (Math.abs(goal - f) <= maxDev) {
						System.out.printf("-> %.3f", f);
						return false; // consumes.
					}
					return true;
				}, input, input);
		
		f1.start();
		f2.start();
		
		input.add(5f);
		
		// Keep processing until all pipes are empty, then exit worker threads.
		while (!input.isEmpty()) {
			try {
				Thread.sleep(10); // ms
			} catch (InterruptedException e) { }
		}
		
		f1.exitWhenDone();
		f2.exitWhenDone();
				
		System.out.println("---------------------");
	}
}
