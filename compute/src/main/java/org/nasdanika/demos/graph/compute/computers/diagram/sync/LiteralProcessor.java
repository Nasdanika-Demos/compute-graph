package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.function.BiFunction;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.IncomingHandler;
import org.nasdanika.graph.processor.ProcessorElement;

/**
 * {@link FunctionFlow} synchronous processor
 */
public class LiteralProcessor implements BiFunction<Object, ProgressMonitor, Object> {
	
	private double value;
	
	@ProcessorElement
	public void setElement(org.nasdanika.drawio.Node element) {
		String value = element.getLabel();
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
