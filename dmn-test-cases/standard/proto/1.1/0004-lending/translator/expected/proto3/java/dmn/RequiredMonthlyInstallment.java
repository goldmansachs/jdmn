
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "RequiredMonthlyInstallment"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RequiredMonthlyInstallment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class RequiredMonthlyInstallment extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "RequiredMonthlyInstallment",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.RequiredMonthlyInstallmentRequest requiredMonthlyInstallmentRequest_) {
        // Create arguments from Request Message
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(requiredMonthlyInstallmentRequest_.getRequestedProduct());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("RequestedProduct", requestedProduct);
        return map_;
    }

    public static java.math.BigDecimal responseToOutput(proto.RequiredMonthlyInstallmentResponse requiredMonthlyInstallmentResponse_) {
        // Extract and convert output
        return java.math.BigDecimal.valueOf(requiredMonthlyInstallmentResponse_.getRequiredMonthlyInstallment());
    }

    public RequiredMonthlyInstallment() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("RequestedProduct") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("RequestedProduct"), new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'RequiredMonthlyInstallment'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long requiredMonthlyInstallmentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments requiredMonthlyInstallmentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            requiredMonthlyInstallmentArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_);

            if (cache_.contains("RequiredMonthlyInstallment")) {
                // Retrieve value from cache
                java.math.BigDecimal output_ = (java.math.BigDecimal)cache_.lookup("RequiredMonthlyInstallment");

                // End decision 'RequiredMonthlyInstallment'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_));

                return output_;
            } else {
                // Evaluate decision 'RequiredMonthlyInstallment'
                java.math.BigDecimal output_ = lambda.apply(requestedProduct, context_);
                cache_.bind("RequiredMonthlyInstallment", output_);

                // End decision 'RequiredMonthlyInstallment'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'RequiredMonthlyInstallment' evaluation", e);
            return null;
        }
    }

    public proto.RequiredMonthlyInstallmentResponse applyProto(proto.RequiredMonthlyInstallmentRequest requiredMonthlyInstallmentRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(requiredMonthlyInstallmentRequest_.getRequestedProduct());

        // Invoke apply method
        java.math.BigDecimal output_ = apply(requestedProduct, context_);

        // Convert output to Response Message
        proto.RequiredMonthlyInstallmentResponse.Builder builder_ = proto.RequiredMonthlyInstallmentResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setRequiredMonthlyInstallment(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                type.TRequestedProduct requestedProduct = 0 < args_.length ? (type.TRequestedProduct) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return InstallmentCalculation.instance().apply(((String)(requestedProduct != null ? requestedProduct.getProductType() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getAmount() : null)), context_);
            }
        };
}
