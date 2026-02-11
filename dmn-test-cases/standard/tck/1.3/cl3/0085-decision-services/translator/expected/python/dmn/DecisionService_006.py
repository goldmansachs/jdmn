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

import Decision_006_2


# Generated(value = ["ds.ftl", "decisionService_006"])
class DecisionService_006(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "http://www.montera.com.au/spec/DMN/0085-decision-services",
        "decisionService_006",
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

    def initSubDecisions(self, decision_006_2: Decision_006_2.Decision_006_2 = None):
        self.decision_006_2 = Decision_006_2.Decision_006_2() if decision_006_2 is None else decision_006_2

    def apply(self, decision_006_3: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start DS 'decisionService_006'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            decisionService_006StartTime_ = int(time.time_ns()/1000)
            decisionService_006Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_006Arguments_.put("decision_006_3", decision_006_3)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_006Arguments_)

            # Bind input decisions
            cache_.bind("decision_006_3", decision_006_3)

            # Evaluate DS 'decisionService_006'
            output_: typing.Optional[str] = self.evaluate(decision_006_3, context_)

            # End DS 'decisionService_006'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_006Arguments_, output_, (int(time.time_ns()/1000) - decisionService_006StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_006' evaluation", e)
            return None

    def evaluate(self, decision_006_3: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        # Apply child decisions
        decision_006_2: typing.Optional[str] = self.decision_006_2.apply(context_)

        return decision_006_2
