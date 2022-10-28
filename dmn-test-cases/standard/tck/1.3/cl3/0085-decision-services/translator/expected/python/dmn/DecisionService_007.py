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

import Decision_007_2


# Generated(value = ["ds.ftl", "decisionService_007"])
class DecisionService_007(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decisionService_007",
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

    def initSubDecisions(self, decision_007_2: Decision_007_2.Decision_007_2 = None):
        self.decision_007_2 = Decision_007_2.Decision_007_2() if decision_007_2 is None else decision_007_2

    def apply(self, decision_007_3: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        try:
            # Start DS 'decisionService_007'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            decisionService_007StartTime_ = int(time.time_ns()/1000)
            decisionService_007Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_007Arguments_.put("decision_007_3", decision_007_3)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_007Arguments_)

            # Bind input decisions
            cache_.bind("decision_007_3", decision_007_3)

            # Evaluate DS 'decisionService_007'
            output_: typing.Optional[bool] = self.evaluate(decision_007_3, context_)

            # End DS 'decisionService_007'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_007Arguments_, output_, (int(time.time_ns()/1000) - decisionService_007StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_007' evaluation", e)
            return None

    def evaluate(self, decision_007_3: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        # Apply child decisions
        decision_007_2: typing.Optional[bool] = self.decision_007_2.apply(context_)

        return decision_007_2
