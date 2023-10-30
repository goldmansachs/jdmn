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

import CreditContingencyFactorTableRuleOutput


# Generated(value = ["bkm.ftl", "CreditContingencyFactorTable"])
class CreditContingencyFactorTable(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "CreditContingencyFactorTable",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE,
        3
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

    def apply(self, riskCategory: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        try:
            # Start BKM 'CreditContingencyFactorTable'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            creditContingencyFactorTableStartTime_ = int(time.time_ns()/1000)
            creditContingencyFactorTableArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            creditContingencyFactorTableArguments_.put("RiskCategory", riskCategory)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, creditContingencyFactorTableArguments_)

            # Evaluate BKM 'CreditContingencyFactorTable'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(riskCategory, context_)

            # End BKM 'CreditContingencyFactorTable'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, creditContingencyFactorTableArguments_, output_, (int(time.time_ns()/1000) - creditContingencyFactorTableStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'CreditContingencyFactorTable' evaluation", e)
            return None

    def evaluate(self, riskCategory: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(riskCategory, context_))
        ruleOutputList_.add(self.rule1(riskCategory, context_))
        ruleOutputList_.add(self.rule2(riskCategory, context_))

        # Return results based on hit policy
        output_: typing.Optional[decimal.Decimal]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE)
            output_ = None if ruleOutput_ is None else ruleOutput_.creditContingencyFactorTable

        return output_

    def rule0(self, riskCategory: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanOr(self.stringEqual(riskCategory, "HIGH"), self.stringEqual(riskCategory, "DECLINE"))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.creditContingencyFactorTable = self.number("0.6")

            # Add annotation
            annotationSet_.addAnnotation("CreditContingencyFactorTable", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, riskCategory: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.stringEqual(riskCategory, "MEDIUM")
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.creditContingencyFactorTable = self.number("0.7")

            # Add annotation
            annotationSet_.addAnnotation("CreditContingencyFactorTable", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, riskCategory: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput.CreditContingencyFactorTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanOr(self.stringEqual(riskCategory, "LOW"), self.stringEqual(riskCategory, "VERY LOW"))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.creditContingencyFactorTable = self.number("0.8")

            # Add annotation
            annotationSet_.addAnnotation("CreditContingencyFactorTable", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
