package org.nasdanika.demos.graph.compute.sync;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.Element;
import org.nasdanika.graph.model.adapters.NodeAdapter;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.graph.processor.Processor;
import org.nasdanika.graph.processor.ProcessorInfo;

/**
 * Reflective target to create synchronous {@link BiFunction} processors.
 */
public class SyncProcessorFactory {
	
	@Processor(
			type = NodeAdapter.class,
			value = "get() instanceof T(org.nasdanika.models.functionflow.FunctionFlow)")
	public Object createFunctionFlowProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		
		return new FunctionFlowProcessor();
	}
	
	@Processor(
			type = NodeAdapter.class,
			value = "get() instanceof T(org.nasdanika.models.functionflow.Start)")
	public Object createStartProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		
		return new StartProcessor();
	}
	
	
	@Processor(
			type = NodeAdapter.class,
			value = "get() instanceof T(org.nasdanika.models.functionflow.End)")
	public Object createEndProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		
		return new EndProcessor();
	}

}
