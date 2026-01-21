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

@javax.annotation.Generated(value = {"inputElement.ftl", "${transformer.escapeInString(modelRepository.name(drgElement))}"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ${javaClassName} implements ${transformer.drgElementInputPojoInterfaceName()} {
    <#assign inputArguments = transformer.drgElementArgumentListInputPojo(drgElement) />
    <@addFields inputArguments />

    <@addAccessors inputArguments />
}
<#macro addFields inputArguments>
    <#list inputArguments as argument>
        <#assign memberName = argument.name/>
        <#assign memberType = argument.type/>
        private ${memberType} ${memberName};
    </#list>
</#macro>

<#macro addAccessors inputArguments>
    <#list inputArguments as argument>
        <#assign memberName = argument.name/>
        <#assign memberType = argument.type/>
    public ${memberType} ${transformer.getter(memberName)} {
        return this.${memberName};
    }

    public void ${transformer.setter(memberName, "${memberType} ${memberName}")} {
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
