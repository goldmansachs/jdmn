<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

/**
 * Handler for requests to Lambda function for model '${modelName}'.
 */
public class ${javaClassName} implements RequestHandler<Map<String, String>, Object> {
    public Object handleRequest(Map<String, String> event, Context context) {
        // Parameters
        // ----------
        // event: Map<String, String>, required
        //     Input event to the Lambda function

        // context: Context, required
        //     Lambda Context runtime methods and attributes

        // Returns
        // ------
        //     Map<String, String>: Object containing details of the event

        return event;
    }
}
