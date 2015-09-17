package uva.se.funcpipes;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Builder interface for pipeline construction starting from the generic
 * argument.
 * 
 * @author Floris den Heijer
 *
 * @param <T>
 *            Type serving as input
 */
public interface PipelineBuilder<T> {

	/**
	 * Adds a function to the pipeline.
	 * 
	 * @param f
	 *            Function to be added.
	 * @return Builder for the resulting pipeline.
	 */
	PipelineBuilder<T> via(Function<T, T> f);

	/**
	 * Adds a void function to the pipeline, which doesn't replace element.
	 * 
	 * @param consumer
	 *            Consuming function.
	 * @return Builder for the resulting pipeline.
	 */
	PipelineBuilder<T> via(Consumer<T> consumer);

	/**
	 * Adds a filter to the pipeline.
	 * 
	 * @param predicate
	 *            Filter predicate.
	 * @return Builder for the resulting pipeline.
	 */
	PipelineBuilder<T> filter(Predicate<T> predicate);
	
	/**
	 * Adds an expander to the pipeline, which transforms an element into a
	 * collection. Each element of the collection will be passed along
	 * individually.
	 * 
	 * @param c
	 *            Transformation function.
	 * @return Builder for the resulting pipeline.
	 */
	PipelineBuilder<T> expand(Function<T, Collection<T>> f);
	
	/**
	 * Transforms the pipeline into another pipeline of T2, and returns it's
	 * builder
	 * 
	 * @param f
	 *            Transformation function.
	 * @return Builder for the new pipeline.
	 */
	<U> PipelineBuilder<U> transform(Function<T, U> f);
	
	PipelineSplitBuilder<T> split();
}
