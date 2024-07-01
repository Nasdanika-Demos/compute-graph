package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.drawio.comparators.CartesianNodeComparator;
import org.nasdanika.drawio.comparators.LabelModelElementComparator;
import org.nasdanika.graph.emf.EReferenceConnection;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.OutgoingEndpoint;
import org.nasdanika.graph.processor.OutgoingHandler;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class AssignmentProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	protected Map<org.nasdanika.drawio.Connection, BiFunction<Object, ProgressMonitor, Object>> outgoingEndpoints = Collections.synchronizedMap(new HashMap<>());		
	
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		Object result = getSortedEndpoints().get(1).apply(arg, progressMonitor);
		BiFunction<Object, ProgressMonitor, Object> variableEndpoint = getSortedEndpoints().get(0);
		variableEndpoint.apply(result, progressMonitor);
		return variableEndpoint.apply(arg, progressMonitor);
	}
	
	@OutgoingHandler
	public BiFunction<Object, ProgressMonitor, Object> getVariableHandler(EReferenceConnection connection) {
		if (connection.getIndex() == 0) {
			return getSortedEndpoints().get(1);
		}
		return null;
	}
	
	@OutgoingEndpoint
	public void addOutgoingEndpoint(org.nasdanika.drawio.Connection connection, BiFunction<Object, ProgressMonitor, Object> endpoint) {
		outgoingEndpoints.put(connection, endpoint);
	}
		
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getHandler() {
		return this;
	}
	
	private List<BiFunction<Object, ProgressMonitor, Object>> getSortedEndpoints() {
		CartesianNodeComparator comparator = new CartesianNodeComparator(CartesianNodeComparator.Direction.rightDown, new LabelModelElementComparator(false));
		return outgoingEndpoints.entrySet().stream().sorted((a,b) -> comparator.compare(a.getKey().getTarget(), b.getKey().getTarget())).map(Map.Entry::getValue).toList();
	}
	

}
