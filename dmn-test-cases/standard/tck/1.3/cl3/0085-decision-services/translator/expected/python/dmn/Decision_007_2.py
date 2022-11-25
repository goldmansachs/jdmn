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

import Decision_007_3


# Generated(value = ["decision.ftl", "decision_007_2"])
class Decision_007_2(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decision_007_2",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, decision_007_3: Decision_007_3.Decision_007_3 = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.decision_007_3 = Decision_007_3.Decision_007_3() if decision_007_3 is None else decision_007_3

    def apply(self, context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        try:
            # Start decision 'decision_007_2'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            decision_007_2StartTime_ = int(time.time_ns()/1000)
            decision_007_2Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decision_007_2Arguments_)

            # Evaluate decision 'decision_007_2'
            output_: typing.Optional[bool] = self.evaluate(context_)

            # End decision 'decision_007_2'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decision_007_2Arguments_, output_, (int(time.time_ns()/1000) - decision_007_2StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decision_007_2' evaluation", e)
            return None

    def evaluate(self, context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[bool]:
        # Apply child decisions
        decision_007_3: typing.Optional[str] = self.decision_007_3.apply(context_)

        return self.stringEqual(decision_007_3, None)
