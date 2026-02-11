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

import PreBureauRiskCategory
import RequiredMonthlyInstallment

import AffordabilityCalculation


# Generated(value = ["decision.ftl", "Pre-bureauAffordability"])
class PreBureauAffordability(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b",
        "Pre-bureauAffordability",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, preBureauRiskCategory: PreBureauRiskCategory.PreBureauRiskCategory = None, requiredMonthlyInstallment: RequiredMonthlyInstallment.RequiredMonthlyInstallment = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.preBureauRiskCategory = PreBureauRiskCategory.PreBureauRiskCategory() if preBureauRiskCategory is None else preBureauRiskCategory
        self.requiredMonthlyInstallment = RequiredMonthlyInstallment.RequiredMonthlyInstallment() if requiredMonthlyInstallment is None else requiredMonthlyInstallment

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        try:
            # Start decision 'Pre-bureauAffordability'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            preBureauAffordabilityStartTime_ = int(time.time_ns()/1000)
            preBureauAffordabilityArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            preBureauAffordabilityArguments_.put("ApplicantData", applicantData)
            preBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_)

            # Evaluate decision 'Pre-bureauAffordability'
            output_: typing.Optional[bool] = self.evaluate(applicantData, requestedProduct, context_)

            # End decision 'Pre-bureauAffordability'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_, output_, (int(time.time_ns()/1000) - preBureauAffordabilityStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Pre-bureauAffordability' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        # Apply child decisions
        preBureauRiskCategory: typing.Optional[str] = self.preBureauRiskCategory.apply(applicantData, context_)
        requiredMonthlyInstallment: typing.Optional[decimal.Decimal] = self.requiredMonthlyInstallment.apply(requestedProduct, context_)

        return AffordabilityCalculation.AffordabilityCalculation.instance().apply(None if (None if (applicantData is None) else (applicantData.monthly) is None) else (None if (applicantData is None) else (applicantData.monthly).income), None if (None if (applicantData is None) else (applicantData.monthly) is None) else (None if (applicantData is None) else (applicantData.monthly).repayments), None if (None if (applicantData is None) else (applicantData.monthly) is None) else (None if (applicantData is None) else (applicantData.monthly).expenses), preBureauRiskCategory, requiredMonthlyInstallment, context_)
