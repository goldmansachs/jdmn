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

import type_.Monthly
import type_.MonthlyImpl
import type_.TApplicantData
import type_.TApplicantDataImpl
import type_.TBureauData
import type_.TBureauDataImpl


# Generated(value = ["decision.ftl", "Adjudication"])
class Adjudication(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "Adjudication",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.LITERAL_EXPRESSION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], supportingDocuments: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start decision 'Adjudication'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            adjudicationStartTime_ = int(time.time_ns()/1000)
            adjudicationArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            adjudicationArguments_.put("ApplicantData", applicantData)
            adjudicationArguments_.put("BureauData", bureauData)
            adjudicationArguments_.put("SupportingDocuments", supportingDocuments)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, adjudicationArguments_)

            # Evaluate decision 'Adjudication'
            output_: typing.Optional[str] = self.evaluate(applicantData, bureauData, supportingDocuments, context_)

            # End decision 'Adjudication'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, adjudicationArguments_, output_, (int(time.time_ns()/1000) - adjudicationStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Adjudication' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], bureauData: typing.Optional[type_.TBureauData.TBureauData], supportingDocuments: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        return "ACCEPT"
