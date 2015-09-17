package uva.se.funcpipes;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Buffer<T, U, R> implements PipelineBuilder<R> {
	
	private final Pipeline<R> _pipe = new Pipeline<R>();
	private BiFunction<T, U, R> _func;
	
	private T _arg1;
	private U _arg2;
	
	public Buffer() {
		this(null);
	}
	
	public Buffer(BiFunction<T, U, R> f) {
		_func = f;
	}
	
	public void fillFirstArg(T arg) {
		_arg1 = arg;
	}
	public void fillSecondArg(U arg) {
		_arg2 = arg;
	}
	
	public void setFunc(BiFunction<T, U, R> f) {
		_func = f;
	}
	
	public void flush() {
		// Save and reset
		final T a1 = _arg1;
		final U a2 = _arg2;
		_arg1 = null;
		_arg2 = null;
		
		if (_func != null) {
			_pipe.feed(_func.apply(a1, a2));
		}
	}

	
	// Pipeline redirect.
	public PipelineBuilder<R> via(Function<R, R> f) {
		return _pipe.via(f);
	}
	public PipelineBuilder<R> via(Consumer<R> consumer) {
		return _pipe.via(consumer);
	}
	public PipelineBuilder<R> filter(Predicate<R> predicate) {
		return _pipe.filter(predicate);
	}
	public PipelineBuilder<R> expand(Function<R, Collection<R>> f) {
		return _pipe.expand(f);
	}
	public <T2> PipelineBuilder<T2> transform(Function<R, T2> f) {
		return _pipe.transform(f);
	}
	public PipelineSplitBuilder<R> split() {
		return _pipe.split();
	}
}
