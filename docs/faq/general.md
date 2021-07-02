# General

## How to execute a DMN model?

DMN models can be executed in jDMN in two ways:
* interpretation on JVM
* translation to Java, followed by the execution of the generated code on JVM. 

For more information please look at the other [FAQs](index.md).


## How to read a DMN model?

DMN models can be read as follows:

```
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger("logger"));
    File input = new File("model.dmn");
    DMNReader reader = new DMNReader(LOGGER, false);
    TDefinitions definitions = reader.read(input).getLeft();
```

## How to validate a DMN model?

DMN models can be validated at the syntax level (XSD schema validation) as follows:

```
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger("logger"));
    File input = new File("model.dmn");
    DMNReader reader = new DMNReader(LOGGER, true);
    TDefinitions definitions = reader.read(input).getLeft();
```

DMN models can be validated at the semantic level by implementing the ```DMNValidator``` interface. jDMN provides a default implementation.

```
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger("logger"));
    File input = new File("model.dmn");
    DMNReader reader = new DMNReader(LOGGER, false);
    Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(input);

    DMNValidator validator = new DefaultDMNValidator();
    validator.validate(new DMNModelRepository(pair));
```

## How to transform a DMN model?

DMN models can be transformed by implementing the ```DMNTransformer``` interface. jDMN provides support for the Composite Design Pattern.

```
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger("logger"));
    File input = new File("model.dmn");
    DMNReader reader = new DMNReader(LOGGER, false);
    Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(input);

    DMNTransformer<TestCases> transformer = new ToQuotedNameTransformer(LOGGER);
    transformer.transform(new DMNModelRepository(pair));
```

## What is a jDMN dialect?

A dialect is a jDMN abstraction introduced to support variations both of both source language (e.g., dialect of DMN) and target language (e.g., Java or Kotlin). This is to support the variations such as: 
* FEEL library variations from one DMN provider to another
* Various mappings of the FEEL/DMN types (e.g., one dialect maps FEEL:number to BigDecimal, other maps FEEL:number to Double)

## How many dialects are supported?

The jDMN dialects are grouped in several categories, based on the supported DMN dialects:
- Standard Dialect
- Signavio Dialect

Each of these dialects has different implementation based on the mapping of the DMN / FEEL types to the target language. The dialects are implemented as Java generics. The recommended dialect is MixedJavaTimeDMNDialectDefinition. It's a bit faster than the others and more user friendly. 

More information in the hierarchy of DMNDialectDefinition interface.

## What is a template provider?

A template provider is a jDMN abstraction introduced to support variations of the layout of the generated Java code, depending on the layout of the decision model (e.g., tree structure). 

## How many template providers are supported?

The supported template providers are the following:
- TreeTemplateProvider

