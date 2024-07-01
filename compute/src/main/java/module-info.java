import org.nasdanika.capability.CapabilityFactory;

module org.nasdanika.demos.graph.compute.computers {
		
	requires transitive org.nasdanika.demos.graph.compute;
	requires transitive org.nasdanika.models.ecore.graph;
	
	exports org.nasdanika.demos.graph.compute.computers.model.sync;
	opens org.nasdanika.demos.graph.compute.computers.model.sync to org.nasdanika.common; // For reflection

	exports org.nasdanika.demos.graph.compute.computers.diagram.sync;
	opens org.nasdanika.demos.graph.compute.computers.diagram.sync to org.nasdanika.common; // For reflection

	provides CapabilityFactory with 
		org.nasdanika.demos.graph.compute.computers.model.sync.SyncCapabilityFactory,
		org.nasdanika.demos.graph.compute.computers.diagram.sync.SyncCapabilityFactory;
	
}
