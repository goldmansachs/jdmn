<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "${modelRepository.name(itemDefinition)}"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
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
            ${transformer.itemDefinitionJavaClassName(javaClassName)} result_ = new ${transformer.itemDefinitionJavaClassName(javaClassName)}();
        <#list itemDefinition.itemComponent as child>
            <#if modelRepository.label(child)?has_content>
            result_.${transformer.setter(child)}((${transformer.itemDefinitionTypeName(child)})((${transformer.contextClassName()})other).get("${modelRepository.name(child)}", "${modelRepository.label(child)}"));
            <#else>
            result_.${transformer.setter(child)}((${transformer.itemDefinitionTypeName(child)})((${transformer.contextClassName()})other).get("${modelRepository.name(child)}"));
            </#if>
        </#list>
            return result_;
        } else if (other instanceof ${transformer.dmnTypeClassName()}) {
            return ${transformer.convertMethodName(itemDefinition)}(((${transformer.dmnTypeClassName()})other).toContext());
        } else {
            throw new ${transformer.dmnRuntimeExceptionClassName()}(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), ${javaClassName}.class.getSimpleName()));
        }
    }
</#macro>

<#macro addAccessors itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.itemDefinitionVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionTypeName(child)/>
    @com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    ${memberType} ${transformer.getter(child)};

    </#list>
</#macro>

<#macro addToContext itemDefinition >
    default ${transformer.contextClassName()} toContext() {
        ${transformer.contextClassName()} context = ${transformer.defaultConstructor(transformer.contextClassName())};
        <#list itemDefinition.itemComponent as child>
            <#assign memberName = transformer.itemDefinitionVariableName(child)/>
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
