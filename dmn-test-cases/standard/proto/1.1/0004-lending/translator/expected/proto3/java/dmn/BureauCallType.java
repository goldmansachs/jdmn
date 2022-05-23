
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "BureauCallType"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "BureauCallType",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class BureauCallType extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "BureauCallType",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.BureauCallTypeRequest bureauCallTypeRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(bureauCallTypeRequest_.getApplicantData());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        return map_;
    }

    public static String responseToOutput(proto.BureauCallTypeResponse bureauCallTypeResponse_) {
        // Extract and convert output
        return bureauCallTypeResponse_.getBureauCallType();
    }

    private final PreBureauRiskCategory preBureauRiskCategory;

    public BureauCallType() {
        this(new PreBureauRiskCategory());
    }

    public BureauCallType(PreBureauRiskCategory preBureauRiskCategory) {
        this.preBureauRiskCategory = preBureauRiskCategory;
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("ApplicantData"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'BureauCallType'", e);
            return null;
        }
    }

    public String apply(String applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BureauCallType'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'BureauCallType'
            long bureauCallTypeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bureauCallTypeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bureauCallTypeArguments_.put("ApplicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeArguments_);

            // Evaluate decision 'BureauCallType'
            String output_ = lambda.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'BureauCallType'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeArguments_, output_, (System.currentTimeMillis() - bureauCallTypeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'BureauCallType' evaluation", e);
            return null;
        }
    }

    public proto.BureauCallTypeResponse apply(proto.BureauCallTypeRequest bureauCallTypeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(bureauCallTypeRequest_.getApplicantData());

        // Invoke apply method
        String output_ = apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.BureauCallTypeResponse.Builder builder_ = proto.BureauCallTypeResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setBureauCallType(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply child decisions
                String preBureauRiskCategory = BureauCallType.this.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

                return BureauCallTypeTable.instance().apply(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
