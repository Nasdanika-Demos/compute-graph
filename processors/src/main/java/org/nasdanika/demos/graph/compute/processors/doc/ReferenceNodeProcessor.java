/**
 */
package org.nasdanika.demos.graph.compute.processors.doc;

import java.util.function.Function;

import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Reference;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.html.model.app.Action;
import org.nasdanika.html.model.app.graph.WidgetFactory;

public class ReferenceNodeProcessor extends OperandNodeProcessor<Reference> {

//	String getName();
//	Variable getVariable();

	public ReferenceNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			Context context,
			Function<ProgressMonitor, Action> prototypeProvider) {
		super(config, context, prototypeProvider);
	}

} // Reference
