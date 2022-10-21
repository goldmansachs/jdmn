<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro decisionConstructor drgElement >
    <#if transformer.isSingletonDecision()>
    <@singletonPattern drgElement />
    <#else>

    def __init__(${transformer.drgElementConstructorSignature(drgElement)}):
        ${decisionBaseClass}.__init__(self)
        <@setSubDecisionFields drgElement/>
    </#if>
</#macro>

<#macro bkmConstructor drgElement >
    <@singletonPattern drgElement />
</#macro>

<#macro dsConstructor drgElement >
    <@singletonPattern drgElement />
</#macro>

<#macro singletonPattern drgElement>
    _instance = None

    def __init__(self):
        raise RuntimeError("Call instance() instead")

    @classmethod
    def instance(cls):
        if cls._instance is None:
            cls._instance = cls.__new__(cls)
            ${decisionBaseClass}.__init__(cls._instance)
    <#if transformer.hasDirectSubDecisions(drgElement)>
            cls._instance.initSubDecisions()
        return cls._instance

    def initSubDecisions(${transformer.drgElementConstructorSignature(drgElement)}):
        <@setSubDecisionFields drgElement/>
    <#else>
        return cls._instance
    </#if>
</#macro>

<#--
    Sub decisions fields
-->
<#macro setSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
        <#assign member = transformer.drgElementReferenceVariableName(subDecision)>
        <#if transformer.isSingletonDecision()>
            <#assign defaultValue = transformer.singletonDecisionInstance(transformer.qualifiedName(subDecision))/>
        <#else>
            <#assign defaultValue = transformer.defaultConstructor(transformer.qualifiedName(subDecision))/>
        </#if>
        self.${member} = ${defaultValue} if ${member} is None else ${member}
        </#items>
    </#list>
</#macro>
