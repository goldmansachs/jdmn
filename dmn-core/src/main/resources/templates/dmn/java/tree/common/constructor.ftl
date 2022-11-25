<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro decisionConstructor drgElement javaClassName>
    <#if transformer.isSingletonDecision()>

    private static class ${javaClassName}LazyHolder {
        static final ${javaClassName} INSTANCE = ${transformer.singletonDecisionConstructor(javaClassName, drgElement)};
    }
    public static ${javaClassName} instance() {
        return ${javaClassName}LazyHolder.INSTANCE;
    }
    </#if>
    <@addSubDecisionFields drgElement/>

    public ${javaClassName}() {
        <#if transformer.hasDirectSubDecisions(drgElement)>
        this(${transformer.drgElementConstructorNewArgumentList(drgElement)});
        </#if>
    }
    <#if transformer.hasDirectSubDecisions(drgElement)>

    public ${javaClassName}(${transformer.drgElementConstructorSignature(drgElement)}) {
        <@setSubDecisionFields drgElement/>
    }
    </#if>
</#macro>

<#macro bkmConstructor drgElement javaClassName>
    private static class ${javaClassName}LazyHolder {
        static final ${javaClassName} INSTANCE = new ${javaClassName}();
    }
    public static ${javaClassName} instance() {
        return ${javaClassName}LazyHolder.INSTANCE;
    }

    private ${javaClassName}() {
    }
</#macro>

<#macro dsConstructor drgElement javaClassName>
    private static class ${javaClassName}LazyHolder {
        static final ${javaClassName} INSTANCE = new ${javaClassName}();
    }
    public static ${javaClassName} instance() {
        return ${javaClassName}LazyHolder.INSTANCE;
    }
    <@addSubDecisionFields drgElement/>

    private ${javaClassName}() {
        <#if transformer.hasDirectSubDecisions(drgElement)>
        this(${transformer.drgElementConstructorNewArgumentList(drgElement)});
        </#if>
    }
    <#if transformer.hasDirectSubDecisions(drgElement)>

    private ${javaClassName}(${transformer.drgElementConstructorSignature(drgElement)}) {
        <@setSubDecisionFields drgElement/>
    }
    </#if>
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
