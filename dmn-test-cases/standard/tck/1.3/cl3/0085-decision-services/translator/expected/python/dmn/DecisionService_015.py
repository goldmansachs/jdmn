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

import Decision_015_1
import Decision_015_2


# Generated(value = ["ds.ftl", "decisionService_015"])
class DecisionService_015(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "http://www.montera.com.au/spec/DMN/0085-decision-services",
        "decisionService_015",
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

    def initSubDecisions(self, decision_015_1: Decision_015_1.Decision_015_1 = None, decision_015_2: Decision_015_2.Decision_015_2 = None):
        self.decision_015_1 = Decision_015_1.Decision_015_1() if decision_015_1 is None else decision_015_1
        self.decision_015_2 = Decision_015_2.Decision_015_2() if decision_015_2 is None else decision_015_2

    def apply(self, context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[jdmn.runtime.Context.Context]:
        try:
            # Start DS 'decisionService_015'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            decisionService_015StartTime_ = int(time.time_ns()/1000)
            decisionService_015Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_015Arguments_)

            # Evaluate DS 'decisionService_015'
            output_: typing.Optional[jdmn.runtime.Context.Context] = self.evaluate(context_)

            # End DS 'decisionService_015'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_015Arguments_, output_, (int(time.time_ns()/1000) - decisionService_015StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_015' evaluation", e)
            return None

    def evaluate(self, context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[jdmn.runtime.Context.Context]:
        # Apply child decisions
        decision_015_1: typing.Optional[str] = self.decision_015_1.apply(context_)
        decision_015_2: typing.Optional[str] = self.decision_015_2.apply(context_)

        output_: typing.Optional[jdmn.runtime.Context.Context] = jdmn.runtime.Context.Context()
        output_.add("decision_015_1",  decision_015_1)
        output_.add("decision_015_2",  decision_015_2)
        return output_
