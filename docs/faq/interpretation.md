# Interpretation

## How to interpret a DMN model?

Follow the steps:
1. Instantiate the chosen jDMN dialect (see [FAQs](index.md) for a list of all supported dialects.)
2. Create the interpreter
3. Evaluate the decision

```
    // Read DMN file
    BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger("logger"));
    File input = new File("model.dmn");
    DMNSerializer reader = new XMLDMNSerializer(LOGGER, false);
    TDefinitions definitions = reader.readModel(input);

    // Create interpreter
    MixedJavaTimeDMNDialectDefinition dialect = new MixedJavaTimeDMNDialectDefinition();
    DMNInterpreter<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> interpreter = dialect.createDMNInterpreter(new DMNModelRepository(pair), inputParameters);

    // Evaluate decision
    String namespace = "http://www.provider.com/model-id";
    String decisionName = "Decision A";
    TDecision decision = (TDecision) repository.findDRGElementByName(repository.getRootDefinitions(), decisionName);

    Map<String, Object> inputs = new LinkedHashMap<>();
    inputs.put("income", BigDecimal.valueOf(10000));
    interpreter.evaluateDecision(namespace, decisionName, EvaluationContext.makeDecisionEvaluationContext(decision, inputs));
```
