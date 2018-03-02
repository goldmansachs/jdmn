
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "equity36Mo"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "equity36Mo",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Equity36Mo extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "equity36Mo",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final Equity36Mo INSTANCE = new Equity36Mo();

    private Equity36Mo() {
    }

    public static java.math.BigDecimal equity36Mo(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, java.math.BigDecimal pmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(p, r, n, pmt, annotationSet_, eventListener_, externalExecutor_);
    }

    private java.math.BigDecimal apply(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, java.math.BigDecimal pmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'equity36Mo'
            long equity36MoStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments equity36MoArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            equity36MoArguments_.put("p", p);
            equity36MoArguments_.put("r", r);
            equity36MoArguments_.put("n", n);
            equity36MoArguments_.put("pmt", pmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_);

            // Evaluate BKM 'equity36Mo'
            java.math.BigDecimal output_ = evaluate(p, r, n, pmt, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'equity36Mo'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_, output_, (System.currentTimeMillis() - equity36MoStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'equity36Mo' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, java.math.BigDecimal pmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericSubtract(numericMultiply(p, numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n)), numericDivide(numericMultiply(pmt, numericAdd(numericUnaryMinus(number("1")), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n))), r));
    }
}
