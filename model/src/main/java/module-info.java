import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.demos.graph.compute.util.ComputeEPackageResourceSetCapabilityFactory;

module org.nasdanika.demos.graph.compute {
	exports org.nasdanika.demos.graph.compute;
	exports org.nasdanika.demos.graph.compute.impl;
	exports org.nasdanika.demos.graph.compute.util;
	
	requires transitive org.eclipse.emf.ecore;
	requires transitive org.eclipse.emf.common;	
	requires transitive org.nasdanika.graph.model;
	
	provides CapabilityFactory with ComputeEPackageResourceSetCapabilityFactory; 
	
}