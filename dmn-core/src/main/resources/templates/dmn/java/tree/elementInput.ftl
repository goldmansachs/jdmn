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

    <@addConstructors inputArguments />

    <@addAccessors inputArguments />
}
<#macro addFields inputArguments>
    <#list inputArguments as argument>
        <#assign nativeMemberName = argument.right.name/>
        <#assign nativeMemberType = argument.right.type/>
    private ${nativeMemberType} ${nativeMemberName};
    </#list>
</#macro>

<#macro addConstructors inputArguments>
    public ${javaClassName}() {
    }

    public ${javaClassName}(${transformer.contextClassName()} input_) {
        if (input_ != null) {
        <#list inputArguments as argument>
            <#assign nativeMemberName = argument.right.name/>
            <#assign nativeMemberType = argument.right.type/>
            <#assign feelMemberType = argument.left.type/>
            <#assign memberKey = "\"${argument.left.displayName}\""/>
            <#assign castToMemberType = "(${nativeMemberType})" />
            Object ${nativeMemberName} = input_.get(${memberKey});
            <#if transformer.isComplexType(feelMemberType)>
            ${transformer.setter(nativeMemberName, "${transformer.convertToItemDefinitionType(nativeMemberName, feelMemberType)}")};
            <#elseif transformer.isListOfComplexType(feelMemberType)>
            <#assign listSource = "((java.util.List)${nativeMemberName})" />
            ${transformer.setter(nativeMemberName, "${castToMemberType}${transformer.convertToListOfItemDefinitionType(listSource, feelMemberType.elementType)}")};
            <#else>
            ${transformer.setter(nativeMemberName, "${castToMemberType}${nativeMemberName}")};
            </#if>
        </#list>
        }
    }
</#macro>

<#macro addAccessors inputArguments>
    <#list inputArguments as argument>
        <#assign memberName = argument.right.name/>
        <#assign nativeMemberType = argument.right.type/>
    public ${nativeMemberType} ${transformer.getter(memberName)} {
        return this.${memberName};
    }

    public void ${transformer.setter(memberName, "${nativeMemberType} ${memberName}")} {
        this.${memberName} = ${memberName};
    }

    </#list>
</#macro>
