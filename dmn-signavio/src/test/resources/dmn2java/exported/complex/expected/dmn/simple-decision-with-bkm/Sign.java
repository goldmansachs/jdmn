
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision-with-extension.ftl", "sign"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sign",
    label = "Sign",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sign extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sign",
        "Sign",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sign() {
    }

    public java.math.BigDecimal apply(String a2, String b3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a2 != null ? number(a2) : null), (b3 != null ? number(b3) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Sign'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String a2, String b3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((a2 != null ? number(a2) : null), (b3 != null ? number(b3) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sign'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a2, java.math.BigDecimal b3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a2, b3, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a2, java.math.BigDecimal b3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'sign'
            long signStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments signArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            signArguments_.put("a2", a2);
            signArguments_.put("b3", b3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, signArguments_);

            // Evaluate decision 'sign'
            java.math.BigDecimal output_ = evaluate(a2, b3, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'sign'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, signArguments_, output_, (System.currentTimeMillis() - signStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sign' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal a2, java.math.BigDecimal b3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return BKM.bKM(b3, a2, annotationSet_, eventListener_, externalExecutor_);
    }
}
