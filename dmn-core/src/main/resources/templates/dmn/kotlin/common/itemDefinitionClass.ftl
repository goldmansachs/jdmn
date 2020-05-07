<#if javaPackageName?has_content>
package ${javaPackageName}
</#if>

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "${modelRepository.name(itemDefinition)}"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class ${javaClassName} : ${transformer.itemDefinitionJavaSimpleInterfaceName(javaClassName)} {
    <@addFields itemDefinition />
    constructor() {
    }

    constructor (${transformer.itemDefinitionSignature(itemDefinition)}) {
    <@addAssigmentForFields itemDefinition />
    }

    <@addEqualsAndHashCode itemDefinition />
    <@addToString itemDefinition />
}
<#macro addFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.itemDefinitionVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionJavaQualifiedInterfaceName(child)/>
    @get:com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    @set:com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    override var ${memberName}: ${memberType} = null

    </#list>
</#macro>

<#macro addAssigmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        this.${transformer.itemDefinitionVariableName(child)} = ${transformer.itemDefinitionVariableName(child)}
    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    override fun equals(other: Any?): Boolean {
        return equalTo(other)
    }

    override fun hashCode(): Int {
        return hash()
    }

</#macro>

<#macro addToString itemDefinition>
    @Override
    override fun toString(): String {
        return asString()
    }
</#macro>
