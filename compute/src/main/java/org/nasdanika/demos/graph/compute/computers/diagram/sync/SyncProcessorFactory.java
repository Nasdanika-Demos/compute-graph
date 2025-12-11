package org.nasdanika.demos.graph.compute.computers.diagram.sync;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.drawio.Node;
import org.nasdanika.graph.Element;
import org.nasdanika.graph.processor.NodeProcessorConfig;
import org.nasdanika.graph.processor.Processor;
import org.nasdanika.graph.processor.ProcessorConfig;
import org.nasdanika.models.app.graph.WidgetFactory;

/**
 * Reflective target to create synchronous {@link BiFunction} processors.
 */
public class SyncProcessorFactory {
	
	@Processor("label == 'Solution'")
	public Object createSolutionProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new SolutionProcessor();
	}
		
	@Processor("label == '='")
	public Object createAssignmentProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new AssignmentProcessor();
	}
	
	@Processor("label == '*'")
	public Object createMultiplicationProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new MultiplicationProcessor();
	}
	
	@Processor("#target.isLiteral(#this)")
	public Object createLiteralProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new LiteralProcessor(); 
	}
	
	/** 
	 * Matching by color
	 * @param node
	 * @return
	 */
	public boolean isLiteral(Node node) {
		Map<String, String> style = node.getStyle();
		return "#e1d5e7".equals(style.get("fillColor")) && style.keySet().contains("ellipse"); // Only circles, excluding legend
	}
		
	@Processor("#target.isVariable(style)")
	public Object createVariableProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new VariableProcessor();
	}
	
	/** 
	 * Matching by color
	 * @param node
	 * @return
	 */
	public boolean isVariable(Map<String, String> style) {
		return "#ffe6cc".equals(style.get("fillColor"));
	}
	
	@Processor("'#bac8d3' == style.get('fillColor')")
	public Object createReferenceProcessor(
		NodeProcessorConfig<WidgetFactory, WidgetFactory, Object> config, 
		boolean parallel, 
		BiConsumer<Element,BiConsumer<ProcessorConfig<WidgetFactory, WidgetFactory, Object>,ProgressMonitor>> infoProvider,
		Function<ProgressMonitor, Object> next,		
		ProgressMonitor progressMonitor) {
		return new ReferenceProcessor();
	}

}
