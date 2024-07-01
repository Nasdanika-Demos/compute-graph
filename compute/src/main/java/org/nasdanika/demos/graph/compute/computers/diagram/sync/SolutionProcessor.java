package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.drawio.comparators.CartesianNodeComparator;
import org.nasdanika.drawio.comparators.LabelModelElementComparator;
import org.nasdanika.graph.processor.OutgoingEndpoint;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class SolutionProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected Map<org.nasdanika.drawio.Connection, BiFunction<Object, ProgressMonitor, Object>> outgoingEndpoints = Collections.synchronizedMap(new HashMap<>());		
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Map<BiFunction<Object, ProgressMonitor, Object>, Object> outgoingEndpointsResults = new LinkedHashMap<>();
		CartesianNodeComparator comparator = new CartesianNodeComparator(CartesianNodeComparator.Direction.rightDown, new LabelModelElementComparator(false));
		for (BiFunction<Object, ProgressMonitor, Object> e: outgoingEndpoints.entrySet().stream().sorted((a,b) -> comparator.compare(a.getKey().getTarget(), b.getKey().getTarget())).map(Map.Entry::getValue).toList()) {
			outgoingEndpointsResults.put(e, e.apply(arg, progressMonitor));
		}
		return outgoingEndpointsResults;
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(org.nasdanika.drawio.Connection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoints.put(connection, endpoint);
	}

}
