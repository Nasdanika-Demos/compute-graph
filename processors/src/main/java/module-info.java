import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.demos.graph.compute.processors.doc.ComputeNodeProcessorFactoryCapabilityFactory;
import org.nasdanika.demos.graph.compute.processors.ecore.ECoreGenComputeProcessorsCapabilityFactory;

module org.nasdanika.demos.graph.compute.processors {
		
	requires transitive org.nasdanika.demos.graph.compute;
	requires transitive org.nasdanika.models.ecore.graph;
	
	exports org.nasdanika.demos.graph.compute.processors.ecore;
	opens org.nasdanika.demos.graph.compute.processors.ecore; // For loading resources
	
	exports org.nasdanika.demos.graph.compute.processors.doc;
	opens org.nasdanika.demos.graph.compute.processors.doc; // For loading resources

	provides CapabilityFactory with 
		ECoreGenComputeProcessorsCapabilityFactory,
		ComputeNodeProcessorFactoryCapabilityFactory;
	
}
