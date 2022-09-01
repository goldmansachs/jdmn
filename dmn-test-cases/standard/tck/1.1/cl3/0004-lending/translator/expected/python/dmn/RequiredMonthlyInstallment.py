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

import type_.TRequestedProduct

import InstallmentCalculation


# Generated(value = ["decision.ftl", "RequiredMonthlyInstallment"])
class RequiredMonthlyInstallment(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "RequiredMonthlyInstallment",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision 'RequiredMonthlyInstallment'
            requiredMonthlyInstallmentStartTime_ = int(time.time_ns()/1000)
            requiredMonthlyInstallmentArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            requiredMonthlyInstallmentArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_)

            # Evaluate decision 'RequiredMonthlyInstallment'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'RequiredMonthlyInstallment'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (int(time.time_ns()/1000) - requiredMonthlyInstallmentStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'RequiredMonthlyInstallment' evaluation", e)
            return None

    def evaluate(self, requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        return InstallmentCalculation.InstallmentCalculation.instance().apply((None if requestedProduct is None else requestedProduct.productType), (None if requestedProduct is None else requestedProduct.rate), (None if requestedProduct is None else requestedProduct.term), (None if requestedProduct is None else requestedProduct.amount), annotationSet_, eventListener_, externalExecutor_, cache_)
