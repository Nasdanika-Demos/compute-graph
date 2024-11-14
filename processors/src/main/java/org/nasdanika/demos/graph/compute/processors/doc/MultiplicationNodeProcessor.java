/**
 */
package org.nasdanika.demos.graph.compute.processors.doc;

import java.util.function.Function;

import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Multiplication;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.models.app.Action;
import org.nasdanika.models.app.graph.WidgetFactory;

public class MultiplicationNodeProcessor extends OperatorNodeProcessor<Multiplication> {
	
	public MultiplicationNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			Context context,
			Function<ProgressMonitor, Action> prototypeProvider) {
		super(config, context, prototypeProvider);
	}	
	
} 
