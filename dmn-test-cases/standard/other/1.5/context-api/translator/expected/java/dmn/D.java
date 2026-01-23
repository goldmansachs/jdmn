
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "D"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "D",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class D extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<type.Country>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "D",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public D() {
    }

    @java.lang.Override()
    public List<type.Country> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Date") != null ? date(input_.get("Date")) : null), (input_.get("Person") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Person"), new com.fasterxml.jackson.core.type.TypeReference<type.PersonImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'D'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<type.Country> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((DInput_)input_).getDate(), ((DInput_)input_).getPerson(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'D'", e);
            return null;
        }
    }

    public List<type.Country> apply(java.time.LocalDate date, type.Person person, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'D'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dArguments_.put("Date", date);
            dArguments_.put("Person", person);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dArguments_);

            // Evaluate decision 'D'
            List<type.Country> output_ = lambda.apply(date, person, context_);

            // End decision 'D'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dArguments_, output_, (System.currentTimeMillis() - dStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'D' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<type.Country>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<type.Country>>() {
            public List<type.Country> apply(Object... args_) {
                java.time.LocalDate date = 0 < args_.length ? (java.time.LocalDate) args_[0] : null;
                type.Person person = 1 < args_.length ? (type.Person) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return ((List<type.Address>)(person != null ? person.getAddresses() : null)).stream().map(x -> ((type.Country)(x != null ? x.getCountry() : null))).collect(Collectors.toList());
            }
        };
}
