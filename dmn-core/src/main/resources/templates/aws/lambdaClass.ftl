<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>
<#assign repository = transformer.getDMNModelRepository() />
<#assign eventVariable = "event_" />
<#assign contextVariable = "context_" />
<#assign outputVariable = "output_" />
<#assign resultVariable = "result_" />
<#assign traceVariable = "trace_" />

import java.util.Map;

/**
 * Handler for requests to Lambda function for element '${repository.name(element)}' in model '${modelName}'.
 */
public class ${javaClassName} implements com.amazonaws.services.lambda.runtime.RequestHandler<Map<String, String>, Object> {
    <#assign elementQName = transformer.qualifiedName(element) >
    <#if transformer.isSingletonDecision()>
    private final ${elementQName} ${transformer.namedElementVariableName(element)} = ${transformer.singletonDecisionInstance(elementQName)};
    <#else>
    private final ${elementQName} ${transformer.namedElementVariableName(element)} = ${transformer.defaultConstructor(elementQName)};
    </#if>

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

        <#assign inputDataReferenceList = transformer.inputDataClosure(repository.makeDRGElementReference(element)) />
        <#list inputDataReferenceList as reference>
        String ${transformer.namedElementVariableName(reference.element)} = ${eventVariable}.get("${repository.name(reference.element)}");
        </#list>
        boolean ${traceVariable} = Boolean.parseBoolean(${eventVariable}.get("_trace"));

        ${transformer.annotationSetClassName()} ${transformer.annotationSetVariableName()} = ${transformer.defaultConstructor(transformer.annotationSetClassName())};
        ${transformer.eventListenerClassName()} ${transformer.eventListenerVariableName()} = ${traceVariable} ? ${transformer.defaultConstructor(transformer.treeTraceEventListenerClassName())} : ${transformer.defaultConstructor(transformer.defaultEventListenerClassName())};
        ${transformer.externalExecutorClassName()} ${transformer.externalExecutorVariableName()} = ${transformer.defaultConstructor(transformer.defaultExternalExecutorClassName())};
        ${transformer.cacheInterfaceName()} ${transformer.cacheVariableName()} = ${transformer.defaultConstructor(transformer.defaultCacheClassName())};
        ${transformer.drgElementOutputType(element)} ${outputVariable} = ${transformer.namedElementVariableName(element)}.apply(${transformer.drgElementArgumentListExtraCache(element)});

        java.util.Map<String, Object> ${resultVariable} = new java.util.LinkedHashMap<>();
        ${resultVariable}.put("${repository.name(element)}", ${outputVariable});
        if (${traceVariable}) {
            ${resultVariable}.put("_explain", ((${transformer.treeTraceEventListenerClassName()}) eventListener_).preorderNodes());
        }

        return ${resultVariable};
    }
}
