package org.nasdanika.demos.graph.compute.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.OutgoingEndpoint;

public class StartProcessor implements BiFunction<Object, ProgressMonitor, Object> {

	protected Collection<BiFunction<Object, ProgressMonitor, Object>> outgoingEndpoints = Collections.synchronizedCollection(new ArrayList<>());	
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Map<BiFunction<Object, ProgressMonitor, Object>, Object> outgoingEndpointsResults = new LinkedHashMap<>();
		for (BiFunction<Object, ProgressMonitor, Object> e: outgoingEndpoints) {
			outgoingEndpointsResults.put(e, e.apply(arg, progressMonitor));
		}
		return outgoingEndpointsResults;
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoints.add(endpoint);
	}

}
