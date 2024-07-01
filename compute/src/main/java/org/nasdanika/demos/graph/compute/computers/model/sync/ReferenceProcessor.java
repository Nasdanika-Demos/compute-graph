package org.nasdanika.demos.graph.compute.computers.model.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.emf.EReferenceConnection;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.OutgoingEndpoint;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class ReferenceProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected BiFunction<Object, ProgressMonitor, Object> outgoingEndpoint;	
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		return outgoingEndpoint.apply(arg, progressMonitor);
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(EReferenceConnection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoint = endpoint;
	}
	
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getIncomingHandler() {
		return this;
	}

}
