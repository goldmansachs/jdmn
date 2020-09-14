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
package ${javaPackageName};
</#if>

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "${modelRepository.name(itemDefinition)}"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = ${serializationClass}.class)
public interface ${javaClassName} extends ${transformer.dmnTypeClassName()} {
    <@addConvertMethod itemDefinition />

    <@addAccessors itemDefinition />
    <@addToContext itemDefinition />
    <@addEqualsAndHashCode itemDefinition />
    <@addToString itemDefinition />
}
<#macro addConvertMethod itemDefinition>
    static ${javaClassName} ${transformer.convertMethodName(itemDefinition)}(Object other) {
        if (other == null) {
            return null;
        } else if (${javaClassName}.class.isAssignableFrom(other.getClass())) {
            return (${javaClassName})other;
        } else if (other instanceof ${transformer.contextClassName()}) {
            ${transformer.itemDefinitionNativeClassName(javaClassName)} result_ = new ${transformer.itemDefinitionNativeClassName(javaClassName)}();
        <#list itemDefinition.itemComponent as child>
            <#if modelRepository.label(child)?has_content>
            result_.${transformer.setter(child)}((${transformer.itemDefinitionNativeQualifiedInterfaceName(child)})((${transformer.contextClassName()})other).get("${modelRepository.name(child)}", "${modelRepository.label(child)}"));
            <#else>
            result_.${transformer.setter(child)}((${transformer.itemDefinitionNativeQualifiedInterfaceName(child)})((${transformer.contextClassName()})other).get("${modelRepository.name(child)}"));
            </#if>
        </#list>
            return result_;
        } else if (other instanceof ${transformer.dmnTypeClassName()}) {
            return ${transformer.convertMethodName(itemDefinition)}(((${transformer.dmnTypeClassName()})other).toContext());
    <#if transformer.isGenerateProto()>
        } else if (other instanceof ${transformer.qualifiedProtoMessageName(itemDefinition)}) {
            ${transformer.itemDefinitionNativeClassName(javaClassName)} result_ = ${transformer.defaultConstructor(transformer.itemDefinitionNativeClassName(javaClassName))};
        <#list itemDefinition.itemComponent as child>
            result_.${transformer.setter(child)}(${transformer.convertProtoMember("other", itemDefinition, child, true)});
        </#list>
            return result_;
    </#if>
        } else {
            throw new ${transformer.dmnRuntimeExceptionClassName()}(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), ${javaClassName}.class.getSimpleName()));
        }
    }
    <#if transformer.isGenerateProto()>

    static ${transformer.qualifiedProtoMessageName(itemDefinition)} toProto(${javaClassName} other) {
        ${transformer.qualifiedProtoMessageName(itemDefinition)}.Builder result_ = ${transformer.qualifiedProtoMessageName(itemDefinition)}.newBuilder();
        if (other != null) {
        <#list itemDefinition.itemComponent as child>
            <#assign memberVariable = transformer.namedElementVariableNameProto(child) />
            ${transformer.qualifiedNativeProtoType(child)} ${memberVariable} = ${transformer.convertMemberToProto("other", javaClassName, child, true)};
            <#if transformer.isProtoReference(child)>
            if (${memberVariable} != null) {
                result_.${transformer.protoSetter(child)}(${memberVariable});
            }
            <#else>
            result_.${transformer.protoSetter(child)}(${memberVariable});
            </#if>
        </#list>
        }
        return result_.build();
    }

    static List<${transformer.qualifiedProtoMessageName(itemDefinition)}> toProto(List<${javaClassName}> other) {
        if (other == null) {
            return null;
        } else {
            return other.stream().map(o -> toProto(o)).collect(java.util.stream.Collectors.toList());
        }
    }
    </#if>
</#macro>

<#macro addAccessors itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.namedElementVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
    @com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    ${memberType} ${transformer.getter(child)};

    </#list>
</#macro>

<#macro addToContext itemDefinition >
    default ${transformer.contextClassName()} toContext() {
        ${transformer.contextClassName()} context = ${transformer.defaultConstructor(transformer.contextClassName())};
        <#list itemDefinition.itemComponent as child>
            <#assign memberName = transformer.namedElementVariableName(child)/>
            <#assign member = transformer.getter(child)/>
        context.put("${memberName}", ${member});
        </#list>
        return context;
    }

</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ${javaClassName} other = (${javaClassName}) o;
        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.getter(child)/>
        if (this.${member} != null ? !this.${member}.equals(other.${member}) : other.${member} != null) return false;
        </#list>

        return true;
    }

    default int hash() {
        int result = 0;
        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.getter(child)/>
        result = 31 * result + (this.${member} != null ? this.${member}.hashCode() : 0);
        </#list>
        return result;
    }

</#macro>

<#macro addToString itemDefinition>
    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
    <#list modelRepository.sortItemComponent(itemDefinition) as child>
        <#assign label = transformer.escapeInString(modelRepository.displayName(child))/>
        <#assign member = transformer.getter(child)/>
        <#if child_index == 0>
        result_.append("${label}=" + ${member});
        <#else>
        result_.append(", ${label}=" + ${member});
        </#if>
    </#list>
        result_.append("}");
        return result_.toString();
    }
</#macro>
