package org.nasdanika.demos.graph.compute.generator.tests;

import java.io.File;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.icepear.echarts.charts.graph.GraphEdgeLineStyle;
import org.icepear.echarts.charts.graph.GraphEmphasis;
import org.icepear.echarts.charts.graph.GraphSeries;
import org.icepear.echarts.components.series.SeriesLabel;
import org.icepear.echarts.render.Engine;
import org.jgrapht.alg.drawing.FRLayoutAlgorithm2D;
import org.jgrapht.alg.drawing.model.Box2D;
import org.jgrapht.alg.drawing.model.MapLayoutModel2D;
import org.jgrapht.alg.drawing.model.Point2D;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.junit.jupiter.api.Test;
import org.nasdanika.common.Context;
import org.nasdanika.common.Diagnostic;
import org.nasdanika.common.ExecutionException;
import org.nasdanika.common.MutableContext;
import org.nasdanika.common.PrintStreamProgressMonitor;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.processors.doc.ArchitectureActionGenerator;
import org.nasdanika.demos.graph.compute.processors.doc.ArchitectureNodeProcessorFactory;
import org.nasdanika.html.model.app.gen.ActionSiteGenerator;
import org.nasdanika.models.architecture.util.ArchitectureDrawioResourceFactory;
import org.nasdanika.models.echarts.graph.Graph;
import org.nasdanika.models.echarts.graph.GraphFactory;
import org.nasdanika.models.echarts.graph.Item;
import org.nasdanika.models.echarts.graph.Node;

public class TestArchitecture {
		
	@Test
	public void testGenerateAWSSite() throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("drawio", new ArchitectureDrawioResourceFactory(uri -> resourceSet.getEObject(uri, true)));
		File awsDiagramFile = new File("aws.drawio").getCanonicalFile();
		Resource awsResource = resourceSet.getResource(URI.createFileURI(awsDiagramFile.getAbsolutePath()), true);
		
		// Generating an action model
		ProgressMonitor progressMonitor = new PrintStreamProgressMonitor();
		MutableContext context = Context.EMPTY_CONTEXT.fork();
		
		Consumer<Diagnostic> diagnosticConsumer = d -> d.dump(System.out, 0);		
		
		File actionModelsDir = new File("target\\action-models\\");
		actionModelsDir.mkdirs();
								
		File output = new File(actionModelsDir, "aws-actions.xmi");
		
		ArchitectureActionGenerator actionGenerator = new ArchitectureActionGenerator(
				awsResource.getContents().get(0),
				new ArchitectureNodeProcessorFactory(context, null));
		
		actionGenerator.generateActionModel(
				diagnosticConsumer, 
				output,
				progressMonitor);
				
		// Generating a web site
		String rootActionResource = "aws-actions.yml";
		URI rootActionURI = URI.createFileURI(new File(rootActionResource).getAbsolutePath());
		
		String pageTemplateResource = "page-template.yml";
		URI pageTemplateURI = URI.createFileURI(new File(pageTemplateResource).getAbsolutePath());
		
		String siteMapDomain = "https://graph.models.nasdanika.org/demos/aws";		
		ActionSiteGenerator actionSiteGenerator = new ActionSiteGenerator() {
			
			@Override
			protected boolean isDeleteOutputPath(String path) {
				return !"CNAME".equals(path);				
			}
			
			@Override
			protected Context createContext(ProgressMonitor progressMonitor) {
				return context;
			}
			
			
		};		
		
		Map<String, Collection<String>> errors = actionSiteGenerator.generate(
				rootActionURI, 
				pageTemplateURI, 
				siteMapDomain, 
				new File("../docs/demo/aws"),  
				new File("target/aws-doc-site-work-dir"), 
				true);
				
		int errorCount = 0;
		for (Entry<String, Collection<String>> ee: errors.entrySet()) {
			System.err.println(ee.getKey());
			for (String error: ee.getValue()) {
				System.err.println("\t" + error);
				++errorCount;
			}
		}
		
		System.out.println("There are " + errorCount + " site errors");
		
		if (errors.size() != 37) {
			throw new ExecutionException("There are problems with pages: " + errorCount);
		}		
		
	}
	
	// --- Module dependency graph ---
	
	private Node getModuleNode(
			Module module, 
			ModuleLayer layer, 
			Graph graph, 
			Item nsdCategory,
			Item eclipseCategory,
			Item javaCategory,
			Item otherCategory) {
		for (Node n: graph.getNodes()) {
			if (n.getName().equals(module.getName())) {
				return n;
			}
		}
		Node ret = GraphFactory.eINSTANCE.createNode();
		ret.setName(module.getName());
		
		if (ret.getName().startsWith("org.nasdanika.")) {
			ret.setCategory(nsdCategory);
		} else if (ret.getName().startsWith("org.eclipse.")) {
			ret.setCategory(eclipseCategory);
		} else if (ret.getName().startsWith("java.")) {
			ret.setCategory(javaCategory);
		} else {
			ret.setCategory(otherCategory);
		}
		
		ret.getSymbolSize().add(10.0 + Math.log(1 + module.getDescriptor().exports().size()));
		
//		org.nasdanika.models.echarts.graph.Label label = GraphFactory.eINSTANCE.createLabel();
//		label.setColor("red");
//		ret.setLabel(label);
		
		graph.getNodes().add(ret);
		return ret;
	}
	
	private Node moduleToNode(
			Module module, 
			ModuleLayer layer, 
			Graph graph,
			Item nsdCategory,
			Item eclipseCategory,
			Item javaCategory,
			Item otherCategory) {
		ModuleDescriptor moduleDescriptor = module.getDescriptor();		
		Node moduleNode = getModuleNode(module, layer, graph, nsdCategory, eclipseCategory, javaCategory, otherCategory);
		for (Requires req: moduleDescriptor.requires()) {
			Optional<Module> rmo = layer.findModule(req.name());
			if (rmo.isPresent()) {
				Node reqNode = moduleToNode(rmo.get(), layer, graph, nsdCategory, eclipseCategory, javaCategory, otherCategory);
				org.nasdanika.models.echarts.graph.Link reqLink = GraphFactory.eINSTANCE.createLink();				
				reqLink.setTarget(reqNode);
				moduleNode.getOutgoingLinks().add(reqLink);
			}
		}		
		return moduleNode;
	}
	
	/**
	 * Generates Graph JSON from a model
	 */
	@Test
	public void testModuleGraphForce() {
		Module thisModule = getClass().getModule();
		ModuleLayer moduleLayer = thisModule.getLayer();
		
		Graph graph = GraphFactory.eINSTANCE.createGraph();
		
		Item nsdCategory = GraphFactory.eINSTANCE.createItem();
		nsdCategory.setName("Nasdanika");
		graph.getCategories().add(nsdCategory);
		
		Item eclipseCategory = GraphFactory.eINSTANCE.createItem();
		eclipseCategory.setName("Eclipse");
		graph.getCategories().add(eclipseCategory);
		
		Item javaCategory = GraphFactory.eINSTANCE.createItem();
		javaCategory.setName("Java");
		graph.getCategories().add(javaCategory);
		
		Item otherCategory = GraphFactory.eINSTANCE.createItem();
		otherCategory.setName("Other");
		graph.getCategories().add(otherCategory);
		
		moduleToNode(thisModule, moduleLayer, graph, nsdCategory, eclipseCategory, javaCategory, otherCategory);
		forceLayout(graph);
		
		GraphSeries graphSeries = new org.icepear.echarts.charts.graph.GraphSeries()
				.setSymbolSize(24)
				.setDraggable(true)				
				.setLayout("none")
	            .setLabel(new SeriesLabel().setShow(true).setPosition("right"))
	            .setLineStyle(new GraphEdgeLineStyle().setColor("source").setCurveness(0))
	            .setRoam(true)
	            .setEmphasis(new GraphEmphasis().setFocus("adjacency"))
	            .setEdgeSymbol(new String[] { "none", "arrow" }); // Line style width 10?
		
		graph.configureGraphSeries(graphSeries);
		
    	org.icepear.echarts.Graph echartsGraph = new org.icepear.echarts.Graph()
                .setTitle("Module Dependencies")
//                .setTooltip("item")
                .setLegend()
                .addSeries(graphSeries);
    	
	    Engine engine = new Engine();
	    new File("target/charts").mkdirs();
	    engine.render("target/charts/module-graph-force.html", echartsGraph, "90%", "2000px", false);		
	}
	
	/**
	 * Uses JGraphT {@link FRLayoutAlgorithm2D} to force layout the graph.
	 * @param graph
	 */
	protected void forceLayout(Graph graph) {
		// Using JGraphT for force layout
		DefaultUndirectedGraph<Node, org.nasdanika.models.echarts.graph.Link> dGraph = new DefaultUndirectedGraph<>(org.nasdanika.models.echarts.graph.Link.class);
		
		// Populating
		for (Node node: graph.getNodes()) {
			dGraph.addVertex(node);
		}	
		
		for (Node node: graph.getNodes()) {
			for (org.nasdanika.models.echarts.graph.Link link: node.getOutgoingLinks()) {
				if (dGraph.getEdge(link.getTarget(), node) == null) { // Not yet connected, connect
					dGraph.addEdge(node, link.getTarget(), link);
				}
			}
		}		
		
		FRLayoutAlgorithm2D<Node, org.nasdanika.models.echarts.graph.Link> forceLayout = new FRLayoutAlgorithm2D<>();
		MapLayoutModel2D<Node> model = new MapLayoutModel2D<>(new Box2D(1000.0, 800.0));
		forceLayout.layout(dGraph, model);
		model.forEach(ne -> {
			Node node = ne.getKey();
			Point2D point = ne.getValue();
			node.setX(point.getX());
			node.setY(point.getY());
		});
		
	}	
	
}
