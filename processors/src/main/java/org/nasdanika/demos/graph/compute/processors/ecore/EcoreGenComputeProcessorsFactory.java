package org.nasdanika.demos.graph.compute.processors.ecore;

import java.util.function.BiConsumer;

import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.common.Reflector.Factory;
import org.nasdanika.demos.graph.compute.ComputePackage;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.html.model.app.Action;
import org.nasdanika.html.model.app.Label;
import org.nasdanika.html.model.app.graph.WidgetFactory;
import org.nasdanika.models.ecore.graph.processors.EPackageNodeProcessor;
import org.nasdanika.models.ecore.graph.processors.EPackageNodeProcessorFactory;

@EPackageNodeProcessorFactory(nsURI = ComputePackage.eNS_URI)
public class EcoreGenComputeProcessorsFactory {

	private Context context;
	
	@Factory
	public final AssignmentProcessorFactory assignmentProcessorFactory;

	@Factory
	public final LiteralProcessorFactory literalProcessorFactory;

	@Factory
	public final MultiplicationProcessorFactory multiplicationProcessorFactory;

	@Factory
	public final OperandProcessorFactory operandProcessorFactory;

	@Factory
	public final OperatorProcessorFactory operatorProcessorFactory;

	@Factory
	public final ReferenceProcessorFactory refrerenceProcessorFactory;

	@Factory
	public final VariableProcessorFactory variableProcessorFactory;
		
	public EcoreGenComputeProcessorsFactory(Context context) {
		this.context = context;
		assignmentProcessorFactory = new AssignmentProcessorFactory(context);
		literalProcessorFactory = new LiteralProcessorFactory(context);
		multiplicationProcessorFactory = new MultiplicationProcessorFactory(context);
		operandProcessorFactory = new OperandProcessorFactory(context);
		operatorProcessorFactory = new OperatorProcessorFactory(context);
		refrerenceProcessorFactory = new ReferenceProcessorFactory(context);
		variableProcessorFactory = new VariableProcessorFactory(context);
	}
	
	/**
	 * Test of different ways to configure action prototype.
	 * @param config
	 * @param prototypeProvider
	 * @param progressMonitor
	 * @return
	 */
	@EPackageNodeProcessorFactory(
			label = "Compute Model",
			actionPrototype = """
                    app-action:
                        text: Param
                        icon: fas fa-user					
					""",
			icon = "https://img.icons8.com/nolan/64/apple-calculator.png",
			description = "A model of operands and operators",
			documentation =  """
				Family model is used to demonstrate different Nasdanika technologies such as:
				
				* Generating of metamodel (Ecore) documentation like this one
				* Loading of models from MS Excel and Drawio diagrams
				* Generation of HTML sites from loaded models		
				
				The model was created to mimic the model from [Eclipse Sirius Basic Family](https://wiki.eclipse.org/Sirius/Tutorials/BasicFamily) tutorial. 
				This site uses fee icons from [Icons8](https://icons8.com/) and quotes [Wikipedia](https://www.wikipedia.org/) articles.
				
				The diagram below is interactive:
				
				 * Hover the mouse pointer over the shapes and connections to see tooltips
				 * Click on the shapes and connections to navigate to the pages of the respective model elements.
						
				"""
	)
	public EPackageNodeProcessor createEPackageProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			java.util.function.Function<ProgressMonitor, Action> prototypeProvider,
			BiConsumer<Label, ProgressMonitor> labelConfigurator,
			ProgressMonitor progressMonitor) {		
		return new EPackageNodeProcessor(config, context, prototypeProvider) {
			
			@Override
			public void configureLabel(Object source, Label label, ProgressMonitor progressMonitor) {
				super.configureLabel(source, label, progressMonitor);
				if (labelConfigurator != null) {
					labelConfigurator.accept(label, progressMonitor);
				}
			}
			
		};
	}	

}
