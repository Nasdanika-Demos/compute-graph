package org.nasdanika.demos.graph.compute.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.IncomingHandler;

public class EndProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		return "End: " + arg;
	}
	
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getIncomingHandler() {
		return (arg, progressMonitor) -> "End: " + arg;
	}

}
