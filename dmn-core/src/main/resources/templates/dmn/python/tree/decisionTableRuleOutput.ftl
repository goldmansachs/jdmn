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
import functools

import ${transformer.jdmnRootPackage()}.runtime.RuleOutput
import ${transformer.jdmnRootPackage()}.runtime.Pair
import ${transformer.jdmnRootPackage()}.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "${modelRepository.name(drgElement)}"])
class ${javaClassName}(${transformer.abstractRuleOutputClassName()}):
    def __init__(self, matched: bool):
        super().__init__(matched)
        <#if modelRepository.isDecisionTableExpression(drgElement)>
        <@addPrivateFields drgElement />
        </#if>

    <#if modelRepository.isDecisionTableExpression(drgElement)>
    <@addEqualsAndHashCode drgElement />

    <@addToString drgElement />
    <@addSortMethod drgElement />
    </#if>
<#macro addPrivateFields drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.output as output>
        self.${transformer.outputClauseVariableName(drgElement, output)}: ${transformer.nullableType(transformer.outputClauseClassName(drgElement, output, output?index))} = None
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
        self.${transformer.outputClausePriorityVariableName(drgElement, output)}: int = 0
    </#if>
    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        if self.${member} != other.${member}:
            return False
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.outputClausePriorityVariableName(drgElement, output)/>
        if self.${member} != other.${member}:
            return False
            </#if>
        </#list>

        return True

    def __hash__(self):
        result = 0
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result = 31 * result + (0 if self.${member} is None else hash(self.${member}))
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.outputClausePriorityVariableName(drgElement, output)/>
        result = 31 * result + (0 if self.${member} is None else hash(self.${member}))
            </#if>
        </#list>

        return result
</#macro>

<#macro addSortMethod drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>

    def sort(self, matchedResults_: typing.List[${transformer.abstractRuleOutputClassName()}]) -> typing.List[${transformer.abstractRuleOutputClassName()}]:
    <#list expression.output as output>
        ${transformer.outputClauseVariableName(drgElement, output)}Pairs = []
        for it in matchedResults_:
            ${transformer.outputClauseVariableName(drgElement, output)}Pairs.append(${transformer.pairClassName()}(it.${transformer.outputClauseVariableName(drgElement, output)}, it.${transformer.outputClausePriorityVariableName(drgElement, output)}))
        ${transformer.outputClauseVariableName(drgElement, output)}Pairs.sort(key=functools.cmp_to_key(${transformer.pairComparatorClassName()}().compare))
    </#list>

        result_: typing.List[${transformer.abstractRuleOutputClassName()}] = []
        for i, _ in enumerate(matchedResults_):
            output_ = ${javaClassName}(True)
            <#list expression.output as output>
            output_.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputClauseVariableName(drgElement, output)}Pairs[i].getLeft()
            output_.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClauseVariableName(drgElement, output)}Pairs[i].getRight()
            </#list>
            result_.append(output_)
        return result_
    </#if>
</#macro>

<#macro addToString drgElement>
    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        <#list expression.output as output>
        <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result_ += ", ${member}='{0}'".format(self.${member})
        </#list>
        result_ += ")"
        return result_
</#macro>
