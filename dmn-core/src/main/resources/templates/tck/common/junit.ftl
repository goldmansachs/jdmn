<#if packageName?has_content>
package ${packageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "${testCases.modelName}"})
public class ${testClassName} extends ${decisionBaseClass} {
    <@addTestCases />
}
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
    @org.junit.Test
    public void testCase${tc.id}() {
        <@initializeInputs tc/>

        <@checkResults tc/>
    }

        </#items>
    </#list>
    private void checkValues(Object expected, Object actual) {
        ${tckUtil.assertClassName()}.assertEquals(expected, actual);
    }
</#macro>

<#macro initializeInputs testCase>
        ${tckUtil.annotationSetClassName()} ${tckUtil.annotationSetVariableName()} = new ${tckUtil.annotationSetClassName()}();
        ${tckUtil.eventListenerClassName()} ${tckUtil.eventListenerVariableName()} = new ${tckUtil.defaultEventListenerClassName()}();
        ${tckUtil.externalExecutorClassName()} ${tckUtil.externalExecutorVariableName()} = new ${tckUtil.defaultExternalExecutorClassName()}();
        <#if tckUtil.isCaching()>
        ${tckUtil.cacheInterfaceName()} ${tckUtil.cacheVariableName()} = new ${tckUtil.defaultCacheClassName()}();
        </#if>
    <#list testCase.inputNode>
        // Initialize input data
        <#items as input>
        ${tckUtil.toJavaType(input)} ${tckUtil.inputDataVariableName(input)} = ${tckUtil.toJavaExpression(testCases, testCase, input)};
        </#items>
    </#list>
</#macro>

<#macro checkResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        // Check ${result.name}
        checkValues(${tckUtil.toJavaExpression(testCases, result)}, new ${tckUtil.qualifiedName(packageName, tckUtil.drgElementClassName(result))}().apply(${tckUtil.drgElementArgumentsExtraCache(tckUtil.drgElementArgumentsExtra(tckUtil.drgElementArgumentList(result)))}));
        </#items>
    </#list>
</#macro>