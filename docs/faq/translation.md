# Translation

## How to generate Java code from DMN?
1. Edit the diagram using a DMN editor 
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
3. Add the dmn-maven-plugin in the plugins section configured as follow

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
                    <goal>tck-to-jav</goal>
                </goals>
                <configuration>
                    <inputTestFileDirectory>${tck.folder}/DIAGRAM_NAME.json</inputTestFileDirectory>
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
Both DMN2Java and TCK2Junit mojos require configuration. Some parameters are specific to a goal, others are common.

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
dmnDialect              | DMN dialect used to describe the decision model. | Mandatory. Default value is com.gs.dmn.dialect.StandardDMNDialectDefinition
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

More examples in the pom of the ```dmn-tck-integration-tests``` module.

## How to evaluate a decision using the generated code?
1. Create an instance of the decision.
2. Create instances of the required input data.
3. Invoke one of the apply methods
For example:

```java
    GenerateOutputData decision = new GenerateOutputData();
    AnnotationSet annotationSet = new AnnotationSet();
    Applicant applicant = new Applicant();
    applicant.setName("Amy");
    applicant.setAge(decision.number("38"));
    applicant.setCreditScore(decision.number("100"));
    applicant.setPriorIssues(Arrays.asList("Late payment"));
    
    java.math.BigDecimal currentRiskAppetite = decision.number("50");
    java.math.BigDecimal lendingThreshold = decision.number("25");
    
    List<Object> result = decision.apply(annotationSet, applicant, currentRiskAppetite, lendingThreshold);
```
