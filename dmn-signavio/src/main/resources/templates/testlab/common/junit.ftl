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
package ${packageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "${testLab.rootDecisionId}"})
<#assign rootOutputParameter = testLab.rootOutputParameter />
public class ${testClassName} extends ${decisionBaseClass} {
    <@addDecisionField />

    <@addTestCases />
}
<#macro addDecisionField>
    private final ${testLabUtil.qualifiedName(packageName, testLabUtil.drgElementClassName(rootOutputParameter))} ${testLabUtil.drgElementVariableName(rootOutputParameter)} = new ${testLabUtil.qualifiedName(packageName, testLabUtil.drgElementClassName(rootOutputParameter))}();
</#macro>

<#macro addTestCases>
    <#list testLab.testCases>
        <#items as testCase>
    @org.junit.Test
    public void testCase${(testCase?index + 1)?c}() {
        ${testLabUtil.annotationSetClassName()} ${testLabUtil.annotationSetVariableName()} = new ${testLabUtil.annotationSetClassName()}();
        <@addApplyPart testCase/>

        <@addAssertPart testCase/>
    }

        </#items>
    </#list>
    private void checkValues(Object expected, Object actual) {
        ${testLabUtil.assertClassName()}.assertEquals(expected, actual);
    }
</#macro>

<#macro addApplyPart testCase>
    <#list testCase.inputValues>
        <#items as input>
        ${testLabUtil.toJavaType(testLab.inputParameterDefinitions[input?index])} ${testLabUtil.inputDataVariableName(testLab.inputParameterDefinitions[input?index])} = ${testLabUtil.toJavaExpression(testLab, testCase, input?index)};
        </#items>
    </#list>
        ${testLabUtil.drgElementOutputType(rootOutputParameter)} ${testLabUtil.drgElementVariableName(rootOutputParameter)} = this.${testLabUtil.drgElementVariableName(rootOutputParameter)}.apply(${testLabUtil.drgElementArgumentList(rootOutputParameter)});
</#macro>

<#macro addAssertPart testCase>
    <#list testCase.expectedValues>
        <#items as expectedValue>
            <#if testLabUtil.isComplex(expectedValue)>
                <#list expectedValue.slots>
                    <#items as slot>
                    <#if testLabUtil.hasListType(rootOutputParameter)>
        checkValues(${testLabUtil.toJavaExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)}.get(${expectedValue?index}).${testLabUtil.getter(testLabUtil.drgElementOutputFieldName(testLab, slot?index))});
                    <#else>
        checkValues(${testLabUtil.toJavaExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)}.${testLabUtil.getter(testLabUtil.drgElementOutputFieldName(testLab, slot?index))});
                    </#if>
                    </#items>
                </#list>
            <#else>
        checkValues(${testLabUtil.toJavaExpression(testLab, expectedValue)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)});
            </#if>
        </#items>
    </#list>
</#macro>