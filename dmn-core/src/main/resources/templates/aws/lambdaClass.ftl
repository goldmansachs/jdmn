<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>
<#assign eventVariable = "event_" />
<#assign contextVariable = "context_" />
<#assign outputVariable = "output_" />
<#assign resultVariable = "result_" />
<#assign traceVariable = "trace_" />

import java.util.Map;

/**
 * Handler for requests to Lambda function for DRG elements in model '${modelName}'.
 */
public class ${javaClassName} implements com.amazonaws.services.lambda.runtime.RequestHandler<Map<String, String>, Object> {
    <#assign executorClassName = transformer.executorClassName() />
    <#assign registryClassName = "ModelElementRegistry" />
    private static final ${executorClassName} EXECUTOR = ${transformer.constructor(executorClassName, transformer.defaultConstructor(registryClassName))};

    public Object handleRequest(Map<String, String> ${eventVariable}, com.amazonaws.services.lambda.runtime.Context ${contextVariable}) {
        // Parameters
        // ----------
        // ${eventVariable}: Map<String, String>, required
        //     Input event to the Lambda function

        // ${contextVariable}: Context, required
        //     Lambda Context runtime methods and attributes

        // Returns
        // ------
        //     Map<String, String>: Object containing details of the event

        com.amazonaws.services.lambda.runtime.LambdaLogger logger = ${contextVariable}.getLogger();

        String elementName = ${eventVariable}.get("_element");
        boolean ${traceVariable} = Boolean.parseBoolean(${eventVariable}.get("_trace"));
        logger.log(String.format("Executing element '%s'", elementName));
        try {
            // Prepare execution context
            ${transformer.annotationSetClassName()} annotations_ = ${transformer.defaultConstructor(transformer.annotationSetClassName())};
            ${transformer.eventListenerClassName()} listener_ = ${traceVariable} ? ${transformer.defaultConstructor(transformer.treeTraceEventListenerClassName())} : ${transformer.defaultConstructor(transformer.defaultEventListenerClassName())};
            ${transformer.externalExecutorClassName()} executor_ = ${transformer.defaultConstructor(transformer.defaultExternalExecutorClassName())};
            ${transformer.cacheInterfaceName()} ${transformer.cacheVariableName()} = ${transformer.defaultConstructor(transformer.defaultCacheClassName())};
            ${transformer.executionContextClassName()} executionContext_ = ${transformer.constructor(transformer.executionContextClassName(), "annotations_, listener_, executor_, ${transformer.cacheVariableName()}")};

            // Execute element
            Object output_ = EXECUTOR.execute(elementName, event_, executionContext_);

            // Return response
            Map<String, Object> response_ = new java.util.LinkedHashMap<>();
            response_.put(elementName, output_);
            if (trace_) {
                response_.put("_explain", ((${transformer.treeTraceEventListenerClassName()}) listener_).getRoot());
            }
            return response_;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Error executing DRG Element '%s'", elementName), e);
        }
    }
}
