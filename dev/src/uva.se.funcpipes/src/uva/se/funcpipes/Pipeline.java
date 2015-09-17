package uva.se.funcpipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * LINQ-like pipeline which allows the chaining of functions operating on a
 * type. The pipeline is configured with a builder, which also allows the
 * specification of a transformation function to another type. This creates a
 * new pipeline and sets up a link, essentially allowing mapping operations.
 *
 * -- edit: just found out about java.util.Stream, which seems to do the same,
 * but doesn't support a shifting data type and expansion.
 * 
 * @author Floris den Heijer
 *
 */
public class Pipeline<T> implements PipelineBuilder<T> {

	private final List<Stage> _stages = new ArrayList<Stage>();

	/**
	 * Send an object down the pipeline.
	 * 
	 * @param obj
	 *            Object to be fed.
	 */
	public void feed(T obj) {
		run(obj, _stages);
	}

	/**
	 * Send multiple objects down the pipeline, each element gets
	 * individually fed.
	 * 
	 * @param collection
	 *            Collection with objects.
	 */
	public void feed(Collection<T> collection) {
		for (T t : collection) {
			run(t, _stages);
		}
	}

	private void run(T obj, List<Stage> stages) {
		
		int n = stages.size();
		for (int i = 0; i < n; i++) {
			
			Stage s = stages.get(i);
			
			// If function, apply and continue.
			if (s.func != null) {
				obj = s.func.apply(obj);
			}

			// If consumer, run and continue.
			if (s.consumer != null) {
				s.consumer.accept(obj);
			}

			// If predicate and fail, return.
			if (s.predicate != null && !s.predicate.test(obj)) {
				return;
			}
			
			// If expander, recurse on sublist and return.
			if (s.expander != null) {
				Collection<T> c = s.expander.apply(obj);
				if (c != null) {
					List<Stage> rest = stages.subList(i+1, n);
					for (T t : c) {
						run(t, rest);
					}
				}
				
				return;
			}

			// If last in line, return.
			if (s.isEnd) {
				return;
			}
		}
	}
	
	/**
	 * Clears the pipeline.
	 */
	public void clear() {
		_stages.clear();
	}
	
	
	// Builder interface.
	public PipelineBuilder<T> via(Function<T, T> f) {
		_stages.add(new Stage().asFunc(f));
		return this;
	}

	public PipelineBuilder<T> via(Consumer<T> consumer) {
		_stages.add(new Stage().asConsumer(consumer, false));
		return this;
	}

	public PipelineBuilder<T> filter(Predicate<T> predicate) {
		_stages.add(new Stage().asPredicate(predicate));
		return this;
	}

	public <T2> PipelineBuilder<T2> transform(Function<T, T2> f) {
		// Create new pipeline and connect.
		Pipeline<T2> p2 = new Pipeline<T2>();
		_stages.add(new Stage().asConsumer(t -> p2.feed(f.apply(t)), true));

		return p2;
	}
	
	public PipelineBuilder<T> expand(Function<T, Collection<T>> f) {
		_stages.add(new Stage().asExpander(f));
		return this;
	}
	
	public PipelineSplitBuilder<T> split() {
		final List<Pipeline<T>> splits = new ArrayList<Pipeline<T>>();
		_stages.add(new Stage().asConsumer(t -> {
			for (Pipeline<T> p : splits) {
				p.feed(t);
			}
		}, true));
		
		return new PipelineSplitBuilder<T>() {
			public PipelineSplitBuilder<T> to(Pipeline<T> p) {
				splits.add(p);
				return this;
			}

			public PipelineSplitBuilder<T> to(Consumer<PipelineBuilder<T>> config) {
				Pipeline<T> p = new Pipeline<T>();
				config.accept(p);
				return to(p);
			}
		};
	}

	/**
	 * Internal stage container
	 */
	private class Stage {

		Function<T, T> func = null;
		Consumer<T> consumer = null;
		Predicate<T> predicate = null;
		Function<T, Collection<T>> expander = null;

		boolean isEnd = false; // ends pipeline execution, safety precaution.
		
		Stage asFunc(Function<T, T> func) {
			this.func = func;
			return this;
		}
		Stage asExpander(Function<T, Collection<T>> func) {
			this.expander = func;
			return this;
		}
		Stage asConsumer(Consumer<T> consumer, boolean isEnd) {
			this.consumer = consumer;
			this.isEnd = isEnd;
			return this;
		}
		Stage asPredicate(Predicate<T> predicate) {
			this.predicate = predicate;
			return this;
		}
	}

}