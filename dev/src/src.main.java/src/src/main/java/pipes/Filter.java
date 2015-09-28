package src.main.java.pipes;

// Based on the original filter interface, but rewritten to include
// the threadedrunner, 
public abstract class Filter<T, R> extends Thread {
	
	public Filter(Pipe<T> in, Pipe<R> out) {
		this.in = in;
		this.out = out; // can be null; sink.
	}

	protected final Pipe<T> in;
	protected final Pipe<R> out;
	
	protected boolean abort = false;
	
	public abstract R transformOne(T obj);

	@Override
	public void run() {
		abort = false;
		
		while (true) {
			T obj = in.poll();
			if (obj == null) {
				if (abort) {
					return;
				}
				
				try {
					Thread.sleep(10); //ms
				} catch (InterruptedException e) {
					return;
				}
			}
			else {
				R result = transformOne(obj);
				if (result != null && out != null) {
					if (!out.offer(result)) {
						throw new RuntimeException("Failed to offer result to next pipe.");
					}
				}				
			}
		}
	}
	
	public void exitWhenDone() {
		abort = true;
	}
}
