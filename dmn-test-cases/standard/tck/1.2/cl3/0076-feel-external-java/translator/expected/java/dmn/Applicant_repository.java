
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "applicant_repository"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "applicant_repository",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Applicant_repository extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "applicant_repository",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Applicant_repository() {
    }

    public type.Applicant apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.Applicant apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'applicant_repository'
            long applicant_repositoryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments applicant_repositoryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicant_repositoryArguments_);

            // Evaluate decision 'applicant_repository'
            type.Applicant output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'applicant_repository'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicant_repositoryArguments_, output_, (System.currentTimeMillis() - applicant_repositoryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'applicant_repository' evaluation", e);
            return null;
        }
    }

    protected type.Applicant evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        com.gs.dmn.runtime.external.JavaExternalFunction<type.Applicant> lookup = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("com.gs.dmn.generated.tck.cl3_0076_feel_external_java.ApplicantRepository", "find", Arrays.asList("java.lang.String")), externalExecutor_, type.Applicant.class);
        return lookup.apply("john");
    }
}
