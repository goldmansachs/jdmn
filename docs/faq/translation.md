# Translation

## How to generate Java code from DMN?

1. Create a DMN model using a DMN editor 
2. Create a Maven project
3. Add the dmn-maven-plugin in the plugins section configured as follows:

```xml
    <plugin>
        <groupId>com.gs.dmn</groupId>
        <artifactId>dmn-maven-plugin</artifactId>
        <version>VERSION</version>
        <executions>
            <execution>
                <id>DIAGRAM_NAME</id>
                <phase>generate-sources</phase>
                <goals>
                    <goal>dmn-to-java</goal>
                </goals>
                <configuration>
                    <inputFileDirectory>${diagram.folder}/DIAGRAM_NAME.dmn</inputFileDirectory>
                    <outputFileDirectory>${project.build.directory}/generated-sources/dmn</outputFileDirectory>
                    <inputParameters>
                        <javaRootPackage>com.gs.YOUR_PACKAGE</javaRootPackage>
                    </inputParameters>
                </configuration>
            </execution>
     </executions>
    </plugin>
```

## How to generate JUnit tests from TCK tests?

1. Create a Maven project
2. Write the tests in TCK format
3. Add the ```dmn-maven-plugin``` in the plugins section configured as follows

```xml
    <plugin>
        <groupId>com.gs.dmn</groupId>
        <artifactId>dmn-maven-plugin</artifactId>
        <version>VERSION</version>
        <executions>
             <execution>
                <id>test-DIAGRAM_NAME</id>
                <phase>generate-test-sources</phase>
                <goals>
                    <goal>tck-to-java</goal>
                </goals>
                <configuration>
                    <inputTestFileDirectory>${tck.folder}/DIAGRAM_NAME.xml</inputTestFileDirectory>
                    <inputModelFileDirectory>${diagram.folder}/DIAGRAM_NAME.dmn</inputModelFileDirectory>
                    <outputFileDirectory>${project.build.directory}/generated-test-sources/tck</outputFileDirectory>
                    <inputParameters>
                          <javaRootPackage>com.gs.YOUR_PACKAGE</javaRootPackage>
                    </inputParameters>
                </configuration>
            </execution>
        </executions>
    </plugin>
```

## How to configure the Maven plugin?

Both ```DMNToJava``` and ```TCKToJavaJUnit``` mojos require configuration. Some parameters are specific to a goal, others are common.

The specific parameters are inside the ```<configuration>``` Maven tag.

The common parameters are the ```<inputParameters>``` tag. The ```<inputParameters>``` tag is inside the ```<configuration>``` Maven tag.

For dmn-to-java goal only:

Parameter Name | Description | Comments
---------------|-------------|----------
inputFileDirectory | Folder where the DMN file are. | Mandatory. Default value ${project.basedir}/src/main/resources/dmn
outputFileDirectory | Folder to contain the generated code. | Mandatory. Default value ${project.build.directory}/generated-sources/dmn

For tck-to-java goal only:

Parameter Name	        |   Description	                                   | Comments
------------------------|--------------------------------------------------|-----------------------------------------------------------------------------------
inputTestFileDirectory  | Folder where the TCK (xml) files are.            | Mandatory. Default value ${project.basedir}/src/main/resources/tck
inputModelFileDirectory | Folder where the DMN files are.                  | Mandatory. Default value ${project.basedir}/src/main/resources/tck
outputFileDirectory     | Folder that contains the generated code.         | Mandatory. Default value ${project.build.directory}/generated-test-resources/junit

Common parameters:

Parameter Name	        |   Description	                                   | Comments
------------------------|--------------------------------------------------|-----------------------------------------------------------------------------------
dmnVersion              | Version of the DMN standard, currently 1.1	   | Required
modelVersion            | Version of the decision model, controlled by the user    | Required
platformVersion         | Version of the DMN Engine used to process the decision model. | Required
xsdValidation           | Forces the DMN reader to perform XSD validation. | Optional. Default value false
javaRootPackage         | Package that contains the generated code.	       | Optional. Default value is empty string
onePackage              | Flag to control the target package for multiple DMN files. | Default value false
caching                 | Flag to control the support for caching.         | Default value false
cachingThreshold        | Number of distinct usages of the same decision that trigger caching. | Default value 1
singletonDecision       | Flag to control the generation of Singleton pattern. | Default value false
parallelStream          | Flag to control the generation of parallel streams when processing lists. | Default value false
protoVersion            | Version of the Protocol Buffer used. | Default value "proto3"
generateProtoMessages   | Flag to control the generation of Proto Messages. | Default value false
generateProtoServices   | Flag to control the generation of Proto Services. | Default value false
dmnDialect              | DMN dialect used to describe the decision model. | Mandatory. Default value is com.gs.dmn.dialect.JavaTimeDMNDialectDefinition
dmnValidators           | DMN validators to be used to validate the model. | Optional. Default value com.gs.dmn.validation.NopDMNValidator
dmnTransformers         | DMN transformers to be applied before translation. | Optional. Default value com.gs.dmn.transformation.NopDMNTransformer. 
templateProvider        | The template provider to be used.                  | Mandatory. Default value is com.gs.dmn.transformation.template.TreeTemplateProvider
lazyEvaluationDetectors | Lazy evaluation detectors to be used during translation | Optional


Sample:
```xml
    <plugin>
        <groupId>com.gs.dmn</groupId>
        <artifactId>dmn-maven-plugin</artifactId>
        <version>${platform.version}</version>
        <configuration>
            <dmnDialect>com.gs.dmn.dialect.MixedJavaTimeDMNDialectDefinition</dmnDialect>
            <templateProvider>com.gs.dmn.transformation.template.TreeTemplateProvider</templateProvider>
            <inputParameters>
                <dmnVersion>${dmn.version}</dmnVersion>
                <modelVersion>${model.version}</modelVersion>
                <platformVersion>${platform.version}</platformVersion>
                <xsdValidation>false</xsdValidation>
            </inputParameters>
        </configuration>
        <executions>
            . . .
        </executions>
    </plugin>
```

More examples in the pom of the [dmn-tck-integration-tests](https://github.com/goldmansachs/jdmn/blob/master/dmn-tck-it/dmn-tck-it-translator/pom.xml) module.

## How to evaluate a decision using the generated code?

1. Create an instance of the decision.
2. Create instances of the required input data.
3. Invoke one of the apply methods.

For example:

```
    // Initialize input data
    com.gs.dmn.generated.other.decision_table_with_annotations.type.TA structA = new com.gs.dmn.generated.other.decision_table_with_annotations.type.TAImpl("A", number("5"));

    // Evaluate decision 'priceGt10'
    com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
    com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
    new PriceGt10().apply(structA, context_);
```

## How to optimize the translator?

The following optimizations are supported:
### Singleton pattern

Singleton pattern can be used to reduce the memory footprint of a decision tree. The steps to follow are:
- Set ```singletonDecision``` input parameter to true
```
          . . .
          <inputParameters>
              <singletonDecision>true</singletonDecision>
              ...
          </inputParameters>
```
- Invoke ```Decision.instance()``` instead of using a default constructor when creating a decision.

### Decision caching

When the decision tree is a DAG it makes sense to use caching to reduce the number of times a decision is calculated. The following configuration is needed:
   - Set ```caching``` flag to true, and ```cachingThreshold``` to the number of re-usages that trigger caching, normally 2.

```
        . . .
        <inputParameters>
            <caching>true</caching>
            <cachingThreshold>2</cachingThreshold>
        </inputParameters>

```

### Lazy evaluation of sparse Decision Tables

In certain cases (e.g., sparse decision tables and hit policy is FIRST) it makes sense to evaluate child decisions on demand. The following steps are to be followed:  

Configure the lazy evaluation detectors and the threshold.

```
        . . .
        <configuration>
            <lazyEvaluationDetectors>
                <lazyEvaluatorDetector>com.gs.dmn.transformation.lazy.SparseDecisionDetector</lazyEvaluatorDetector>
            </lazyEvaluationDetectors>
            . . .
            <inputParameters>
                <sparsityThreshold>0.8</sparsityThreshold>
            </inputParameters>
        </configuration>

```

### Lazy evaluation of Input Data

In certain cases (e.g., input data is retrieved from a database and is used in a decision table with hit policy FIRST) it makes sense to retrieve child input data on demand. Module dmn-jpa-it contains an example for this optimization. 

### Usage of parallel streams.

In certain cases (e.g., iteration over lists) it makes sense to use parallel streams. The following configuration is needed:

```
        . . .
        <configuration>
            . . .
            <inputParameters>
                <parallelStream>true</parallelStream>
            </inputParameters>
        </configuration>

```

## How to generate support for gRPC?

The following configuration is needed:

```
        . . .
       <inputParameters>
           <generateProtoMessages>true</generateProtoMessages>
           <generateProtoServices>true</generateProtoServices>
           <protoVersion>proto3</protoVersion>
           . . .
       </inputParameters>
```

More examples in the [dmn-tck-integration-tests](https://github.com/goldmansachs/jdmn/blob/master/dmn-tck-it/dmn-tck-it-translator) module.
