
import java.util.*;
import java.util.stream.Collectors;

import static InstallmentCalculation.InstallmentCalculation;

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

    public RequiredMonthlyInstallment() {
    }

    public java.math.BigDecimal apply(String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'RequiredMonthlyInstallment'
            long requiredMonthlyInstallmentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments requiredMonthlyInstallmentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            requiredMonthlyInstallmentArguments_.put("requestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_);

            // Evaluate decision 'RequiredMonthlyInstallment'
            java.math.BigDecimal output_ = evaluate(requestedProduct, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'RequiredMonthlyInstallment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RequiredMonthlyInstallment' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return InstallmentCalculation(((String)(requestedProduct != null ? requestedProduct.getProductType() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getRate() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getTerm() : null)), ((java.math.BigDecimal)(requestedProduct != null ? requestedProduct.getAmount() : null)), annotationSet_, eventListener_, externalExecutor_);
    }
}
