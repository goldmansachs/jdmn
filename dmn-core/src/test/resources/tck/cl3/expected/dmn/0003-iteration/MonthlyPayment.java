
import java.util.*;
import java.util.stream.Collectors;

import static PMT2.PMT2;

@javax.annotation.Generated(value = {"decision.ftl", "MonthlyPayment"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "MonthlyPayment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class MonthlyPayment extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "MonthlyPayment",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public MonthlyPayment() {
    }

    public List<java.math.BigDecimal> apply(String loans, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((loans != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loans, type.TLoanImpl[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'MonthlyPayment'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String loans, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((loans != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loans, type.TLoanImpl[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'MonthlyPayment'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<type.TLoan> loans, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(loans, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<java.math.BigDecimal> apply(List<type.TLoan> loans, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("loans", loans);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            List<java.math.BigDecimal> output_ = evaluate(loans, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'MonthlyPayment' evaluation", e);
            return null;
        }
    }

    private List<java.math.BigDecimal> evaluate(List<type.TLoan> loans, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return loans.stream().map(i -> PMT2(i, annotationSet_, eventListener_, externalExecutor_)).collect(Collectors.toList());
    }
}
