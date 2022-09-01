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

import Decision_014_2


# Generated(value = {"ds.ftl", "decisionService_014"})
class DecisionService_014(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decisionService_014",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION_SERVICE,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.OTHER,
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
            cls._instance.initSubDecisions()
        return cls._instance

    def initSubDecisions(self, decision_014_2: Decision_014_2.Decision_014_2 = None):
        self.decision_014_2 = Decision_014_2.Decision_014_2() if decision_014_2 is None else decision_014_2

    def apply(self, inputData_014_1: typing.Optional[str], decision_014_3: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start DS 'decisionService_014'
            decisionService_014StartTime_ = int(time.time_ns()/1000)
            decisionService_014Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_014Arguments_.put("inputData_014_1", inputData_014_1)
            decisionService_014Arguments_.put("decision_014_3", decision_014_3)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_014Arguments_)

            # Bind input decisions
            cache_.bind("decision_014_3", decision_014_3)

            # Evaluate DS 'decisionService_014'
            output_: typing.Optional[str] = self.evaluate(inputData_014_1, decision_014_3, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End DS 'decisionService_014'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_014Arguments_, output_, (int(time.time_ns()/1000) - decisionService_014StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_014' evaluation", e)
            return None
    def evaluate(self, inputData_014_1: typing.Optional[str], decision_014_3: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_014_2: typing.Optional[str] = self.decision_014_2.apply(inputData_014_1, annotationSet_, eventListener_, externalExecutor_, cache_)

        return decision_014_2
