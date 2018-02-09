<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "${modelRepository.name(drgElement)}"})
public class ${javaClassName} extends ${transformer.abstractRuleOutputClassName()} {
    <#if modelRepository.isDecisionTableExpression(drgElement)>
    <@addPrivateFields drgElement />
    </#if>

    public ${javaClassName}(boolean matched) {
        super(matched);
    }

    <#if modelRepository.isDecisionTableExpression(drgElement)>
    <@addAccessors drgElement />

    <@addEqualsAndHashCode drgElement />

    <@addToString drgElement />
    <@addSortMethod drgElement />
    </#if>
}
<#macro addPrivateFields drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.output as output>
    private ${transformer.outputClauseClassName(drgElement, output)} ${transformer.outputClauseVariableName(drgElement, output)};
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
    private Integer ${transformer.outputClausePriorityVariableName(drgElement, output)};
    </#if>
    </#list>
</#macro>

<#macro addAccessors drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.output as output>
    public ${transformer.outputClauseClassName(drgElement, output)} ${transformer.getter(drgElement, output)} {
        return this.${transformer.outputClauseVariableName(drgElement, output)};
    }
    public void ${transformer.setter(drgElement, output)}(${transformer.outputClauseClassName(drgElement, output)} ${transformer.outputClauseVariableName(drgElement, output)}) {
        this.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputClauseVariableName(drgElement, output)};
    }
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>

    public Integer ${transformer.priorityGetter(drgElement, output)} {
        return this.${transformer.outputClausePriorityVariableName(drgElement, output)};
    }
    public void ${transformer.prioritySetter(drgElement, output)}(Integer ${transformer.outputClausePriorityVariableName(drgElement, output)}) {
        this.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClausePriorityVariableName(drgElement, output)};
    }
    </#if>
    </#list>
</#macro>

<#macro addEqualsAndHashCode itemDefinition >
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ${javaClassName} other = (${javaClassName}) o;
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.getter(drgElement, output)/>
        if (this.${member} != null ? !this.${member}.equals(other.${member}) : other.${member} != null) return false;
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.priorityGetter(drgElement, output)/>
        if (this.${member} != null ? !this.${member}.equals(other.${member}) : other.${member} != null) return false;
            </#if>
        </#list>

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
            <#assign member = transformer.getter(drgElement, output)/>
        result = 31 * result + (this.${member} != null ? this.${member}.hashCode() : 0);
            <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>
                <#assign member = transformer.priorityGetter(drgElement, output)/>
        result = 31 * result + (this.${member} != null ? this.${member}.hashCode() : 0);
            </#if>
        </#list>

        return result;
    }
</#macro>

<#macro addSortMethod drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#if modelRepository.isOutputOrderHit(expression.hitPolicy)>

    @Override
    public List<${transformer.abstractRuleOutputClassName()}> sort(List<${transformer.abstractRuleOutputClassName()}> matchedResults_) {
    <#list expression.output as output>
        List<${transformer.pairClassName()}<${transformer.outputClauseClassName(drgElement, output)}, Integer>> ${transformer.outputClauseVariableName(drgElement, output)}Pairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            ${transformer.outputClauseVariableName(drgElement, output)}Pairs.add(new ${transformer.pairClassName()}(((${javaClassName})matchedResult_).${transformer.getter(drgElement, output)}, ((${javaClassName})matchedResult_).${transformer.priorityGetter(drgElement, output)}));
        });
        ${transformer.outputClauseVariableName(drgElement, output)}Pairs.sort(new ${transformer.pairComparatorClassName()}());
    </#list>

        List<${transformer.abstractRuleOutputClassName()}> result_ = new ArrayList<${transformer.abstractRuleOutputClassName()}>();
        for(int i=0; i<matchedResults_.size(); i++) {
            ${javaClassName} output_ = new ${javaClassName}(true);
            <#list expression.output as output>
            output_.${transformer.setter(drgElement, output)}(${transformer.outputClauseVariableName(drgElement, output)}Pairs.get(i).getLeft());
            output_.${transformer.prioritySetter(drgElement, output)}(${transformer.outputClauseVariableName(drgElement, output)}Pairs.get(i).getRight());
            </#list>
            result_.add(output_);
        }
        return result_;
    }
    </#if>
</#macro>

<#macro addToString drgElement>
    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        <#list expression.output as output>
        <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result_.append(String.format(", ${member}='%s'", ${member}));
        </#list>
        result_.append(")");
        return result_.toString();
    }
</#macro>
