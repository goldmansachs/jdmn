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

import Decision_012_2


# Generated(value = ["ds.ftl", "decisionService_012"])
class DecisionService_012(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decisionService_012",
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

    def initSubDecisions(self, decision_012_2: Decision_012_2.Decision_012_2 = None):
        self.decision_012_2 = Decision_012_2.Decision_012_2() if decision_012_2 is None else decision_012_2

    def apply(self, inputData_012_1: typing.Optional[str], inputData_012_2: typing.Optional[str], decision_012_3: typing.Optional[str], decision_012_4: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start DS 'decisionService_012'
            decisionService_012StartTime_ = int(time.time_ns()/1000)
            decisionService_012Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_012Arguments_.put("inputData_012_1", inputData_012_1)
            decisionService_012Arguments_.put("inputData_012_2", inputData_012_2)
            decisionService_012Arguments_.put("decision_012_3", decision_012_3)
            decisionService_012Arguments_.put("decision_012_4", decision_012_4)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_012Arguments_)

            # Bind input decisions
            cache_.bind("decision_012_3", decision_012_3)
            cache_.bind("decision_012_4", decision_012_4)

            # Evaluate DS 'decisionService_012'
            output_: typing.Optional[str] = self.evaluate(inputData_012_1, inputData_012_2, decision_012_3, decision_012_4, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End DS 'decisionService_012'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_012Arguments_, output_, (int(time.time_ns()/1000) - decisionService_012StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_012' evaluation", e)
            return None

    def evaluate(self, inputData_012_1: typing.Optional[str], inputData_012_2: typing.Optional[str], decision_012_3: typing.Optional[str], decision_012_4: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_012_2: typing.Optional[str] = self.decision_012_2.apply(inputData_012_1, inputData_012_2, annotationSet_, eventListener_, externalExecutor_, cache_)

        return decision_012_2
