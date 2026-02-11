
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_018"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0092-feel-lambda",
    name = "bkm_018",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_018 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_018",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_018LazyHolder {
        static final Bkm_018 INSTANCE = new Bkm_018();
    }
    public static Bkm_018 instance() {
        return Bkm_018LazyHolder.INSTANCE;
    }

    private Bkm_018() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("p1"), input_.get("p2"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Bkm_018'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Bkm_018Input_)input_).getP1(), ((Bkm_018Input_)input_).getP2(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Bkm_018'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Bkm_018Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Bkm_018'", e);
            return null;
        }
    }

    public Boolean apply(String p1, String p2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_018'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_018StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_018Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_018Arguments_.put("p1", p1);
            bkm_018Arguments_.put("p2", p2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_018Arguments_);

            // Evaluate BKM 'bkm_018'
            Boolean output_ = lambda.apply(p1, p2, context_);

            // End BKM 'bkm_018'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_018Arguments_, output_, (System.currentTimeMillis() - bkm_018StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_018' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                String p1 = 0 < args_.length ? (String) args_[0] : null;
                String p2 = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return (booleanEqual(stringEqual(p1, "a"), Boolean.TRUE)) ? Boolean.TRUE : Boolean.FALSE;
            }
        };
}
