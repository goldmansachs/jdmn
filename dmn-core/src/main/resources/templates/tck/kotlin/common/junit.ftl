<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#if packageName?has_content>
package ${packageName}
</#if>

import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "${testCases.modelName}"])
class ${testClassName} : ${decisionBaseClass}() {
    <@addTestCases />
}
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
    @org.junit.jupiter.api.Test
    fun testCase${tc.id}() {
        <@initializeInputs tc/>

        <@checkResults tc/>
    }

        </#items>
    </#list>
    private fun checkValues(expected: Any?, actual: Any?) {
        ${tckUtil.assertClassName()}.assertEquals(expected, actual)
    }
</#macro>

<#macro initializeInputs testCase>
        val ${tckUtil.executionContextVariableName()} = ${tckUtil.executionContextBuilderClassName()}.executionContext().build()
        val ${tckUtil.cacheVariableName()} = ${tckUtil.executionContextVariableName()}.getCache()
    <#list testCase.inputNode>
        // Initialize input data
        <#items as input>
        <#assign inputInfo = tckUtil.extractInputNodeInfo(testCases, testCase, input) >
        val ${tckUtil.inputDataVariableName(inputInfo)}: ${tckUtil.toNativeType(inputInfo)} = ${tckUtil.toNativeExpression(inputInfo)}
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${tckUtil.inputDataVariableName(inputInfo)}", ${tckUtil.inputDataVariableName(inputInfo)})
        </#if>
        </#items>
    </#list>
</#macro>

<#macro checkResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        // Check '${result.name}'
        <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, testCase, result) >
        checkValues(${tckUtil.toNativeExpression(resultInfo)}, ${tckUtil.defaultConstructor(tckUtil.qualifiedName(resultInfo))}.apply(${tckUtil.drgElementArgumentList(resultInfo)}))
        </#items>
    </#list>
</#macro>
