package nl.uva.se.ft3impl.own;

import java.util.function.Function;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class LambdaFilter<T, R> extends Filter<T, R> {

	public LambdaFilter(Function<T, R> f, Pipe<T> in, Pipe<R> out) {
		super(in, out);
		this.func = f;
	}
	
	protected final Function<T, R> func;

	@Override
	public R transformOne(T obj) {
		return func.apply(obj);
	}
	

}
