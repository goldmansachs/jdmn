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

import PostBureauAffordability
import PostBureauRiskCategory

import RoutingRules


# Generated(value = ["decision.ftl", "Routing"])
class Routing(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "Routing",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, postBureauAffordability: PostBureauAffordability.PostBureauAffordability = None, postBureauRiskCategory: PostBureauRiskCategory.PostBureauRiskCategory = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.postBureauAffordability = PostBureauAffordability.PostBureauAffordability() if postBureauAffordability is None else postBureauAffordability
        self.postBureauRiskCategory = PostBureauRiskCategory.PostBureauRiskCategory() if postBureauRiskCategory is None else postBureauRiskCategory

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start decision 'Routing'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            routingStartTime_ = int(time.time_ns()/1000)
            routingArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            routingArguments_.put("ApplicantData", applicantData)
            routingArguments_.put("BureauData", bureauData)
            routingArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, routingArguments_)

            # Evaluate decision 'Routing'
            output_: typing.Optional[str] = self.evaluate(applicantData, bureauData, requestedProduct, context_)

            # End decision 'Routing'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, routingArguments_, output_, (int(time.time_ns()/1000) - routingStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Routing' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        # Apply child decisions
        postBureauAffordability: typing.Optional[bool] = self.postBureauAffordability.apply(applicantData, bureauData, requestedProduct, context_)
        postBureauRiskCategory: typing.Optional[str] = self.postBureauRiskCategory.apply(applicantData, bureauData, context_)

        return RoutingRules.RoutingRules.instance().apply(postBureauRiskCategory, postBureauAffordability, None if (bureauData is None) else (bureauData.bankrupt), None if (bureauData is None) else (bureauData.creditScore), context_)
