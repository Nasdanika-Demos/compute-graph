package org.nasdanika.demos.graph.compute.generator.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.Test;
import org.nasdanika.common.Context;
import org.nasdanika.common.Diagnostic;
import org.nasdanika.common.ExecutionException;
import org.nasdanika.common.MutableContext;
import org.nasdanika.common.NullProgressMonitor;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.demos.graph.compute.ComputePackage;
import org.nasdanika.html.bootstrap.Theme;
import org.nasdanika.html.model.app.gen.ActionSiteGenerator;
import org.nasdanika.models.ecore.graph.processors.EcoreActionGenerator;

/**
 * Tests Ecore -> Graph -> Processor -> actions generation
 * @author Pavel
 *
 */
public class TestComputeModelDocGen {
	
	@Test
	public void testGenerateComputModelDoc() throws IOException, DiagnosticException {
		ProgressMonitor progressMonitor = new NullProgressMonitor(); // new PrintStreamProgressMonitor();
		MutableContext context = Context.EMPTY_CONTEXT.fork();
		Consumer<Diagnostic> diagnosticConsumer = d -> d.dump(System.out, 0);
		
		File actionModelsDir = new File("target\\action-models\\");
		actionModelsDir.mkdirs();
		File output = new File(actionModelsDir, "compute-graph.xmi");
			
		EcoreActionGenerator actionGenerator = EcoreActionGenerator.loadEcoreActionGenerator(
				ComputePackage.eINSTANCE, 
				context, 
				null, 
				null, 
				null, 
				diagnosticConsumer,
				progressMonitor); 
						
		actionGenerator.generateActionModel(diagnosticConsumer, output, progressMonitor);
				
		String rootActionResource = "actions.yml";
		URI rootActionURI = URI.createFileURI(new File(rootActionResource).getAbsolutePath());//.appendFragment("/");
		
		String siteMapDomain = "https://compute.graph.demos.nasdanika.org";		
		ActionSiteGenerator actionSiteGenerator = new ActionSiteGenerator() {
			
			protected boolean isDeleteOutputPath(String path) {
				return !"CNAME".equals(path) && !path.startsWith("demo/");				
			};
			
		};		
		
		Map<String, Collection<String>> errors = actionSiteGenerator.generate(
				rootActionURI, 
				Theme.Cerulean.pageTemplateCdnURI, 
				siteMapDomain, 
				new File("../docs"), 
				new File("target/doc-site-work-dir"), 
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
		
		if (errorCount != 167) {
			throw new ExecutionException("There are problems with pages: " + errorCount);
		}		
	}
	
}
