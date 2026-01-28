<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#if javaPackageName?has_content>
package ${javaPackageName}
</#if>

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "${modelRepository.name(itemDefinition)}"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = ${serializationClass}::class)
interface ${javaClassName} : ${transformer.dmnTypeClassName()} {
    <@addMembers itemDefinition />
    <@addToContext itemDefinition />
    <@addEqualsAndHashCode itemDefinition />
    <@addToString itemDefinition />

    companion object {
        <@addConvertMethod itemDefinition />
    }
}
<#macro addMembers itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.namedElementVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
    @get:com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    val ${transformer.namedElementVariableName(child)}: ${memberType}

    </#list>
</#macro>

<#macro addToContext itemDefinition >
    override fun toContext(): ${transformer.contextClassName()} {
        val context = ${transformer.defaultConstructor(transformer.contextClassName())}
        <#list itemDefinition.itemComponent as child>
            <#assign memberName = transformer.namedElementVariableName(child)/>
        context.add("${memberName}", this.${memberName})
        </#list>
        return context
    }

</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ${javaClassName}
        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.namedElementVariableName(child)/>
        if (if (this.${member} != null) this.${member} != other.${member} else other.${member} != null) return false
        </#list>

        return true
    }

    fun hash(): Int {
        var result = 0
        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.namedElementVariableName(child)/>
        result = 31 * result + (if (this.${member} != null) this.${member}.hashCode() else 0)
        </#list>
        return result
    }

</#macro>

<#macro addToString itemDefinition>
    fun asString(): String {
        val result_ = StringBuilder("{")
    <#list modelRepository.sortItemComponent(itemDefinition) as child>
        <#assign label = transformer.escapeInString(modelRepository.displayName(child))/>
        <#assign member = transformer.namedElementVariableName(child)/>
        <#if child_index == 0>
        result_.append("${label}=" + ${member})
        <#else>
        result_.append(", ${label}=" + ${member})
        </#if>
    </#list>
        result_.append("}")
        return result_.toString()
    }
</#macro>

<#macro addConvertMethod itemDefinition>
        @JvmStatic
        fun ${transformer.convertMethodName(itemDefinition)}(other: Any?): ${javaClassName}? {
            if (other == null) {
                return null
            } else if (other is ${javaClassName}?) {
                return other
            } else if (other is ${transformer.contextClassName()}) {
                var result_ = ${transformer.itemDefinitionNativeClassName(javaClassName)}()
            <#list itemDefinition.itemComponent as child>
                <#assign memberName = transformer.namedElementVariableName(child)/>
                <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
                <#assign nameString = "\"${modelRepository.name(child)}\"" />
                <#assign labelString = "\"${modelRepository.label(child)}\"" />
                <#assign childFEELType = transformer.toFEELType(child) />
                <#if modelRepository.label(child)?has_content>
                var ${memberName} = other.get(${nameString}, ${labelString})
                <#else>
                var ${memberName} = other.get(${nameString})
                </#if>
                <#if transformer.isComplexType(childFEELType)>
                result_.${memberName} =  ${transformer.convertToItemDefinitionType(memberName, childFEELType)}
                <#elseif transformer.isListOfComplexType(childFEELType)>
                <#assign listSource = "(${memberName} as java.util.List<*>)" />
                result_.${memberName} = ${transformer.convertToListOfItemDefinitionType(listSource, childFEELType.elementType)} as ${memberType}
                <#else>
                result_.${memberName} = ${memberName} as ${memberType}
                </#if>
             </#list>
                return result_
            } else if (other is ${transformer.dmnTypeClassName()}) {
                return ${transformer.convertMethodName(itemDefinition)}(other.toContext())
            } else {
                throw ${transformer.dmnRuntimeExceptionClassName()}(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), ${javaClassName}::class.java.getSimpleName()))
            }
        }
</#macro>
