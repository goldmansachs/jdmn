import typing
import decimal
import datetime
import isodate
import time

import jdmn.runtime.Context
import jdmn.runtime.DefaultDMNBaseDecision
import jdmn.runtime.ExecutionContext
import jdmn.runtime.LambdaExpression
import jdmn.runtime.LazyEval
import jdmn.runtime.Pair
import jdmn.runtime.Range
import jdmn.runtime.RuleOutput
import jdmn.runtime.RuleOutputList

import jdmn.runtime.annotation.Annotation
import jdmn.runtime.annotation.AnnotationSet
import jdmn.runtime.annotation.DRGElementKind
import jdmn.runtime.annotation.ExpressionKind
import jdmn.runtime.annotation.HitPolicy

import jdmn.runtime.cache.Cache

import jdmn.runtime.external.ExternalFunctionExecutor

import jdmn.runtime.listener.Arguments
import jdmn.runtime.listener.DRGElement
import jdmn.runtime.listener.EventListener
import jdmn.runtime.listener.Rule

import org.gs.ExtraDaysCase2RuleOutput


# Generated(value = ["decision.ftl", "Extra days case 2"])
class ExtraDaysCase2(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "org.gs",
        "Extra days case 2",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT,
        2
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision 'Extra days case 2'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            extraDaysCase2StartTime_ = int(time.time_ns()/1000)
            extraDaysCase2Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            extraDaysCase2Arguments_.put("Age", age)
            extraDaysCase2Arguments_.put("Years of Service", yearsOfService)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, extraDaysCase2Arguments_)

            # Evaluate decision 'Extra days case 2'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(age, yearsOfService, context_)

            # End decision 'Extra days case 2'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, extraDaysCase2Arguments_, output_, (int(time.time_ns()/1000) - extraDaysCase2StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Extra days case 2' evaluation", e)
            return None

    def evaluate(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(age, yearsOfService, context_))
        ruleOutputList_.add(self.rule1(age, yearsOfService, context_))

        # Return results based on hit policy
        output_: typing.Optional[decimal.Decimal]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = self.number("0")
        else:
            ruleOutputs_: typing.List[jdmn.runtime.RuleOutput.RuleOutput] = ruleOutputList_.applyMultiple(jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT)
            output_ = self.max(list(map(lambda o: o.extraDaysCase2, ruleOutputs_)))

        return output_

    def rule0(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: org.gs.ExtraDaysCase2RuleOutput.ExtraDaysCase2RuleOutput = org.gs.ExtraDaysCase2RuleOutput.ExtraDaysCase2RuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            self.numericGreaterEqualThan(yearsOfService, self.number("30"))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.extraDaysCase2 = self.number("3")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: org.gs.ExtraDaysCase2RuleOutput.ExtraDaysCase2RuleOutput = org.gs.ExtraDaysCase2RuleOutput.ExtraDaysCase2RuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.numericGreaterEqualThan(age, self.number("60")),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.extraDaysCase2 = self.number("3")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
