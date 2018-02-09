
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Bankrates"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Bankrates",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bankrates extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Bankrates",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Bankrates() {
    }

    public List<type.TLoanProduct> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<type.TLoanProduct> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            List<type.TLoanProduct> output_ = evaluate(annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Bankrates' evaluation", e);
            return null;
        }
    }

    private List<type.TLoanProduct> evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return asList(new type.TLoanProductImpl(number("0"), "Oceans Capital", number("0"), number(".03500")),
				new type.TLoanProductImpl(number("2700"), "eClick Lending", number("1.1"), number(".03200")),
				new type.TLoanProductImpl(number("1200"), "eClickLending", number("0.1"), number(".03375")),
				new type.TLoanProductImpl(number("3966"), "AimLoan", number("1.1"), number(".03000")),
				new type.TLoanProductImpl(number("285"), "Home Loans Today", number("1.1"), number(".03125")),
				new type.TLoanProductImpl(number("4028"), "Sebonic", number("0.1"), number(".03125")),
				new type.TLoanProductImpl(number("4317"), "AimLoan", number("0.1"), number(".03125")),
				new type.TLoanProductImpl(number("2518"), "eRates Mortgage", number("1.1"), number(".03125")),
				new type.TLoanProductImpl(number("822"), "Home Loans Today", number("0.1"), number(".03250")),
				new type.TLoanProductImpl(number("1995"), "AimLoan", number("0"), number(".03250")));
    }
}
