
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "varargs_001"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "varargs_001",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Varargs_001 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "varargs_001",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Varargs_001() {
    }

    public Object apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Object apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'varargs_001'
            long varargs_001StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments varargs_001Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, varargs_001Arguments_);

            // Evaluate decision 'varargs_001'
            Object output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'varargs_001'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, varargs_001Arguments_, output_, (System.currentTimeMillis() - varargs_001StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'varargs_001' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        com.gs.dmn.runtime.external.JavaExternalFunction<Object> format = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("java.lang.String", "format", Arrays.asList("java.lang.String", "[Ljava.lang.Object;")), externalExecutor_, Object.class);
        return format.apply("foo %s", "bar");
    }
}
