package org.nasdanika.demos.graph.compute.computers.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Literal;
import org.nasdanika.graph.emf.EObjectNode;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.ProcessorElement;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class LiteralProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	private double value;
	
	@ProcessorElement
	public void setElement(EObjectNode element) {
		String value = ((Literal) element.get()).getValue();
		if ("π".equals(value)) {
			this.value = Math.PI;
		} else {
			this.value = Double.parseDouble(value);
		}
	}
		
	@Override
	public Object apply(Object arg, ProgressMonitor progressMonitor) {
		return value;
	}
	
	@IncomingHandler
	public BiFunction<Object, ProgressMonitor, Object> getIncomingHandler() {
		return this;
	}

}
