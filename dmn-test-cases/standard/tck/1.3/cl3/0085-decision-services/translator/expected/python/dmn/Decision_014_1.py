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

import DecisionService_014


# Generated(value = ["decision.ftl", "decision_014_1"])
class Decision_014_1(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "http://www.montera.com.au/spec/DMN/0085-decision-services",
        "decision_014_1",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.CONTEXT,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, decision_014_3: Decision_014_3.Decision_014_3 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.decision_014_3 = Decision_014_3.Decision_014_3() if decision_014_3 is None else decision_014_3

    def apply(self, inputData_014_1: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[jdmn.runtime.Context.Context]:
        try:
            # Start decision 'decision_014_1'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            decision_014_1StartTime_ = int(time.time_ns()/1000)
            decision_014_1Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decision_014_1Arguments_.put("inputData_014_1", inputData_014_1)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_014_1Arguments_)

            # Evaluate decision 'decision_014_1'
            output_: typing.Optional[jdmn.runtime.Context.Context] = self.evaluate(inputData_014_1, context_)

            # End decision 'decision_014_1'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_014_1Arguments_, output_, (int(time.time_ns()/1000) - decision_014_1StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_014_1' evaluation", e)
            return None

    def evaluate(self, inputData_014_1: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[jdmn.runtime.Context.Context]:
        # Apply child decisions
        decision_014_3: typing.Optional[str] = self.decision_014_3.apply(context_)

        decisionService_014: typing.Optional[str] = DecisionService_014.DecisionService_014.instance().apply("A", "B", context_)
        decision_014_1: typing.Optional[jdmn.runtime.Context.Context] = jdmn.runtime.Context.Context()
        decision_014_1.add("inputData_014_1",  inputData_014_1)
        decision_014_1.add("decision_014_3",  decision_014_3)
        decision_014_1.add("decisionService_014",  decisionService_014)
        return decision_014_1
