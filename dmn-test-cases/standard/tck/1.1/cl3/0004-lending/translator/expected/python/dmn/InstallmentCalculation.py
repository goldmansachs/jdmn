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


# Generated(value = ["bkm.ftl", "InstallmentCalculation"])
class InstallmentCalculation(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "InstallmentCalculation",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.CONTEXT,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
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

    def apply(self, productType: typing.Optional[str], rate: typing.Optional[decimal.Decimal], term: typing.Optional[decimal.Decimal], amount: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        try:
            # Start BKM 'InstallmentCalculation'
            installmentCalculationStartTime_ = int(time.time_ns()/1000)
            installmentCalculationArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            installmentCalculationArguments_.put("ProductType", productType)
            installmentCalculationArguments_.put("Rate", rate)
            installmentCalculationArguments_.put("Term", term)
            installmentCalculationArguments_.put("Amount", amount)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, installmentCalculationArguments_)

            # Evaluate BKM 'InstallmentCalculation'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End BKM 'InstallmentCalculation'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, installmentCalculationArguments_, output_, (int(time.time_ns()/1000) - installmentCalculationStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'InstallmentCalculation' evaluation", e)
            return None

    def evaluate(self, productType: typing.Optional[str], rate: typing.Optional[decimal.Decimal], term: typing.Optional[decimal.Decimal], amount: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        monthlyFee: typing.Optional[decimal.Decimal] = (self.number("20.00") if self.booleanEqual(self.stringEqual(productType, "STANDARD LOAN"), True) else (self.number("25.00") if self.booleanEqual(self.stringEqual(productType, "SPECIAL LOAN"), True) else None))
        monthlyRepayment: typing.Optional[decimal.Decimal] = self.numericDivide(self.numericDivide(self.numericMultiply(amount, rate), self.number("12")), self.numericSubtract(self.number("1"), self.numericExponentiation(self.numericAdd(self.number("1"), self.numericDivide(rate, self.number("12"))), self.numericUnaryMinus(term))))
        return self.numericAdd(monthlyRepayment, monthlyFee)
