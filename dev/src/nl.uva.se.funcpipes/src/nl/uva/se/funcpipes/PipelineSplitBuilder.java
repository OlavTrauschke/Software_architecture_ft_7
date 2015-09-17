package nl.uva.se.funcpipes;

import java.util.function.Consumer;

public interface PipelineSplitBuilder<T> {
	PipelineSplitBuilder<T> to(Pipeline<T> p);
	
	PipelineSplitBuilder<T> to(Consumer<PipelineBuilder<T>> config);
}
