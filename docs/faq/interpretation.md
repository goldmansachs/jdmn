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
    DMNReader reader = new DMNReader(LOGGER, true);
    Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(input);

    // Create interpreter
    MixedJavaTimeDMNDialectDefinition dialect = new MixedJavaTimeDMNDialectDefinition();
    DMNInterpreter<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> interpreter = dialect.createDMNInterpreter(new DMNModelRepository(pair), inputParameters);

    // Evaluate decision
    String namespace = "http://www.provider.com/model-id";
    String decisionName = "Decision A";
    Map<String, Object> inputs = new LinkedHashMap<>();
    inputs.put("income", BigDecimal.valueOf(10000));
    Object result = interpreter.evaluateDecision(namespace, decisionName, inputs);
```
