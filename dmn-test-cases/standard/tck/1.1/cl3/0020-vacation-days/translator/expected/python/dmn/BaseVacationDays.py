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


# Generated(value = ["decision.ftl", "'Base Vacation Days'"])
class BaseVacationDays(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "'Base Vacation Days'",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision ''Base Vacation Days''
            baseVacationDaysStartTime_ = int(time.time_ns()/1000)
            baseVacationDaysArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, baseVacationDaysArguments_)

            # Evaluate decision ''Base Vacation Days''
            output_: typing.Optional[decimal.Decimal] = self.evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision ''Base Vacation Days''
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, baseVacationDaysArguments_, output_, (int(time.time_ns()/1000) - baseVacationDaysStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in ''Base Vacation Days'' evaluation", e)
            return None

    def evaluate(self, annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        return self.number("22")
