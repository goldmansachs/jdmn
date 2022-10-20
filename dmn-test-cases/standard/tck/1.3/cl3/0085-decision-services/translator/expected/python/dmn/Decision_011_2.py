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

import Decision_011_3
import Decision_011_4


# Generated(value = ["decision.ftl", "decision_011_2"])
class Decision_011_2(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decision_011_2",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, decision_011_3: Decision_011_3.Decision_011_3 = None, decision_011_4: Decision_011_4.Decision_011_4 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.decision_011_3 = Decision_011_3.Decision_011_3() if decision_011_3 is None else decision_011_3
        self.decision_011_4 = Decision_011_4.Decision_011_4() if decision_011_4 is None else decision_011_4

    def apply(self, inputData_011_1: typing.Optional[str], inputData_011_2: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'decision_011_2'
            decision_011_2StartTime_ = int(time.time_ns()/1000)
            decision_011_2Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decision_011_2Arguments_.put("inputData_011_1", inputData_011_1)
            decision_011_2Arguments_.put("inputData_011_2", inputData_011_2)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_011_2Arguments_)

            # Evaluate decision 'decision_011_2'
            output_: typing.Optional[str] = self.evaluate(inputData_011_1, inputData_011_2, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'decision_011_2'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_011_2Arguments_, output_, (int(time.time_ns()/1000) - decision_011_2StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_011_2' evaluation", e)
            return None

    def evaluate(self, inputData_011_1: typing.Optional[str], inputData_011_2: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_011_3: typing.Optional[str] = self.decision_011_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_)
        decision_011_4: typing.Optional[str] = self.decision_011_4.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        return self.stringAdd(self.stringAdd(self.stringAdd(self.stringAdd(self.stringAdd(self.stringAdd(inputData_011_1, " "), inputData_011_2), " "), decision_011_3), " "), decision_011_4)
