package org.nasdanika.demos.graph.compute.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.nasdanika.common.BiSupplier;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.ChildProcessor;
import org.nasdanika.models.functionflow.FunctionFlow;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class FunctionFlowProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected Collection<BiFunction<Object, ProgressMonitor, Object>> startProcessors = Collections.synchronizedCollection(new ArrayList<>());
	protected Collection<BiFunction<Object, ProgressMonitor, Object>> endProcessors = Collections.synchronizedCollection(new ArrayList<>());

	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Map<BiFunction<Object, ProgressMonitor, Object>, Object> startProcessorResults = new LinkedHashMap<>();
		for (BiFunction<Object, ProgressMonitor, Object> startProcessor: startProcessors) {
			startProcessorResults.put(startProcessor, startProcessor.apply(arg, progressMonitor));
		}
		Map<BiFunction<Object, ProgressMonitor, Object>, Object> endProcessorResults = new LinkedHashMap<>();
		for (BiFunction<Object, ProgressMonitor, Object> endProcessor: endProcessors) {
			endProcessorResults.put(endProcessor, endProcessor.apply(arg, progressMonitor));
		}
		
		return BiSupplier.of(startProcessorResults, endProcessorResults);
	}	
	
	@ChildProcessor("get() instanceof T(org.nasdanika.models.functionflow.Start)")
	public void addStartProcessor(BiFunction<Object, ProgressMonitor, Object> startProcessor) {
		startProcessors.add(startProcessor);
	}
	
	@ChildProcessor("get() instanceof T(org.nasdanika.models.functionflow.End)")
	public void addEndProcessor(BiFunction<Object, ProgressMonitor, Object> endProcessor) {
		endProcessors.add(endProcessor);
	}

}
