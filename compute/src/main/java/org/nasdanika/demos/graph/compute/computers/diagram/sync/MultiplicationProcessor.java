package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.OutgoingEndpoint;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class MultiplicationProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected Collection<BiFunction<Object, ProgressMonitor, Object>> outgoingEndpoints = Collections.synchronizedCollection(new ArrayList<>());	
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		double ret = 1; 
		for (BiFunction<Object, ProgressMonitor, Object> e: outgoingEndpoints) {
			ret *=  (double) e.apply(arg, progressMonitor);
		}
		return ret;
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(org.nasdanika.drawio.Connection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoints.add(endpoint);
	}
	
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getIncomingHandler() {
		return this;
	}

}
