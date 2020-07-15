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

@javax.annotation.Generated(value = ["itemDefinition.ftl", "${modelRepository.name(itemDefinition)}"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class ${javaClassName} : ${transformer.itemDefinitionNativeSimpleInterfaceName(javaClassName)} {
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
        <#assign memberName = transformer.namedElementVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
    @get:com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    @set:com.fasterxml.jackson.annotation.JsonGetter("${transformer.escapeInString(modelRepository.displayName(child))}")
    override var ${memberName}: ${memberType} = null

    </#list>
</#macro>

<#macro addAssigmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        this.${transformer.namedElementVariableName(child)} = ${transformer.namedElementVariableName(child)}
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
