
import java.util.Map;

/**
 * Handler for requests to Lambda function for DRG elements in model '0020-vacation-days'.
 */
public class F0020VacationDaysMapRequestHandler implements com.amazonaws.services.lambda.runtime.RequestHandler<Map<String, String>, Object> {
    private static final com.gs.dmn.runtime.discovery.ModelElementRegistry REGISTRY = new com.gs.dmn.runtime.discovery.ModelElementRegistry();

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

        com.amazonaws.services.lambda.runtime.LambdaLogger logger = context_.getLogger();

        String elementName = event_.get("_element");
        boolean trace_ = Boolean.parseBoolean(event_.get("_trace"));
        logger.log(String.format("Executing element '%s'", elementName));
        try {
            // Prepare execution context
            com.gs.dmn.runtime.listener.EventListener listener_ = trace_ ? new com.gs.dmn.runtime.listener.TreeTraceEventListener() : new com.gs.dmn.runtime.listener.NopEventListener();
            com.gs.dmn.runtime.ExecutionContext executionContext_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withListener(listener_).build();

            // Execute element
            com.gs.dmn.runtime.ExecutableDRGElement element = REGISTRY.discover(elementName);
            Object output_ = element.applyMap(event_, executionContext_);

            // Return response
            Map<String, Object> response_ = new java.util.LinkedHashMap<>();
            response_.put(elementName, output_);
            if (trace_) {
                response_.put("_explain", ((com.gs.dmn.runtime.listener.TreeTraceEventListener) listener_).getRoot());
            }
            return response_;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Error executing DRG Element '%s'", elementName), e);
        }
    }
}
