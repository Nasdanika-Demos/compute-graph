package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.IncomingHandler;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class VariableProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	private double value;
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		if (arg instanceof Double) {
			value = (double) arg;
		}
		return value;
	}
	
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getHandler() {
		return this;
	}
	
}
