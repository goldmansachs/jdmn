<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.

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

import ${transformer.jdmnRootPackage()}.runtime.DMNType
import ${transformer.jdmnRootPackage()}.runtime.Context
import ${transformer.jdmnRootPackage()}.runtime.DMNRuntimeException


# Generated(value = {"itemDefinitionInterface.ftl", "${modelRepository.name(itemDefinition)}"})
class ${javaClassName}(${transformer.dmnTypeClassName()}):
    def __init__(self):
        ${transformer.dmnTypeClassName()}.__init__(self)

    <@addAssignmentForFields itemDefinition />

    <@addConvertMethod itemDefinition />

    <@addToContext itemDefinition />

    <@addEqualsAndHashCode itemDefinition />

    <@addToString itemDefinition />
<#macro addAssignmentForFields itemDefinition>
    <#list itemDefinition.itemComponent as child>
        self.${transformer.namedElementVariableName(child)} = None
    </#list>
</#macro>

<#macro addConvertMethod itemDefinition>
    def ${transformer.convertMethodName(itemDefinition)}(self, other: typing.Any) -> typing.Optional['${javaClassName}']:
        if other is None:
            return None
        elif issubclass(type(other), ${javaClassName}):
            return other
        elif isinstance(other, ${transformer.contextClassName()}):
            result_ = ${transformer.defaultConstructor(javaClassName)}
        <#list itemDefinition.itemComponent as child>
            <#assign name = "\"${modelRepository.name(child)}\"" />
            <#assign label = "\"${modelRepository.label(child)}\"" />
            <#if modelRepository.label(child)?has_content>
            result_.${transformer.namedElementVariableName(child)} = other.get(${name}, ${label})
            <#else>
            result_.${transformer.namedElementVariableName(child)} = other.get(${name})
            </#if>
        </#list>
            return result_
        elif isinstance(other, ${transformer.dmnTypeClassName()}):
            return self.${transformer.convertMethodName(itemDefinition)}(other.toContext())
        else:
            raise ${transformer.dmnRuntimeExceptionClassName()}("Cannot convert '{0}' to '{1}'".format(type(other), type(${javaClassName})))
</#macro>

<#macro addToContext itemDefinition >
    def toContext(self) -> ${transformer.contextClassName()}:
        context = ${transformer.defaultConstructor(transformer.contextClassName())}
        <#list itemDefinition.itemComponent as child>
            <#assign name = modelRepository.name(child)/>
            <#assign memberName = transformer.namedElementVariableName(child)/>
        context.put("${memberName}", self.${memberName})
        </#list>
        return context
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    def equalTo(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.namedElementVariableName(child)/>
        if self.${member} != other.${member}:
            return False
        </#list>

        return True

    def hashCode(self):
        result = 0
        <#list modelRepository.sortItemComponent(itemDefinition) as child>
            <#assign member = transformer.namedElementVariableName(child)/>
        result = 31 * result + (0 if self.${member} is None else hash(self.${member}))
        </#list>
        return result
</#macro>

<#macro addToString itemDefinition>
    def asString(self) -> str:
        result_ = "{"
    <#list modelRepository.sortItemComponent(itemDefinition) as child>
        <#assign label = transformer.escapeInString(modelRepository.displayName(child))/>
        <#assign member = transformer.namedElementVariableName(child)/>
        <#if child_index == 0>
        result_ += ("${label}=" + str(self.${member}))
        <#else>
        result_ += (", ${label}=" + str(self.${member}))
        </#if>
    </#list>
        result_ += "}"
        return result_
</#macro>