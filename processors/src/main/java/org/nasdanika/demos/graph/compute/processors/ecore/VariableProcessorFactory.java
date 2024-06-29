/**
 */
package org.nasdanika.demos.graph.compute.processors.ecore;

import java.util.function.BiConsumer;

import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.common.Util;
import org.nasdanika.demos.graph.compute.ComputePackage;
import org.nasdanika.emf.EmfUtil.EModelElementDocumentation;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.html.model.app.Action;
import org.nasdanika.html.model.app.Label;
import org.nasdanika.html.model.app.graph.WidgetFactory;
import org.nasdanika.models.ecore.graph.processors.EClassNodeProcessor;
import org.nasdanika.models.ecore.graph.processors.EClassifierNodeProcessorFactory;

@EClassifierNodeProcessorFactory(classifierID = ComputePackage.VARIABLE)
public class VariableProcessorFactory {
	
	protected Context context;
	
	public VariableProcessorFactory(Context context) {
		this.context = context;
	}
	
	@EClassifierNodeProcessorFactory(
			label = "Person",
			description = "In this model a person is an abstract supertype of Man and Woman",
			documentation = 
					"""
					A person (pl.: people or persons, depending on context) is a being who has certain capacities or attributes such as reason, morality, 
					consciousness or self-consciousness, and being a part of a culturally established form of social relations such as kinship, 
					ownership of property, or legal responsibility. The defining features of personhood and, consequently, what makes a person count as a person, 
					differ widely among cultures and contexts.
	
					In addition to the question of personhood, of what makes a being count as a person to begin with, there are further questions about personal identity and self: 
					both about what makes any particular person that particular person instead of another, 
					and about what makes a person at one time the same person as they were or will be at another time despite any intervening changes.
	
					The plural form "people" is often used to refer to an entire nation or ethnic group (as in "a people"), and this was the original meaning of the word; 
					it subsequently acquired its use as a plural form of person. The plural form "persons" is often used in philosophical and legal writing.
					""",
			icon = "fas fa-user"
	)
	public EClassNodeProcessor createOperandProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			java.util.function.Function<ProgressMonitor, Action> prototypeProvider,
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
			
			@Override
			protected EModelElementDocumentation getLoadDocumentation() {
				return new EModelElementDocumentation("""
						Some ``documentation``:
						
						```yaml
						key: value
						```
						
						""", 
						Util.createClassURI(getClass()));
			}
						
		};
	}	

//	String getName();

} 
