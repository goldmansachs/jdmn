
import java.util.Map;

/**
 * Handler for requests to Lambda function for element ''Extra days case 1'' in model '0020-vacation-days'.
 */
public class ExtraDaysCase1Lambda implements com.amazonaws.services.lambda.runtime.RequestHandler<Map<String, String>, Object> {
    private final ExtraDaysCase1 extraDaysCase1 = new ExtraDaysCase1();

    public Object handleRequest(Map<String, String> event_, com.amazonaws.services.lambda.runtime.Context context_) {
        // Parameters
        // ----------
        // event_: Map<String, String>, required
        //     Input event to the Lambda function

        // context_: Context, required
        //     Lambda Context runtime methods and attributes

        // Returns
        // ------
        //     Map<String, String>: Object containing details of the event

        String age = event_.get("Age");
        String yearsOfService = event_.get("'Years of Service'");
        boolean trace_ = Boolean.parseBoolean(event_.get("_trace"));

        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = trace_ ? new com.gs.dmn.runtime.listener.TreeTraceEventListener() : new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        java.math.BigDecimal output_ = extraDaysCase1.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_);

        java.util.Map<String, Object> result_ = new java.util.LinkedHashMap<>();
        result_.put("'Extra days case 1'", output_);
        if (trace_) {
            result_.put("_explain", ((com.gs.dmn.runtime.listener.TreeTraceEventListener) eventListener_).getRoot());
        }

        return result_;
    }
}
