
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "fetchForexRate"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/1b49e2cbacaf470fb5d093be73afd27e.xml",
    name = "fetchForexRate",
    label = "FetchForexRate",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FetchForexRate extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fetchForexRate",
        "FetchForexRate",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final IsForexRateRequired isForexRateRequired;

    public FetchForexRate() {
        this(new IsForexRateRequired());
    }

    public FetchForexRate(IsForexRateRequired isForexRateRequired) {
        this.isForexRateRequired = isForexRateRequired;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("DerivativeType"), input_.get("TaxChargeType"), (input_.get("Transaction") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Transaction"), new com.fasterxml.jackson.core.type.TypeReference<type.TransactionImpl>() {}) : null), (input_.get("TransactionTaxMetaData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TransactionTaxMetaData"), new com.fasterxml.jackson.core.type.TypeReference<type.TransactionTaxMetaDataImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FetchForexRate'", e);
            return null;
        }
    }

    public String apply(String derivativeType, String taxChargeType, type.Transaction transaction, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'fetchForexRate'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fetchForexRateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fetchForexRateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fetchForexRateArguments_.put("DerivativeType", derivativeType);
            fetchForexRateArguments_.put("TaxChargeType", taxChargeType);
            fetchForexRateArguments_.put("Transaction", transaction);
            fetchForexRateArguments_.put("TransactionTaxMetaData", transactionTaxMetaData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fetchForexRateArguments_);

            // Evaluate decision 'fetchForexRate'
            String output_ = evaluate(derivativeType, taxChargeType, transaction, transactionTaxMetaData, context_);

            // End decision 'fetchForexRate'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fetchForexRateArguments_, output_, (System.currentTimeMillis() - fetchForexRateStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fetchForexRate' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String derivativeType, String taxChargeType, type.Transaction transaction, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        Boolean isForexRateRequired = this.isForexRateRequired.apply(derivativeType, taxChargeType, transactionTaxMetaData, context_);

        return ((com.gs.dmn.runtime.external.JavaExternalFunction<String>)((com.gs.dmn.runtime.Context)new com.gs.dmn.runtime.Context().add("f", new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("com.gs.dmn.generated.external_functions.ForexRateService", "fetchECBForexRateFor", Arrays.asList("com.gs.dmn.generated.external_functions.type.Transaction", "java.lang.Boolean")), externalExecutor_, String.class))).get("f")).apply(transaction, isForexRateRequired);
    }
}
