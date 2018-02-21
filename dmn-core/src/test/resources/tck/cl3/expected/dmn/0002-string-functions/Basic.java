
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Basic"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Basic",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Basic extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Basic",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Basic() {
    }

    public type.TBasic apply(String a, String b, String numC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(a, b, (numC != null ? number(numC) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Basic'", e);
            return null;
        }
    }

    public type.TBasic apply(String a, String b, String numC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply(a, b, (numC != null ? number(numC) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Basic'", e);
            return null;
        }
    }

    public type.TBasic apply(String a, String b, java.math.BigDecimal numC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, numC, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TBasic apply(String a, String b, java.math.BigDecimal numC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("a", a);
            arguments_.put("b", b);
            arguments_.put("numC", numC);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Evaluate expression
            type.TBasic output_ = evaluate(a, b, numC, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Basic' evaluation", e);
            return null;
        }
    }

    private type.TBasic evaluate(String a, String b, java.math.BigDecimal numC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        Boolean startsWithX = startsWith(a, "x");
        Boolean startsWithB = startsWith(a, b);
        Boolean endsWithX = endsWith(a, "x");
        Boolean endsWithB = endsWith(a, b);
        Boolean containsX = contains(a, "x");
        Boolean containsB = contains(a, b);
        String substringC1 = substring(a, numC, number("1"));
        java.math.BigDecimal stringlength = stringLength(a);
        String uppercase = upperCase(a);
        String lowercase = lowerCase(b);
        String substringBeforeB = substringBefore(a, b);
        String substringAfterB = substringAfter(a, b);
        type.TBasicImpl basic = new type.TBasicImpl();
        basic.setStartsWithX(startsWithX);
        basic.setStartsWithB(startsWithB);
        basic.setEndsWithX(endsWithX);
        basic.setEndsWithB(endsWithB);
        basic.setContainsX(containsX);
        basic.setContainsB(containsB);
        basic.setSubstringC1(substringC1);
        basic.setStringlength(stringlength);
        basic.setUppercase(uppercase);
        basic.setLowercase(lowercase);
        basic.setSubstringBeforeB(substringBeforeB);
        basic.setSubstringAfterB(substringAfterB);
        return basic;
    }
}
