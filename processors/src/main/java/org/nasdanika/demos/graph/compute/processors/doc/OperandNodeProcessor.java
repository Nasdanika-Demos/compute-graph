/**
 */
package org.nasdanika.demos.graph.compute.processors.doc;

import java.util.function.Function;

import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Operand;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.models.app.Action;
import org.nasdanika.models.app.graph.WidgetFactory;
import org.nasdanika.models.app.graph.emf.EObjectNodeProcessor;

public class OperandNodeProcessor<T extends Operand> extends EObjectNodeProcessor<T> {

	public OperandNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			Context context,
			Function<ProgressMonitor, Action> prototypeProvider) {
		super(config, context, prototypeProvider);
	}
} 
