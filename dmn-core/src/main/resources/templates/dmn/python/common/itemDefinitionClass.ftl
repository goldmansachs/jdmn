<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
import typing
import decimal
import datetime
import time
import isodate

import ${transformer.qualifiedModuleName(javaPackageName, transformer.itemDefinitionNativeSimpleInterfaceName(javaClassName))}
<@importItemDefinitionComplexComponents itemDefinition />


# Generated(value = {"itemDefinition.ftl", "${modelRepository.name(itemDefinition)}"})
<#assign qualifiedInterfaceName = transformer.qualifiedName(javaPackageName, transformer.itemDefinitionNativeSimpleInterfaceName(javaClassName))/>
class ${javaClassName}(${qualifiedInterfaceName}):
    def __init__(self, ${transformer.itemDefinitionSignature(itemDefinition)}):
        ${qualifiedInterfaceName}.__init__(self)

    <@addAssignmentForFields itemDefinition />

    <@addEqualsAndHashCode itemDefinition />
    <@addToString itemDefinition />
<#macro addAssignmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        self.${transformer.namedElementVariableName(child)} = ${transformer.namedElementVariableName(child)}
    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    def __eq__(self, other: typing.Any) -> bool:
        return self.equalTo(other)

    def __hash__(self):
        return self.hashCode()

</#macro>

<#macro addToString itemDefinition>
    def __str__(self) -> str:
        return self.asString()
</#macro>

<#macro importItemDefinitionComplexComponents itemDefinition>
    <#list transformer.itemDefinitionComplexComponents(itemDefinition)>
        <#items as component>
import ${transformer.qualifiedModuleName(javaPackageName, component)}
        </#items>
    </#list>
</#macro>