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

import CreditContingencyFactorTable


# Generated(value = {"bkm.ftl", "AffordabilityCalculation"})
class AffordabilityCalculation(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "AffordabilityCalculation",
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

    def apply(self, monthlyIncome: typing.Optional[decimal.Decimal], monthlyRepayments: typing.Optional[decimal.Decimal], monthlyExpenses: typing.Optional[decimal.Decimal], riskCategory: typing.Optional[str], requiredMonthlyInstallment: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[bool]:
        try:
            # Start BKM 'AffordabilityCalculation'
            affordabilityCalculationStartTime_ = int(time.time_ns()/1000)
            affordabilityCalculationArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            affordabilityCalculationArguments_.put("MonthlyIncome", monthlyIncome)
            affordabilityCalculationArguments_.put("MonthlyRepayments", monthlyRepayments)
            affordabilityCalculationArguments_.put("MonthlyExpenses", monthlyExpenses)
            affordabilityCalculationArguments_.put("RiskCategory", riskCategory)
            affordabilityCalculationArguments_.put("RequiredMonthlyInstallment", requiredMonthlyInstallment)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, affordabilityCalculationArguments_)

            # Evaluate BKM 'AffordabilityCalculation'
            output_: typing.Optional[bool] = self.evaluate(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End BKM 'AffordabilityCalculation'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, affordabilityCalculationArguments_, output_, (int(time.time_ns()/1000) - affordabilityCalculationStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'AffordabilityCalculation' evaluation", e)
            return None

    def evaluate(self, monthlyIncome: typing.Optional[decimal.Decimal], monthlyRepayments: typing.Optional[decimal.Decimal], monthlyExpenses: typing.Optional[decimal.Decimal], riskCategory: typing.Optional[str], requiredMonthlyInstallment: typing.Optional[decimal.Decimal], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[bool]:
        disposableIncome: typing.Optional[decimal.Decimal] = self.numericSubtract(monthlyIncome, self.numericAdd(monthlyExpenses, monthlyRepayments))
        creditContingencyFactor: typing.Optional[decimal.Decimal] = CreditContingencyFactorTable.CreditContingencyFactorTable.instance().apply(riskCategory, annotationSet_, eventListener_, externalExecutor_, cache_)
        affordability: typing.Optional[bool] = (True if self.booleanEqual(self.numericGreaterThan(self.numericMultiply(disposableIncome, creditContingencyFactor), requiredMonthlyInstallment), True) else False)
        return affordability
