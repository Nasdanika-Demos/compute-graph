/**
 */
package org.nasdanika.demos.graph.compute.processors.doc;

import org.eclipse.emf.ecore.EObject;
import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Literal;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.models.app.Action;
import org.nasdanika.models.app.graph.WidgetFactory;

public class LiteralNodeProcessor extends OperandNodeProcessor<Literal> {
	
//	String getValue();
	
	public LiteralNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
			Context context,
			java.util.function.BiFunction<EObject, ProgressMonitor, Action> prototypeProvider) {
		super(config, context, prototypeProvider);
	}
	

} 
