# Interpretation

## How to interpret a DMN model?

Follow the steps:
1. Instantiate the chosen jDMN dialect (see [FAQs](index.md) for a list of all supported dialects.)
2. Create the interpreter
3. Evaluate the decision

```
    // Read DMN file
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(...));
    DMNReader reader = new DMNReader(LOGGER, false);
    TDefinitions definitions = reader.read(dmnFileURL);

    // Create interpreter
    DMNDialectDefinition dialect = new StandardDMNDialectDefinition();
    DMNInterpreter interpreter = dialect.createDMNInterpreter(definitions);
    
    // Bind inputs to values
    RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
    T inputValue = ...; // where T is determined by the FEEL data type and the dialect  
    String inputName = ...;
    runtimeEnvironment.bind(inputName, inputValue);

    // Evaluate decision
    String decisionName = ...;
    Object result = interpreter.evaluate(decisionName, runtimeEnvironment);
```