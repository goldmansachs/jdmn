
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "calculateDiscountedPrice"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "calculateDiscountedPrice",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CalculateDiscountedPrice extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "calculateDiscountedPrice",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final AgeClassification ageClassification;

    public CalculateDiscountedPrice() {
        this(new AgeClassification());
    }

    public CalculateDiscountedPrice(AgeClassification ageClassification) {
        this.ageClassification = ageClassification;
    }

    public java.math.BigDecimal apply(String price, String student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((price != null ? number(price) : null), (student != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(student, new com.fasterxml.jackson.core.type.TypeReference<type.StudentImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CalculateDiscountedPrice'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String price, String student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((price != null ? number(price) : null), (student != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(student, new com.fasterxml.jackson.core.type.TypeReference<type.StudentImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CalculateDiscountedPrice'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal price, type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(price, student, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal price, type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'calculateDiscountedPrice'
            long calculateDiscountedPriceStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments calculateDiscountedPriceArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            calculateDiscountedPriceArguments_.put("price", price);
            calculateDiscountedPriceArguments_.put("student", student);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, calculateDiscountedPriceArguments_);

            // Evaluate decision 'calculateDiscountedPrice'
            java.math.BigDecimal output_ = lambda.apply(price, student, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'calculateDiscountedPrice'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, calculateDiscountedPriceArguments_, output_, (System.currentTimeMillis() - calculateDiscountedPriceStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'calculateDiscountedPrice' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal price = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                type.Student student = 1 < args_.length ? (type.Student) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                com.gs.dmn.runtime.Context ageClassification = CalculateDiscountedPrice.this.ageClassification.apply(student, annotationSet_, eventListener_, externalExecutor_, cache_);

                return numericMultiply(price, ((java.math.BigDecimal)((com.gs.dmn.runtime.Context)ageClassification).get("discount")));
            }
        };
}
