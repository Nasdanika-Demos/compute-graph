/**
 */
package org.nasdanika.demos.graph.compute.processors.ecore;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.ComputePackage;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.models.app.Action;
import org.nasdanika.models.app.Label;
import org.nasdanika.models.app.graph.WidgetFactory;
import org.nasdanika.models.ecore.graph.processors.EClassNodeProcessor;
import org.nasdanika.models.ecore.graph.processors.EClassifierNodeProcessorFactory;

@EClassifierNodeProcessorFactory(classifierID = ComputePackage.ASSIGNMENT)
public class AssignmentProcessorFactory {
		
	protected Context context;
	
	public AssignmentProcessorFactory(Context context) {
		this.context = context;
	}
	
	@EClassifierNodeProcessorFactory(
			description = "Assigns the value of its right-hand operand to its left-hand operand",
			documentation = 
					"""
					Assigns the value of its right-hand operand (index 1) to a its left-hand operand (index 0), which should be a variable.
					""",
			icon = "fas fa-equals"
	)
	public EClassNodeProcessor createOperandProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			java.util.function.BiFunction<EObject, ProgressMonitor, Action> prototypeProvider,
			BiConsumer<Label, ProgressMonitor> labelConfigurator,
			ProgressMonitor progressMonitor) {		
		return new EClassNodeProcessor(config, context, prototypeProvider) {
			
			@Override
			public void configureLabel(Object source, Label label, ProgressMonitor progressMonitor) {
				super.configureLabel(source, label, progressMonitor);
				if (labelConfigurator != null) {
					labelConfigurator.accept(label, progressMonitor);
				}
			}
			
//			@Override
//			protected EModelElementDocumentation getLoadDocumentation() {
//				return new EModelElementDocumentation("""
//						Some ``documentation``:
//						
//						```yaml
//						key: value
//						```
//						
//						""", 
//						Util.createClassURI(getClass()));
//			}
						
		};
	}	
		
} 
