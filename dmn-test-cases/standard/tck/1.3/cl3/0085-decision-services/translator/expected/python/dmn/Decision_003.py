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

import Decision_003_input_1
import Decision_003_input_2


# Generated(value = ["decision.ftl", "decision_003"])
class Decision_003(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decision_003",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, decision_003_input_1: Decision_003_input_1.Decision_003_input_1 = None, decision_003_input_2: Decision_003_input_2.Decision_003_input_2 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.decision_003_input_1 = Decision_003_input_1.Decision_003_input_1() if decision_003_input_1 is None else decision_003_input_1
        self.decision_003_input_2 = Decision_003_input_2.Decision_003_input_2() if decision_003_input_2 is None else decision_003_input_2

    def apply(self, inputData_003: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'decision_003'
            decision_003StartTime_ = int(time.time_ns()/1000)
            decision_003Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decision_003Arguments_.put("inputData_003", inputData_003)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_003Arguments_)

            # Evaluate decision 'decision_003'
            output_: typing.Optional[str] = self.evaluate(inputData_003, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'decision_003'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_003Arguments_, output_, (int(time.time_ns()/1000) - decision_003StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_003' evaluation", e)
            return None

    def evaluate(self, inputData_003: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_003_input_1: typing.Optional[str] = self.decision_003_input_1.apply(annotationSet_, eventListener_, externalExecutor_, cache_)
        decision_003_input_2: typing.Optional[str] = self.decision_003_input_2.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        return self.stringAdd(self.stringAdd(self.stringAdd(self.stringAdd(self.stringAdd("A ", decision_003_input_1), " "), decision_003_input_2), " "), inputData_003)
