package nl.uva.se.ft3impl.own;

import java.util.function.Predicate;

import src.main.java.pipes.Filter;
import src.main.java.pipes.Pipe;

public class PredicateFilter <T> extends Filter<T, T> {

	public PredicateFilter(Predicate<T> p, Pipe<T> in, Pipe<T> out) {
		super(in, out);
		this.pred = p;
	}
	
	protected final Predicate<T> pred;

	@Override
	public T transformOne(T obj) {
		if (pred.test(obj))
			return obj;
		
		return null;
	}
}
