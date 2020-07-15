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

@javax.annotation.Generated(value = {"itemDefinition.ftl", "${modelRepository.name(itemDefinition)}"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ${javaClassName} implements ${transformer.itemDefinitionNativeSimpleInterfaceName(javaClassName)} {
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
        <#assign memberName = transformer.namedElementVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
        private ${memberType} ${memberName};
    </#list>
</#macro>

<#macro addAssigmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        this.${transformer.setter(child)}(${transformer.namedElementVariableName(child)});
    </#list>
</#macro>

<#macro addAccessors itemDefinition>
    <#list itemDefinition.itemComponent as child>
        <#assign memberName = transformer.namedElementVariableName(child)/>
        <#assign memberType = transformer.itemDefinitionNativeQualifiedInterfaceName(child)/>
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
