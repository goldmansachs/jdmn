
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "listGen9"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "listGen9",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ListGen9 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "listGen9",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final ListGen7 listGen7;

    public ListGen9() {
        this(new ListGen7());
    }

    public ListGen9(ListGen7 listGen7) {
        this.listGen7 = listGen7;
    }

    public List<String> apply(String a, String b, String wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(a, b, (wx != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(wx, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ListGen9'", e);
            return null;
        }
    }

    public List<String> apply(String a, String b, String wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply(a, b, (wx != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(wx, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ListGen9'", e);
            return null;
        }
    }

    public List<String> apply(String a, String b, List<String> wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, wx, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(String a, String b, List<String> wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("a", a);
            arguments.put("b", b);
            arguments.put("wx", wx);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<String> listGen7Output = listGen7.apply(wx, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            List<String> output_ = evaluate(a, b, listGen7Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'listGen9' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(String a, String b, List<String> listGen7, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return flatten(asList(a, b, listGen7));
    }
}
