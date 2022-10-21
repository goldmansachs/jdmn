<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#--
    Events
-->
<#macro startDRGElement drgElement>
            // ${transformer.startElementCommentText(drgElement)}
            long ${transformer.namedElementVariableName(drgElement)}StartTime_ = <@currentTimeMillis/>;
            ${transformer.argumentsClassName()} ${transformer.argumentsVariableName(drgElement)} = ${transformer.defaultConstructor(transformer.argumentsClassName())};
            <#assign elementNames = transformer.drgElementArgumentDisplayNameList(drgElement)/>
            <#list transformer.drgElementArgumentNameList(drgElement)>
            <#items as arg>
            ${transformer.argumentsVariableName(drgElement)}.put("${transformer.escapeInString(elementNames[arg?index])}", ${arg});
            </#items>
            </#list>
            ${transformer.eventListenerVariableName()}.startDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.argumentsVariableName(drgElement)});
</#macro>

<#macro endDRGElement drgElement output>
    <@endDRGElementIndent "" drgElement output/>
</#macro>

<#macro endDRGElementIndent extraIndent drgElement output>
            ${extraIndent}// ${transformer.endElementCommentText(drgElement)}
            ${extraIndent}${transformer.eventListenerVariableName()}.endDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.argumentsVariableName(drgElement)}, ${output}, (<@currentTimeMillis/> - ${transformer.namedElementVariableName(drgElement)}StartTime_));
</#macro>

<#macro endDRGElementAndReturn drgElement output>
            <@endDRGElementAndReturnIndent "" drgElement output/>
</#macro>

<#macro endDRGElementAndReturnIndent extraIndent drgElement output>
            <@endDRGElementIndent extraIndent drgElement output/>

            ${extraIndent}return ${output};
</#macro>

<#macro startRule drgElement rule_index>
        // Rule start
        ${transformer.eventListenerVariableName()}.startRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>);
</#macro>

<#macro matchRule drgElement rule_index>
            // Rule match
            ${transformer.eventListenerVariableName()}.matchRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>);
</#macro>

<#macro endRule drgElement rule_index output>
        // Rule end
        ${transformer.eventListenerVariableName()}.endRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>, ${output});
</#macro>

<#macro drgElementAnnotation drgElement>${transformer.drgElementMetadataFieldName()}</#macro>

<#macro ruleAnnotation>${transformer.drgRuleMetadataFieldName()}</#macro>

<#macro currentTimeMillis>System.currentTimeMillis()</#macro>