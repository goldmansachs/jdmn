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

@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "${modelRepository.name(drgElement)}"])
class ${javaClassName}(matched: Boolean) : ${transformer.abstractRuleOutputClassName()}(matched) {
    <#if modelRepository.isDecisionTableExpression(drgElement)>
    <@addPrivateFields drgElement />
    </#if>

    <#if modelRepository.isDecisionTableExpression(drgElement)>
    <@addEqualsAndHashCode drgElement />

    <@addToString drgElement />
    <@addSortMethod drgElement />
    </#if>
}
<#macro addPrivateFields drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.output as output>
    @com.fasterxml.jackson.annotation.JsonProperty("${transformer.escapeInString(transformer.outputClauseName(drgElement, output))}")
    var ${transformer.outputClauseVariableName(drgElement, output)}: ${transformer.outputClauseClassName(drgElement, output, output?index)}? = null
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
    var ${transformer.outputClausePriorityVariableName(drgElement, output)}: Int? = 0
    </#if>
    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ${javaClassName}
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        if (if (this.${member} != null) this.${member} != other.${member} else other.${member} != null) return false
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.outputClausePriorityVariableName(drgElement, output)/>
        if (if (this.${member} != null) this.${member} != other.${member} else other.${member} != null) return false
            </#if>
        </#list>

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result = 31 * result + (if (this.${member} != null) this.${member}.hashCode() else 0)
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.outputClausePriorityVariableName(drgElement, output)/>
        result = 31 * result + (if (this.${member} != null) this.${member}.hashCode() else 0)
            </#if>
        </#list>

        return result
    }
</#macro>

<#macro addSortMethod drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>

    override fun sort(matchedResults_: MutableList<${transformer.abstractRuleOutputClassName()}>): MutableList<${transformer.abstractRuleOutputClassName()}> {
    <#list expression.output as output>
        val ${transformer.outputClauseVariableName(drgElement, output)}Pairs: MutableList<${transformer.pairClassName()}<${transformer.outputClauseClassName(drgElement, output, output?index)}?, Int?>> = ArrayList()
        matchedResults_.forEach({ (it as ${javaClassName})
            ${transformer.outputClauseVariableName(drgElement, output)}Pairs.add(${transformer.pairClassName()}(it.${transformer.outputClauseVariableName(drgElement, output)}, it.${transformer.outputClausePriorityVariableName(drgElement, output)}))
        })
        ${transformer.outputClauseVariableName(drgElement, output)}Pairs.sortWith(${transformer.pairComparatorClassName()}())
    </#list>

        val result_: MutableList<${transformer.abstractRuleOutputClassName()}> = ArrayList<${transformer.abstractRuleOutputClassName()}>()
        for(i in 0 until matchedResults_.size) {
            var output_ = ${javaClassName}(true)
            <#list expression.output as output>
            output_.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputClauseVariableName(drgElement, output)}Pairs.get(i).getLeft()
            output_.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClauseVariableName(drgElement, output)}Pairs.get(i).getRight()
            </#list>
            result_.add(output_)
        }
        return result_
    }
    </#if>
</#macro>

<#macro addToString drgElement>
    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        <#list expression.output as output>
        <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result_.append(String.format(", ${member}='%s'", ${member}))
        </#list>
        result_.append(")")
        return result_.toString()
    }
</#macro>
