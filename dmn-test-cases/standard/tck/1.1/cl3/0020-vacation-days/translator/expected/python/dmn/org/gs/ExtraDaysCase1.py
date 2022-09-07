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

import org.gs.ExtraDaysCase1RuleOutput


# Generated(value = ["decision.ftl", "'Extra days case 1'"])
class ExtraDaysCase1(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "org.gs",
        "'Extra days case 1'",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT,
        2
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision ''Extra days case 1''
            extraDaysCase1StartTime_ = int(time.time_ns()/1000)
            extraDaysCase1Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            extraDaysCase1Arguments_.put("Age", age)
            extraDaysCase1Arguments_.put("'Years of Service'", yearsOfService)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, extraDaysCase1Arguments_)

            # Evaluate decision ''Extra days case 1''
            output_: typing.Optional[decimal.Decimal] = self.evaluate(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision ''Extra days case 1''
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, extraDaysCase1Arguments_, output_, (int(time.time_ns()/1000) - extraDaysCase1StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in ''Extra days case 1'' evaluation", e)
            return None

    def evaluate(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule1(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

        # Return results based on hit policy
        output_: typing.Optional[decimal.Decimal]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = self.number("0")
        else:
            ruleOutputs_: typing.List[jdmn.runtime.RuleOutput.RuleOutput] = ruleOutputList_.applyMultiple(jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT)
            output_ = self.max(list(map(lambda o: o.extraDaysCase1, ruleOutputs_)))

        return output_

    def rule0(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: org.gs.ExtraDaysCase1RuleOutput.ExtraDaysCase1RuleOutput = org.gs.ExtraDaysCase1RuleOutput.ExtraDaysCase1RuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanOr((self.numericLessThan(age, self.number("18"))), (self.numericGreaterEqualThan(age, self.number("60")))),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.extraDaysCase1 = self.number("5")

            # Add annotation
            annotationSet_.addAnnotation("'Extra days case 1'", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: org.gs.ExtraDaysCase1RuleOutput.ExtraDaysCase1RuleOutput = org.gs.ExtraDaysCase1RuleOutput.ExtraDaysCase1RuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            (self.numericGreaterEqualThan(yearsOfService, self.number("30")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.extraDaysCase1 = self.number("5")

            # Add annotation
            annotationSet_.addAnnotation("'Extra days case 1'", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
