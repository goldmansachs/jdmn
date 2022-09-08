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
import type_.TBureauData
import type_.TBureauDataImpl
import type_.TRequestedProduct
import type_.TRequestedProductImpl

import PostBureauRiskCategory
import RequiredMonthlyInstallment

import AffordabilityCalculation


# Generated(value = ["decision.ftl", "'Post-bureauAffordability'"])
class PostBureauAffordability(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "'Post-bureauAffordability'",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, postBureauRiskCategory: PostBureauRiskCategory.PostBureauRiskCategory = None, requiredMonthlyInstallment: RequiredMonthlyInstallment.RequiredMonthlyInstallment = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.postBureauRiskCategory = PostBureauRiskCategory.PostBureauRiskCategory() if postBureauRiskCategory is None else postBureauRiskCategory
        self.requiredMonthlyInstallment = RequiredMonthlyInstallment.RequiredMonthlyInstallment() if requiredMonthlyInstallment is None else requiredMonthlyInstallment

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[bool]:
        try:
            # Start decision ''Post-bureauAffordability''
            postBureauAffordabilityStartTime_ = int(time.time_ns()/1000)
            postBureauAffordabilityArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            postBureauAffordabilityArguments_.put("ApplicantData", applicantData)
            postBureauAffordabilityArguments_.put("BureauData", bureauData)
            postBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_)

            # Evaluate decision ''Post-bureauAffordability''
            output_: typing.Optional[bool] = self.evaluate(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision ''Post-bureauAffordability''
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (int(time.time_ns()/1000) - postBureauAffordabilityStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in ''Post-bureauAffordability'' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[bool]:
        # Apply child decisions
        postBureauRiskCategory: typing.Optional[str] = self.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_)
        requiredMonthlyInstallment: typing.Optional[decimal.Decimal] = self.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

        return AffordabilityCalculation.AffordabilityCalculation.instance().apply((None if (None if applicantData is None else applicantData.monthly) is None else (None if applicantData is None else applicantData.monthly).income), (None if (None if applicantData is None else applicantData.monthly) is None else (None if applicantData is None else applicantData.monthly).repayments), (None if (None if applicantData is None else applicantData.monthly) is None else (None if applicantData is None else applicantData.monthly).expenses), postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_)
