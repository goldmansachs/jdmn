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

import type_.TApplicantData
import type_.TRequestedProduct

import BureauCallType
import Eligibility

import StrategyRuleOutput


# Generated(value = ["decision.ftl", "Strategy"])
class Strategy(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "Strategy",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE,
        3
    )

    def __init__(self, bureauCallType: BureauCallType.BureauCallType = None, eligibility: Eligibility.Eligibility = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.bureauCallType = BureauCallType.BureauCallType() if bureauCallType is None else bureauCallType
        self.eligibility = Eligibility.Eligibility() if eligibility is None else eligibility

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'Strategy'
            strategyStartTime_ = int(time.time_ns()/1000)
            strategyArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            strategyArguments_.put("ApplicantData", applicantData)
            strategyArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, strategyArguments_)

            # Evaluate decision 'Strategy'
            output_: typing.Optional[str] = self.evaluate(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'Strategy'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, strategyArguments_, output_, (int(time.time_ns()/1000) - strategyStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Strategy' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        bureauCallType: typing.Optional[str] = self.bureauCallType.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)
        eligibility: typing.Optional[str] = self.eligibility.apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(bureauCallType, eligibility, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule1(bureauCallType, eligibility, annotationSet_, eventListener_, externalExecutor_, cache_))
        ruleOutputList_.add(self.rule2(bureauCallType, eligibility, annotationSet_, eventListener_, externalExecutor_, cache_))

        # Return results based on hit policy
        output_: typing.Optional[str]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE)
            output_ = None if ruleOutput_ is None else ruleOutput_.strategy

        return output_

    def rule0(self, bureauCallType: typing.Optional[str], eligibility: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: StrategyRuleOutput.StrategyRuleOutput = StrategyRuleOutput.StrategyRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.stringEqual(eligibility, "INELIGIBLE")),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.strategy = "DECLINE"

            # Add annotation
            annotationSet_.addAnnotation("Strategy", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, bureauCallType: typing.Optional[str], eligibility: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: StrategyRuleOutput.StrategyRuleOutput = StrategyRuleOutput.StrategyRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.stringEqual(eligibility, "ELIGIBLE")),
            self.booleanOr((self.stringEqual(bureauCallType, "FULL")), (self.stringEqual(bureauCallType, "MINI")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.strategy = "BUREAU"

            # Add annotation
            annotationSet_.addAnnotation("Strategy", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, bureauCallType: typing.Optional[str], eligibility: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: StrategyRuleOutput.StrategyRuleOutput = StrategyRuleOutput.StrategyRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.stringEqual(eligibility, "ELIGIBLE")),
            (self.stringEqual(bureauCallType, "NONE"))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.strategy = "THROUGH"

            # Add annotation
            annotationSet_.addAnnotation("Strategy", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_