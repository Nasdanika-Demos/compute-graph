import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.demos.graph.compute.computers.sync.SyncCapabilityFactory;

module org.nasdanika.demos.graph.compute.computers {
		
	requires transitive org.nasdanika.demos.graph.compute;
	requires transitive org.nasdanika.models.ecore.graph;
	
	exports org.nasdanika.demos.graph.compute.computers.sync;
	opens org.nasdanika.demos.graph.compute.computers.sync to org.nasdanika.common; // For loading resources

	provides CapabilityFactory with SyncCapabilityFactory;
	
}
