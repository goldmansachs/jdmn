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

import RoutingRulesRuleOutput


# Generated(value = ["bkm.ftl", "RoutingRules"])
class RoutingRules(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "RoutingRules",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.PRIORITY,
        5
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

    def apply(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start BKM 'RoutingRules'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            routingRulesStartTime_ = int(time.time_ns()/1000)
            routingRulesArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            routingRulesArguments_.put("Post-bureauRiskCategory", postBureauRiskCategory)
            routingRulesArguments_.put("Post-bureauAffordability", postBureauAffordability)
            routingRulesArguments_.put("Bankrupt", bankrupt)
            routingRulesArguments_.put("CreditScore", creditScore)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, routingRulesArguments_)

            # Evaluate BKM 'RoutingRules'
            output_: typing.Optional[str] = self.evaluate(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_)

            # End BKM 'RoutingRules'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, routingRulesArguments_, output_, (int(time.time_ns()/1000) - routingRulesStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'RoutingRules' evaluation", e)
            return None

    def evaluate(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(self.rule1(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(self.rule2(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(self.rule3(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(self.rule4(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))

        # Return results based on hit policy
        output_: typing.Optional[str]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.PRIORITY)
            output_ = None if ruleOutput_ is None else ruleOutput_.routingRules

        return output_

    def rule0(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: RoutingRulesRuleOutput.RoutingRulesRuleOutput = RoutingRulesRuleOutput.RoutingRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            self.booleanEqual(postBureauAffordability, False),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.routingRules = "DECLINE"
            output_.routingRulesPriority = 3

            # Add annotation
            annotationSet_.addAnnotation("RoutingRules", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: RoutingRulesRuleOutput.RoutingRulesRuleOutput = RoutingRulesRuleOutput.RoutingRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            self.booleanEqual(bankrupt, True),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.routingRules = "DECLINE"
            output_.routingRulesPriority = 3

            # Add annotation
            annotationSet_.addAnnotation("RoutingRules", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: RoutingRulesRuleOutput.RoutingRulesRuleOutput = RoutingRulesRuleOutput.RoutingRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.stringEqual(postBureauRiskCategory, "HIGH"),
            True,
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.routingRules = "REFER"
            output_.routingRulesPriority = 2

            # Add annotation
            annotationSet_.addAnnotation("RoutingRules", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule3(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(3, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: RoutingRulesRuleOutput.RoutingRulesRuleOutput = RoutingRulesRuleOutput.RoutingRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            True,
            self.numericLessThan(creditScore, self.number("580"))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.routingRules = "REFER"
            output_.routingRulesPriority = 2

            # Add annotation
            annotationSet_.addAnnotation("RoutingRules", 3, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule4(self, postBureauRiskCategory: typing.Optional[str], postBureauAffordability: typing.Optional[bool], bankrupt: typing.Optional[bool], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(4, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: RoutingRulesRuleOutput.RoutingRulesRuleOutput = RoutingRulesRuleOutput.RoutingRulesRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.routingRules = "ACCEPT"
            output_.routingRulesPriority = 1

            # Add annotation
            annotationSet_.addAnnotation("RoutingRules", 4, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
