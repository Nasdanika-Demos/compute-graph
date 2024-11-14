package org.nasdanika.demos.graph.compute.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.jupiter.api.Test;
import org.nasdanika.capability.CapabilityLoader;
import org.nasdanika.capability.ServiceCapabilityFactory;
import org.nasdanika.capability.ServiceCapabilityFactory.Requirement;
import org.nasdanika.capability.emf.ResourceSetRequirement;
import org.nasdanika.common.Context;
import org.nasdanika.common.PrintStreamProgressMonitor;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.common.Transformer;
import org.nasdanika.graph.emf.EObjectGraphFactory;
import org.nasdanika.graph.emf.EObjectNode;
import org.nasdanika.graph.processor.CapabilityProcessorFactory;
import org.nasdanika.graph.processor.NodeProcessorInfo;
import org.nasdanika.graph.processor.NopEndpointProcessorConfigFactory;
import org.nasdanika.graph.processor.ProcessorConfig;
import org.nasdanika.graph.processor.ProcessorFactory;
import org.nasdanika.graph.processor.ProcessorInfo;
import org.nasdanika.graph.processor.ReflectiveProcessorFactoryProvider;

public class ComputeGraphTests {
	@Test
	public void testDrawioSyncComputeGraph() throws Exception {
		File diagramFile = new File("parse-tree.drawio").getCanonicalFile();
		org.nasdanika.drawio.Document diagram = org.nasdanika.drawio.Document.load(diagramFile.toURI().toURL());
		// Configs and processors. Pass-through (dumb) connections, override isPassthrough to return true for "smart" connections
		NopEndpointProcessorConfigFactory<Function<Object,Object>> processorConfigFactory = new NopEndpointProcessorConfigFactory<>();
		
		Transformer<org.nasdanika.graph.Element, ProcessorConfig> transformer = new Transformer<>(processorConfigFactory);
		ProgressMonitor progressMonitor = new PrintStreamProgressMonitor();
		Map<org.nasdanika.graph.Element, ProcessorConfig> configs = transformer.transform(Collections.singleton(diagram), false, progressMonitor);
		
		ReflectiveProcessorFactoryProvider<BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>> processorFactoryProvider = new ReflectiveProcessorFactoryProvider<>(new org.nasdanika.demos.graph.compute.computers.diagram.sync.SyncProcessorFactory());
		ProcessorFactory<BiFunction<Object, ProgressMonitor, Object>> processorFactory = processorFactoryProvider.getFactory(); 
		
		Map<org.nasdanika.graph.Element, ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>>> processors = processorFactory.createProcessors(configs.values(), false, progressMonitor);
		
		// Solution processor
		ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>> solutionProcessorInfo = processors
				.values()
				.stream()
				.filter(i -> i.getProcessor() != null  && i.getElement() instanceof org.nasdanika.drawio.Node && "Solution".equals(((org.nasdanika.drawio.Node) i.getElement()).getLabel()))
//				.forEach(i -> System.out.println(i.getProcessor() + " " + i.getParentProcessorConfig() + " " + i.getElement()));
				.findAny()
				.get();
		
		BiFunction<Object, ProgressMonitor, Object> solutionProcessor = solutionProcessorInfo.getProcessor();
		Object result = solutionProcessor.apply("Hello", progressMonitor);
		System.out.println(result);
	}
		
	@Test
	public void testDrawioCapabilitySyncComputeGraph() throws Exception {
		File diagramFile = new File("parse-tree.drawio").getCanonicalFile();
		org.nasdanika.drawio.Document diagram = org.nasdanika.drawio.Document.load(diagramFile.toURI().toURL());
		// Configs and processors. Pass-through (dumb) connections, override isPassthrough to return true for "smart" connections
		NopEndpointProcessorConfigFactory<Function<Object,Object>> processorConfigFactory = new NopEndpointProcessorConfigFactory<>();
		
		Transformer<org.nasdanika.graph.Element, ProcessorConfig> transformer = new Transformer<>(processorConfigFactory);
		ProgressMonitor progressMonitor = new PrintStreamProgressMonitor();
		Map<org.nasdanika.graph.Element, ProcessorConfig> configs = transformer.transform(Collections.singleton(diagram), false, progressMonitor);
		
		CapabilityLoader capabilityLoader = new CapabilityLoader();		
		CapabilityProcessorFactory<Object, BiFunction<Object, ProgressMonitor, Object>> processorFactory = new CapabilityProcessorFactory<Object, BiFunction<Object, ProgressMonitor, Object>>(
				BiFunction.class, 
				BiFunction.class, 
				BiFunction.class, 
				null, 
				capabilityLoader); 
		
		Map<org.nasdanika.graph.Element, ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>>> processors = processorFactory.createProcessors(configs.values(), false, progressMonitor);
		
		// Solution processor
		ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>> solutionProcessorInfo = processors
				.values()
				.stream()
				.filter(i -> i.getProcessor() != null  && i.getElement() instanceof org.nasdanika.drawio.Node && "Solution".equals(((org.nasdanika.drawio.Node) i.getElement()).getLabel()))
//				.forEach(i -> System.out.println(i.getProcessor() + " " + i.getParentProcessorConfig() + " " + i.getElement()));
				.findAny()
				.get();
		
		BiFunction<Object, ProgressMonitor, Object> solutionProcessor = solutionProcessorInfo.getProcessor();
		Object result = solutionProcessor.apply("Hello", progressMonitor);
		System.out.println(result);
	}
				
	@Test
	public void testModelSyncComputeGraph() throws IOException {
		CapabilityLoader capabilityLoader = new CapabilityLoader();
		ProgressMonitor progressMonitor = new PrintStreamProgressMonitor();
		Requirement<ResourceSetRequirement, ResourceSet> requirement = ServiceCapabilityFactory.createRequirement(ResourceSet.class);		
		ResourceSet resourceSet = capabilityLoader.loadOne(requirement, progressMonitor);
		File diagramFile = new File("parse-tree.drawio").getCanonicalFile();
		Resource resource = resourceSet.getResource(URI.createFileURI(diagramFile.getAbsolutePath()), true);			
		EObject root = resource.getContents().get(0);			
		Context context = Context.EMPTY_CONTEXT;
		
		NodeProcessorInfo<BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>> processorInfo = (NodeProcessorInfo<BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>, BiFunction<Object, ProgressMonitor, Object>>) createCapabilityProcessor(root, null, context, progressMonitor);
		BiFunction<Object, ProgressMonitor, Object> processor = processorInfo.getProcessor();
		Object result = processor.apply("Hello", progressMonitor);
		System.out.println(result);
	}
		
	protected ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>> createCapabilityProcessor(EObject root, Object requirement, Context context, ProgressMonitor progressMonitor) {
		// Creating graph		
		EObjectGraphFactory eObjectGraphFactory = new EObjectGraphFactory();  
		Transformer<EObject,EObjectNode> graphFactory = new Transformer<>(eObjectGraphFactory); // Reflective node creation using @ElementFactory annotation
		Map<EObject, EObjectNode> registry = graphFactory.transform(Collections.singleton(root), false, progressMonitor);
		
		// Configs and processors. Pass-through (dumb) connections, override isPassthrough to return true for "smart" connections
		NopEndpointProcessorConfigFactory<Function<Object,Object>> processorConfigFactory = new NopEndpointProcessorConfigFactory<>();
		
		Transformer<org.nasdanika.graph.Element, ProcessorConfig> transformer = new Transformer<>(processorConfigFactory);
		Map<org.nasdanika.graph.Element, ProcessorConfig> configs = transformer.transform(registry.values(), false, progressMonitor);

		CapabilityLoader capabilityLoader = new CapabilityLoader();		
		CapabilityProcessorFactory<Object, BiFunction<Object, ProgressMonitor, Object>> processorFactory = new CapabilityProcessorFactory<Object, BiFunction<Object, ProgressMonitor, Object>>(
				BiFunction.class, 
				BiFunction.class, 
				BiFunction.class, 
				null, 
				capabilityLoader); 
		
		Map<org.nasdanika.graph.Element, ProcessorInfo<BiFunction<Object, ProgressMonitor, Object>>> processors = processorFactory.createProcessors(configs.values(), false, progressMonitor);
		
		// Root element processor
		return processors
				.values()
				.stream()
				.filter(i -> i.getProcessor() != null  && i.getElement() instanceof EObjectNode && ((EObjectNode) i.getElement()).get().eContainer() == null)
//				.forEach(i -> System.out.println(i.getProcessor() + " " + i.getParentProcessorConfig() + " " + i.getElement()));
				.findAny()
				.get();
	}
	
}
