package org.nasdanika.demos.graph.compute.processors.doc;

import java.util.function.BiConsumer;

import org.eclipse.emf.common.util.URI;
import org.nasdanika.common.Context;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.Assignment;
import org.nasdanika.demos.graph.compute.Literal;
import org.nasdanika.demos.graph.compute.Multiplication;
import org.nasdanika.demos.graph.compute.Operand;
import org.nasdanika.demos.graph.compute.Operator;
import org.nasdanika.demos.graph.compute.Reference;
import org.nasdanika.demos.graph.compute.Variable;
import org.nasdanika.graph.Element;
import org.nasdanika.graph.emf.EObjectNode;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.graph.processor.ProcessorInfo;
import org.nasdanika.graph.processor.emf.EObjectNodeProcessor;
import org.nasdanika.models.app.Action;
import org.nasdanika.models.app.Label;
import org.nasdanika.models.app.AppFactory;
import org.nasdanika.models.app.graph.WidgetFactory;
import org.nasdanika.ncore.util.NcoreUtil;

/**
 * Node processor factory to use with {@link EObjectReflectiveProcessorFactory} to generate family documentation.
 * @author Pavel
 *
 */
public class ComputeNodeProcessorFactory {
			
	private Context context;
	private java.util.function.BiFunction<URI, ProgressMonitor, Label> prototypeProvider;

	protected java.util.function.Function<ProgressMonitor, Action> getPrototypeProvider(NodeProcessorConfig<WidgetFactory, WidgetFactory> config) {
		return progressMonitor -> {
			if (prototypeProvider != null) {
				for (URI identifier: NcoreUtil.getIdentifiers(((EObjectNode) config.getElement()).get())) {
					Label prototype = prototypeProvider.apply(identifier, progressMonitor);
					if (prototype instanceof Action) {
						return (Action) prototype;
					}				
				}			
			}
			return AppFactory.eINSTANCE.createAction();
		};		
	}
	
	/**
	 * 
	 * @param context
	 * @param reflectiveFactories Objects with annotated methods for creating processors. 
	 */
	public ComputeNodeProcessorFactory(
			Context context, 
			java.util.function.BiFunction<URI, ProgressMonitor, Label> prototypeProvider)  {
		this.context = context;
		this.prototypeProvider = prototypeProvider;
	}
	
	@EObjectNodeProcessor(type = Assignment.class)
	public Object createAssignmentNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			boolean parallel, 
			BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
			ProgressMonitor progressMonitor) {
		
		return new AssignmentNodeProcessor(config, context, getPrototypeProvider(config));
	}
	
	@EObjectNodeProcessor(type = Literal.class)
	public Object createLiteranNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			boolean parallel, 
			BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
			ProgressMonitor progressMonitor) {
		
		return new LiteralNodeProcessor(config, context, getPrototypeProvider(config));
	}
	
	@EObjectNodeProcessor(type = Multiplication.class)
	public Object createMultiplicationNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			boolean parallel, 
			BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
			ProgressMonitor progressMonitor) {
		
		return new MultiplicationNodeProcessor(config, context, getPrototypeProvider(config));
	}
		
	@EObjectNodeProcessor(type = Operand.class)
	public Object createOperandNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			boolean parallel, 
			BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
			ProgressMonitor progressMonitor) {
		
		return new OperandNodeProcessor<Operand>(config, context, getPrototypeProvider(config));
	}
	
	@EObjectNodeProcessor(type = Operator.class)
	public Object createOperatorNodeProcessor(
			NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
			boolean parallel, 
			BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
			ProgressMonitor progressMonitor) {
		
		return new OperatorNodeProcessor<Operator>(config, context, getPrototypeProvider(config));
	}
	
	@EObjectNodeProcessor(type = Reference.class)
	public Object createReferenceNodeProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		ProgressMonitor progressMonitor) {
	
	return new ReferenceNodeProcessor(config, context, getPrototypeProvider(config));
	}
	
	@EObjectNodeProcessor(type = Variable.class)
	public Object createVariableNodeProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		ProgressMonitor progressMonitor) {
	
	return new VariableNodeProcessor(config, context, getPrototypeProvider(config));
	}
		
}
