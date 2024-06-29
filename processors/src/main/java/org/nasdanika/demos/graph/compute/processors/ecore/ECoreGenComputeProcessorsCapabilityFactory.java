package org.nasdanika.demos.graph.compute.processors.ecore;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.capability.CapabilityProvider;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.models.ecore.graph.processors.EcoreNodeProcessorFactoryCapabilityFactory.TargetRequirement;

public class ECoreGenComputeProcessorsCapabilityFactory implements CapabilityFactory<TargetRequirement, Object> {

	@Override
	public boolean canHandle(Object requirement) {
		return requirement instanceof TargetRequirement;
	}

	@Override
	public CompletionStage<Iterable<CapabilityProvider<Object>>> create(
			TargetRequirement requirement,
			BiFunction<Object, ProgressMonitor, CompletionStage<Iterable<CapabilityProvider<Object>>>> resolver,
			ProgressMonitor progressMonitor) {
		
		return CompletableFuture.completedStage(
				Collections.singleton(
						CapabilityProvider.of(
								new EcoreGenComputeProcessorsFactory(requirement.requirement().context()))));
	}

}
