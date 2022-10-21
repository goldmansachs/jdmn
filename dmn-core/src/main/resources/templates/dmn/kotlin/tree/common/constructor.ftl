<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro bkmConstructor drgElement javaClassName>
        private val INSTANCE = ${javaClassName}()

        @JvmStatic
        fun instance(): ${javaClassName} {
            return INSTANCE
        }
</#macro>

<#--
    Sub decisions fields
-->
<#macro addSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>

        <#items as subDecision>
    private final ${transformer.qualifiedName(subDecision)} ${transformer.drgElementReferenceVariableName(subDecision)};
        </#items>
    </#list>
</#macro>

<#macro setSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
        this.${transformer.drgElementReferenceVariableName(subDecision)} = ${transformer.drgElementReferenceVariableName(subDecision)};
        </#items>
    </#list>
</#macro>
