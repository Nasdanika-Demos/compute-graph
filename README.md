# Compute Graph

Demo for the [Executable (computational) graphs & diagrams](https://medium.com/nasdanika/executable-computational-graphs-diagrams-1eeffc80976d) medium article.

This demo shows two approaches to creating executable software starting from arbitrary [Drawio](https://www.drawio.com/) diagrams which may not follow any notation.
The software traces back to the diagram(s) and modifications in the diagrams change how software executes.

The demo uses For this demo a ``parse-tree.drawio`` diagram is located in the [compute](compute) module/folder.

The demonstrated approaches may be applied when there is a body of pre-existing diagrams, or when diagram authors (subject matter experts) are not proficient with diagramming notations.
In general, a diagram created by a child might be used to create executable programs.

## Direct mapping

With this approach processors are created for [Drawio diagram](https://docs.nasdanika.org/core/drawio/index.html) elements, which are [graph](https://docs.nasdanika.org/core/graph/index.html) elements.

* [ComputeGraphTests](compute/src/test/java/org/nasdanika/demos/graph/compute/tests/ComputeGraphTests.java) ``testDrawioSyncComputeGraph`` shows how to create graph processors and perform computations using [ReflectiveProcessorFactoryProvider](https://javadoc.io/doc/org.nasdanika.core/graph/latest/org.nasdanika.graph/org/nasdanika/graph/processor/ReflectiveProcessorFactoryProvider.html)
* ``testDrawioCapabilitySyncComputeGraph`` does the same, but using [CapabilityProcessorFactory](https://javadoc.io/doc/org.nasdanika.core/graph/latest/org.nasdanika.graph/org/nasdanika/graph/processor/CapabilityProcessorFactory.html)
* Processors and factories can be found in [org.nasdanika.demos.graph.compute.computers.diagram.sync](compute/src/main/java/org/nasdanika/demos/graph/compute/computers/diagram/sync) package

## Mapping to a a model

This approach uses mapping of a diagram to a semantic model and then providing executability for the semantic model. 

### Elicit a domain model (metamodel)

You may use an existing model, e.g. one of [Nasdanika](https://docs.nasdanika.org/index.html) models, or create a new model.
A new model may extend existing models.

In this demo the metamodel is located in the [model](model) module.

### Document the metamodel

Create metamodel documentation processors. 
Metamodel documentation processors can be found in the [processors/src/main/java/org/nasdanika/demos/graph/compute/processors/ecore](processors/src/main/java/org/nasdanika/demos/graph/compute/processors/ecore) package. 
They are registered as services in the processor's module [module-info.java](processors/src/main/java/module-info.java).

Generate [metamodel documentation](https://nasdanika-demos.github.io/compute-graph/)[^not_complete].
In this demo documentation is generated with a JUnit test - [TestComputeModelDocGen.java](generator/src/test/java/org/nasdanika/demos/graph/compute/generator/tests/TestComputeModelDocGen.java).

[^not_complete]: Currently the metamodel documentation is incomplete - it was copied from the [familiy metamodel](https://family.models.nasdanika.org/).

### Map the diagram to the model

Use diagram element properties to map them to metamodel classes to create a model. 
The mapping process is explained in the [Beyond Diagrams](https://leanpub.com/beyond-diagrams) book.

### Create documentation processors

You may create documentation processors and generate documentation from the loaded model. 
In this demo documentation processors are located in [processors/src/main/java/org/nasdanika/demos/graph/compute/processors/doc](processors/src/main/java/org/nasdanika/demos/graph/compute/processors/doc) package.
The are incomplete. 

You may find complete functional processors in https://github.com/Nasdanika-Models/family/tree/main/processors/src/main/java/org/nasdanika/models/family/processors/doc. 

* [An example of a web site generated using processors](https://family.models.nasdanika.org/demos/mapping/)
* [Generator](https://github.com/Nasdanika-Models/family/blob/main/demos/mapping/src/test/java/org/nasdanika/models/family/demos/mapping/tests/TestFamilyMappingSiteGen.java)

With documentation generation you may associate detailed information with diagram elements.

### Create compute processors

Follow [examples](compute/src/main/java/org/nasdanika/demos/graph/compute/computers/model/sync) and [reference documentation](https://docs.nasdanika.org/core/graph/index.html#processors-and-processor-factories) to create compute processors.

To perform computations follow ``testModelSyncComputeGraph`` method in [ComputeGraphTests](compute/src/test/java/org/nasdanika/demos/graph/compute/tests/ComputeGraphTests.java).

Currently in this demo there is one set of model processors to perform computations synchronously. 
You may have multiple flavors of processors. 
For example:

* Synchronous computations
* Asynchronous computations (CompletionStage)
* [Reactive](https://projectreactor.io/) computations
* Code generation - text or, say, [Java model](https://java.models.nasdanika.org/)

Please note that you don't need to create documentation processors and generate documentation in order to create compute processors.
