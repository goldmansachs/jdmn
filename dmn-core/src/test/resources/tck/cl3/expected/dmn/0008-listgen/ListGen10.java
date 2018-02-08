
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "listGen10"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "listGen10",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ListGen10 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "listGen10",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final ListGen4 listGen4;
    private final ListGen7 listGen7;

    public ListGen10() {
        this(new ListGen4(), new ListGen7());
    }

    public ListGen10(ListGen4 listGen4, ListGen7 listGen7) {
        this.listGen4 = listGen4;
        this.listGen7 = listGen7;
    }

    public List<String> apply(String c, String wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(c, (wx != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(wx, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ListGen10'", e);
            return null;
        }
    }

    public List<String> apply(String c, String wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply(c, (wx != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(wx, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ListGen10'", e);
            return null;
        }
    }

    public List<String> apply(String c, List<String> wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(c, wx, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(String c, List<String> wx, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("c", c);
            arguments.put("wx", wx);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<String> listGen4Output = listGen4.apply(c, annotationSet_, eventListener_, externalExecutor_);
            List<String> listGen7Output = listGen7.apply(wx, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            List<String> output_ = evaluate(listGen4Output, listGen7Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'listGen10' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> listGen4, List<String> listGen7, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return flatten(asList(listGen4, listGen7));
    }
}
