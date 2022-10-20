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

import Decision_003


# Generated(value = {"ds.ftl", "decisionService_003"})
class DecisionService_003(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decisionService_003",
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

    def initSubDecisions(self, decision_003: Decision_003.Decision_003 = None):
        self.decision_003 = Decision_003.Decision_003() if decision_003 is None else decision_003

    def apply(self, inputData_003: typing.Optional[str], decision_003_input_1: typing.Optional[str], decision_003_input_2: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start DS 'decisionService_003'
            decisionService_003StartTime_ = int(time.time_ns()/1000)
            decisionService_003Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_003Arguments_.put("inputData_003", inputData_003)
            decisionService_003Arguments_.put("decision_003_input_1", decision_003_input_1)
            decisionService_003Arguments_.put("decision_003_input_2", decision_003_input_2)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_003Arguments_)

            # Bind input decisions
            cache_.bind("decision_003_input_1", decision_003_input_1)
            cache_.bind("decision_003_input_2", decision_003_input_2)

            # Evaluate DS 'decisionService_003'
            output_: typing.Optional[str] = self.evaluate(inputData_003, decision_003_input_1, decision_003_input_2, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End DS 'decisionService_003'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_003Arguments_, output_, (int(time.time_ns()/1000) - decisionService_003StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_003' evaluation", e)
            return None

    def evaluate(self, inputData_003: typing.Optional[str], decision_003_input_1: typing.Optional[str], decision_003_input_2: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_003: typing.Optional[str] = self.decision_003.apply(inputData_003, annotationSet_, eventListener_, externalExecutor_, cache_)

        return decision_003
