package org.nasdanika.demos.graph.compute.computers.sync;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.ComputePackage;
import org.nasdanika.demos.graph.compute.Operator;
import org.nasdanika.graph.Element;
import org.nasdanika.graph.emf.EObjectNode;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.graph.processor.Processor;
import org.nasdanika.graph.processor.ProcessorInfo;

/**
 * Reflective target to create synchronous {@link BiFunction} processors.
 */
public class SyncProcessorFactory {
	
	@Processor(
			type = EObjectNode.class,
			value = "#target.isOperator(get())")
	public Object createOperatorProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new OperatorProcessor();
	}
	
	/**
	 * @param node
	 * @return true for solution node
	 */
	public static boolean isOperator(Object obj) {
		return obj instanceof Operator && ((Operator) obj).eClass() == ComputePackage.Literals.OPERATOR;
	}
		
	@Processor(
			type = EObjectNode.class,
			value = "get() instanceof T(org.nasdanika.demos.graph.compute.Assignment)")
	public Object createAssignmentProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new AssignmentProcessor();
	}
	
	@Processor(
			type = EObjectNode.class,
			value = "get() instanceof T(org.nasdanika.demos.graph.compute.Multiplication)")
	public Object createMultiplicationProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new MultiplicationProcessor();
	}
	
	@Processor(
			type = EObjectNode.class,
			value = "get() instanceof T(org.nasdanika.demos.graph.compute.Literal)")
	public Object createLiteralProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new LiteralProcessor(); 
	}
		
	@Processor(
			type = EObjectNode.class,
			value = "get() instanceof T(org.nasdanika.demos.graph.compute.Variable)")
	public Object createVariableProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new VariableProcessor();
	}
	
	@Processor(
			type = EObjectNode.class,
			value = "get() instanceof T(org.nasdanika.demos.graph.compute.Reference)")
	public Object createReferenceProcessor(
		NodeProcessorConfig<?,?> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorInfo<Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new ReferenceProcessor();
	}

}
