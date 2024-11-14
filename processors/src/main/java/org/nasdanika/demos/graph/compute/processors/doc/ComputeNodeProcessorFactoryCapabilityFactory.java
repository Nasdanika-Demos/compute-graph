package org.nasdanika.demos.graph.compute.processors.doc;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.capability.CapabilityProvider;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.models.app.graph.emf.HtmlAppGenerator.NodeProcessorFactoryRequirement;

import reactor.core.publisher.Flux;

public class ComputeNodeProcessorFactoryCapabilityFactory implements CapabilityFactory<NodeProcessorFactoryRequirement, Object> {

	@Override
	public boolean canHandle(Object requirement) {
		return requirement instanceof NodeProcessorFactoryRequirement;
	}

	@Override
	public CompletionStage<Iterable<CapabilityProvider<Object>>> create(
			NodeProcessorFactoryRequirement requirement,
			Loader loader, 
			ProgressMonitor progressMonitor) {

		ComputeNodeProcessorFactory factory = new ComputeNodeProcessorFactory(requirement.context(), requirement.prototypeProvider());
		if (requirement.factoryPredicate() == null || requirement.factoryPredicate().test(factory)) {
			CapabilityProvider<Object> capabilityProvider = new CapabilityProvider<Object>() {
				
				@Override
				public Flux<Object> getPublisher() {
					return Flux.just(factory);
				}
				
			};			
			
			return CompletableFuture.completedStage(Collections.singleton(capabilityProvider));
		}
		return CompletableFuture.completedStage(Collections.emptyList());
	}

}