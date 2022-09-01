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

import Decision_002


# Generated(value = {"ds.ftl", "decisionService_002"})
class DecisionService_002(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "decisionService_002",
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

    def initSubDecisions(self, decision_002: Decision_002.Decision_002 = None):
        self.decision_002 = Decision_002.Decision_002() if decision_002 is None else decision_002

    def apply(self, decision_002_input: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        try:
            # Start DS 'decisionService_002'
            decisionService_002StartTime_ = int(time.time_ns()/1000)
            decisionService_002Arguments_ = jdmn.runtime.listener.Arguments.Arguments()
            decisionService_002Arguments_.put("decision_002_input", decision_002_input)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, decisionService_002Arguments_)

            # Bind input decisions
            cache_.bind("decision_002_input", decision_002_input)

            # Evaluate DS 'decisionService_002'
            output_: typing.Optional[str] = self.evaluate(decision_002_input, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End DS 'decisionService_002'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, decisionService_002Arguments_, output_, (int(time.time_ns()/1000) - decisionService_002StartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'decisionService_002' evaluation", e)
            return None
    def evaluate(self, decision_002_input: typing.Optional[str], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[str]:
        # Apply child decisions
        decision_002: typing.Optional[str] = self.decision_002.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        return decision_002
