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

import DecisionService_006


# Generated(value = ["decision.ftl", "decision_006_1"])
class Decision_006_1(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decision_006_1",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start decision 'decision_006_1'
            decision_006_1StartTime_ = int(time.time_ns()/1000)
            decision_006_1Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_006_1Arguments_)

            # Evaluate decision 'decision_006_1'
            output_: typing.Optional[str] = self.evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'decision_006_1'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_006_1Arguments_, output_, (int(time.time_ns()/1000) - decision_006_1StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_006_1' evaluation", e)
            return None

    def evaluate(self, annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        return DecisionService_006.DecisionService_006.instance().apply("bar", annotationSet_, eventListener_, externalExecutor_, cache_)