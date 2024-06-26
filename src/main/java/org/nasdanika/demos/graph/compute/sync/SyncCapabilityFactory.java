package org.nasdanika.demos.graph.compute.sync;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.capability.CapabilityProvider;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.graph.processor.CapabilityProcessorFactory.ProcessorRequirement;
import org.nasdanika.graph.processor.ReflectiveProcessorServiceFactory.ReflectiveProcessorFactoryProviderTargetRequirement;

public class SyncCapabilityFactory implements CapabilityFactory<ReflectiveProcessorFactoryProviderTargetRequirement<Object, BiFunction<Object, ProgressMonitor, Object>>, Object> {

	@Override
	public boolean canHandle(Object requirement) {
		if (requirement instanceof ReflectiveProcessorFactoryProviderTargetRequirement) {
			ReflectiveProcessorFactoryProviderTargetRequirement<?,?> targetRequirement = (ReflectiveProcessorFactoryProviderTargetRequirement<?,?>) requirement;
			if (targetRequirement.processorType() == BiFunction.class) { // To account for generic parameters create a non-generic sub-interface binding those parameters.
				ProcessorRequirement<?, ?> processorRequiremment = targetRequirement.processorRequirement();
				if (processorRequiremment.handlerType() == BiFunction.class && processorRequiremment.endpointType() == BiFunction.class) {
					return processorRequiremment.requirement() == null; // Customize if needed
				}
			}
		}
		return false;
	}

	@Override
	public CompletionStage<Iterable<CapabilityProvider<Object>>> create(
			ReflectiveProcessorFactoryProviderTargetRequirement<Object, BiFunction<Object, ProgressMonitor, Object>> requirement,
			BiFunction<Object, ProgressMonitor, CompletionStage<Iterable<CapabilityProvider<Object>>>> resolver,
			ProgressMonitor progressMonitor) {
		
		return CompletableFuture.completedStage(Collections.singleton(CapabilityProvider.of(new SyncProcessorFactory())));	
	}

}
