import typing
import decimal
import datetime
import isodate
import time

import jdmn.runtime.DefaultDMNBaseDecision
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

import EligibilityRulesRuleOutput


# Generated(value = {"bkm.ftl", "EligibilityRules"})
class EligibilityRules(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "EligibilityRules",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.PRIORITY,
        4
    )
    _instance = None

    def __init__(self):
        raise RuntimeError("Call instance() instead")

    @classmethod
    def instance(cls):
        if cls._instance is None:
            cls._instance = cls.__new__(cls)
            jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(cls._instance)
        return cls._instance

    def apply(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start BKM 'EligibilityRules'
            eligibilityRulesStartTime_ = int(time.time_ns()/1000)
            eligibilityRulesArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eligibilityRulesArguments_.put("'Pre-bureauRiskCategory'", preBureauRiskCategory)
            eligibilityRulesArguments_.put("'Pre-bureauAffordability'", preBureauAffordability)
            eligibilityRulesArguments_.put("Age", age)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, eligibilityRulesArguments_)

            # Evaluate BKM 'EligibilityRules'
            output_: typing.Optional[str] = self.evaluate(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End BKM 'EligibilityRules'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, eligibilityRulesArguments_, output_, (int(time.time_ns()/1000) - eligibilityRulesStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'EligibilityRules' evaluation", e)
            return None

    def evaluate(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule1(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule2(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule3(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_))

        # Return results based on hit policy
        output_: typing.Optional[str]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.PRIORITY)
            output_ = None if ruleOutput_ is None else ruleOutput_.eligibilityRules

        return output_

    def rule0(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: EligibilityRulesRuleOutput.EligibilityRulesRuleOutput = EligibilityRulesRuleOutput.EligibilityRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.stringEqual(preBureauRiskCategory, "DECLINE")),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            # Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: EligibilityRulesRuleOutput.EligibilityRulesRuleOutput = EligibilityRulesRuleOutput.EligibilityRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            (self.booleanEqual(preBureauAffordability, False)),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            # Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: EligibilityRulesRuleOutput.EligibilityRulesRuleOutput = EligibilityRulesRuleOutput.EligibilityRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            (self.numericLessThan(age, self.number("18")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            # Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule3(self, preBureauRiskCategory: typing.Optional[str], preBureauAffordability: typing.Optional[bool], age: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(3, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: EligibilityRulesRuleOutput.EligibilityRulesRuleOutput = EligibilityRulesRuleOutput.EligibilityRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.eligibilityRules = "ELIGIBLE"
            output_.eligibilityRulesPriority = 1

            # Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 3, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
