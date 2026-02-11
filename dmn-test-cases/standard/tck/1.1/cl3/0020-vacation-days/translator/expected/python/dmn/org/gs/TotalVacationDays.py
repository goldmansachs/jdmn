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

import org.gs.BaseVacationDays
import org.gs.ExtraDaysCase1
import org.gs.ExtraDaysCase2
import org.gs.ExtraDaysCase3


# Generated(value = ["decision.ftl", "Total Vacation Days"])
class TotalVacationDays(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "https://www.drools.org/kie-dmn",
        "Total Vacation Days",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, baseVacationDays: org.gs.BaseVacationDays.BaseVacationDays = None, extraDaysCase1: org.gs.ExtraDaysCase1.ExtraDaysCase1 = None, extraDaysCase2: org.gs.ExtraDaysCase2.ExtraDaysCase2 = None, extraDaysCase3: org.gs.ExtraDaysCase3.ExtraDaysCase3 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.baseVacationDays = org.gs.BaseVacationDays.BaseVacationDays() if baseVacationDays is None else baseVacationDays
        self.extraDaysCase1 = org.gs.ExtraDaysCase1.ExtraDaysCase1() if extraDaysCase1 is None else extraDaysCase1
        self.extraDaysCase2 = org.gs.ExtraDaysCase2.ExtraDaysCase2() if extraDaysCase2 is None else extraDaysCase2
        self.extraDaysCase3 = org.gs.ExtraDaysCase3.ExtraDaysCase3() if extraDaysCase3 is None else extraDaysCase3

    def apply(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision 'Total Vacation Days'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            totalVacationDaysStartTime_ = int(time.time_ns()/1000)
            totalVacationDaysArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            totalVacationDaysArguments_.put("Age", age)
            totalVacationDaysArguments_.put("Years of Service", yearsOfService)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, totalVacationDaysArguments_)

            # Evaluate decision 'Total Vacation Days'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(age, yearsOfService, context_)

            # End decision 'Total Vacation Days'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, totalVacationDaysArguments_, output_, (int(time.time_ns()/1000) - totalVacationDaysStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Total Vacation Days' evaluation", e)
            return None

    def evaluate(self, age: typing.Optional[decimal.Decimal], yearsOfService: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        # Apply child decisions
        baseVacationDays: typing.Optional[decimal.Decimal] = self.baseVacationDays.apply(context_)
        extraDaysCase1: typing.Optional[decimal.Decimal] = self.extraDaysCase1.apply(age, yearsOfService, context_)
        extraDaysCase2: typing.Optional[decimal.Decimal] = self.extraDaysCase2.apply(age, yearsOfService, context_)
        extraDaysCase3: typing.Optional[decimal.Decimal] = self.extraDaysCase3.apply(age, yearsOfService, context_)

        return self.numericAdd(self.numericAdd(baseVacationDays, self.max(extraDaysCase1, extraDaysCase3)), extraDaysCase2)
