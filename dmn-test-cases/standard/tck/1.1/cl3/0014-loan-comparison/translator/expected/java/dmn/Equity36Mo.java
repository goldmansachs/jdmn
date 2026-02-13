
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "equity36Mo"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_56c7d4a5-e6db-4bba-ac5f-dc082a16f719",
    name = "equity36Mo",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Equity36Mo extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.trisotech.com/definitions/_56c7d4a5-e6db-4bba-ac5f-dc082a16f719",
        "equity36Mo",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Equity36MoLazyHolder {
        static final Equity36Mo INSTANCE = new Equity36Mo();
    }
    public static Equity36Mo instance() {
        return Equity36MoLazyHolder.INSTANCE;
    }

    private Equity36Mo() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("p") != null ? number(input_.get("p")) : null), (input_.get("r") != null ? number(input_.get("r")) : null), (input_.get("n") != null ? number(input_.get("n")) : null), (input_.get("pmt") != null ? number(input_.get("pmt")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Equity36Mo'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Equity36MoInput_)input_).getP(), ((Equity36MoInput_)input_).getR(), ((Equity36MoInput_)input_).getN(), ((Equity36MoInput_)input_).getPmt(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Equity36Mo'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Equity36MoInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Equity36Mo'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number p, java.lang.Number r, java.lang.Number n, java.lang.Number pmt, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'equity36Mo'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long equity36MoStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments equity36MoArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            equity36MoArguments_.put("p", p);
            equity36MoArguments_.put("r", r);
            equity36MoArguments_.put("n", n);
            equity36MoArguments_.put("pmt", pmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_);

            // Evaluate BKM 'equity36Mo'
            java.lang.Number output_ = lambda.apply(p, r, n, pmt, context_);

            // End BKM 'equity36Mo'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_, output_, (System.currentTimeMillis() - equity36MoStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'equity36Mo' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number p = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number r = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                java.lang.Number n = 2 < args_.length ? (java.lang.Number) args_[2] : null;
                java.lang.Number pmt = 3 < args_.length ? (java.lang.Number) args_[3] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 4 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[4] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericSubtract(numericMultiply(p, numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n)), numericDivide(numericMultiply(pmt, numericAdd(numericUnaryMinus(number("1")), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n))), r));
            }
        };
}
