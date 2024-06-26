import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.demos.graph.compute.sync.SyncCapabilityFactory;

module org.nasdanika.demos.graph.compute {
		
	requires transitive org.nasdanika.graph.model;
	requires transitive org.nasdanika.models.ecore.graph;
	
	exports org.nasdanika.demos.graph.compute.sync;
	opens org.nasdanika.demos.graph.compute.sync to org.nasdanika.common; // For loading resources

	provides CapabilityFactory with SyncCapabilityFactory;
	
}
