package org.nasdanika.demos.graph.compute.computers.model.sync;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.emf.EReferenceConnection;
import org.nasdanika.graph.processor.OutgoingEndpoint;

public class OperatorProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected Map<Integer,BiFunction<Object, ProgressMonitor, Object>> outgoingEndpoints = Collections.synchronizedMap(new TreeMap<>());		
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Map<BiFunction<Object, ProgressMonitor, Object>, Object> outgoingEndpointsResults = new LinkedHashMap<>();
		for (BiFunction<Object, ProgressMonitor, Object> e: outgoingEndpoints.values()) {
			outgoingEndpointsResults.put(e, e.apply(arg, progressMonitor));
		}
		return outgoingEndpointsResults;
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(EReferenceConnection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoints.put(connection.getIndex(), endpoint);
	}

}
