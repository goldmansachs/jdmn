<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "${modelRepository.name(itemDefinition)}"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ${javaClassName} implements ${transformer.itemDefinitionJavaInterfaceName(javaClassName)} {
    <@addFields itemDefinition />

    public ${javaClassName}() {
    }

    public ${javaClassName}(${transformer.itemDefinitionSignature(itemDefinition)}) {
    <@addAssigmentForFields itemDefinition />
    }

    <@addAccessors itemDefinition />
    <@addEqualsAndHashCode itemDefinition />
    <@addToString itemDefinition />
}
<#macro addFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.itemDefinitionVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionTypeName(child)/>
        private ${memberType} ${memberName};
    </#list>
</#macro>

<#macro addAssigmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        this.${transformer.setter(child)}(${transformer.itemDefinitionVariableName(child)});
    </#list>
</#macro>

<#macro addAccessors itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.itemDefinitionVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionTypeName(child)/>
    @com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    public ${memberType} ${transformer.getter(child)} {
        return this.${memberName};
    }

    @com.fasterxml.jackson.annotation.JsonSetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    public void ${transformer.setter(child)}(${memberType} ${memberName}) {
        this.${memberName} = ${memberName};
    }

    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

</#macro>

<#macro addToString itemDefinition>
    @Override
    public String toString() {
        return asString();
    }
</#macro>
