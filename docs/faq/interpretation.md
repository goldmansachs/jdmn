# Interpretation

## How to interpret a DMN model?

Follow the steps:
1. Instantiate the chosen jDMN dialect (see [FAQs](index.md) for a list of all supported dialects.)
2. Create the interpreter
3. Evaluate the decision

```
    DMNDialectDefinition dialect = new StandardDMNDialectDefinition();
    DMNInterpreter interpreter = dialect.createDMNInterpreter(definitions);
    String decisionName = ...;
    RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
    Object result = interpreter.evaluate(decisionName, runtimeEnvironment);
```