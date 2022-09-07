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

import Decision_014_3


# Generated(value = ["decision.ftl", "decision_014_2"])
class Decision_014_2(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decision_014_2",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, decision_014_3: Decision_014_3.Decision_014_3 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.decision_014_3 = Decision_014_3.Decision_014_3() if decision_014_3 is None else decision_014_3

    def apply(self, inputData_014_1: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'decision_014_2'
            decision_014_2StartTime_ = int(time.time_ns()/1000)
            decision_014_2Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decision_014_2Arguments_.put("inputData_014_1", inputData_014_1)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_014_2Arguments_)

            # Evaluate decision 'decision_014_2'
            output_: typing.Optional[str] = self.evaluate(inputData_014_1, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'decision_014_2'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_014_2Arguments_, output_, (int(time.time_ns()/1000) - decision_014_2StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_014_2' evaluation", e)
            return None

    def evaluate(self, inputData_014_1: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_014_3: typing.Optional[str] = self.decision_014_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        return self.stringAdd(self.stringAdd(inputData_014_1, " "), decision_014_3)
