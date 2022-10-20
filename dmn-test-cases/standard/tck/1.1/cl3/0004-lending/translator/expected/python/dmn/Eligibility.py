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

import type_.Monthly
import type_.MonthlyImpl
import type_.TApplicantData
import type_.TApplicantDataImpl
import type_.TRequestedProduct
import type_.TRequestedProductImpl

import PreBureauAffordability
import PreBureauRiskCategory

import EligibilityRules


# Generated(value = ["decision.ftl", "Eligibility"])
class Eligibility(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "Eligibility",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, preBureauAffordability: PreBureauAffordability.PreBureauAffordability = None, preBureauRiskCategory: PreBureauRiskCategory.PreBureauRiskCategory = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.preBureauAffordability = PreBureauAffordability.PreBureauAffordability() if preBureauAffordability is None else preBureauAffordability
        self.preBureauRiskCategory = PreBureauRiskCategory.PreBureauRiskCategory() if preBureauRiskCategory is None else preBureauRiskCategory

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'Eligibility'
            eligibilityStartTime_ = int(time.time_ns()/1000)
            eligibilityArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eligibilityArguments_.put("ApplicantData", applicantData)
            eligibilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, eligibilityArguments_)

            # Evaluate decision 'Eligibility'
            output_: typing.Optional[str] = self.evaluate(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'Eligibility'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, eligibilityArguments_, output_, (int(time.time_ns()/1000) - eligibilityStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Eligibility' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        preBureauAffordability: typing.Optional[bool] = self.preBureauAffordability.apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)
        preBureauRiskCategory: typing.Optional[str] = self.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

        return EligibilityRules.EligibilityRules.instance().apply(preBureauRiskCategory, preBureauAffordability, None if (applicantData is None) else (applicantData.age), annotationSet_, eventListener_, externalExecutor_, cache_)
