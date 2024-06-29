package org.nasdanika.demos.graph.compute.computers.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.emf.EReferenceConnection;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.OutgoingEndpoint;
import org.nasdanika.graph.processor.OutgoingHandler;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class AssignmentProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	private BiFunction<Object, ProgressMonitor, Object> expressionEndpoint;
	private BiFunction<Object, ProgressMonitor, Object> variableEndpoint;
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Object result = expressionEndpoint.apply(arg, progressMonitor);
		variableEndpoint.apply(result, progressMonitor);
		return variableEndpoint.apply(arg, progressMonitor);
	}
	
	@OutgoingHandler
	public BiFunction<Object, ProgressMonitor, Object> getVariableHandler(EReferenceConnection connection) {
		if (connection.getIndex() == 0) {
			return expressionEndpoint;
		}
		return null;
	}
	
	@OutgoingEndpoint
	public void addVariableEndpoint(EReferenceConnection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		if (connection.getIndex() == 0) {
			variableEndpoint = endpoint;
		}
	}
		
	@OutgoingEndpoint
	public void addExpressionEndpoint(EReferenceConnection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		if (connection.getIndex() == 1) {
			expressionEndpoint = endpoint;
		}
	}
		
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getHandler() {
		return this;
	}

}
