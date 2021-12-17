
import java.util.Map;

/**
 * Handler for requests to Lambda function for element ''Base Vacation Days'' in model '0020-vacation-days'.
 */
public class BaseVacationDaysLambda implements com.amazonaws.services.lambda.runtime.RequestHandler<Map<String, String>, Object> {
    private final BaseVacationDays baseVacationDays = new BaseVacationDays();

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

        boolean trace_ = Boolean.parseBoolean(event_.get("_trace"));

        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = trace_ ? new com.gs.dmn.runtime.listener.TreeTraceEventListener() : new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        java.math.BigDecimal output_ = baseVacationDays.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

        java.util.Map<String, Object> result_ = new java.util.LinkedHashMap<>();
        result_.put("'Base Vacation Days'", output_);
        if (trace_) {
            result_.put("_explain", ((com.gs.dmn.runtime.listener.TreeTraceEventListener) eventListener_).getRoot());
        }

        return result_;
    }
}
