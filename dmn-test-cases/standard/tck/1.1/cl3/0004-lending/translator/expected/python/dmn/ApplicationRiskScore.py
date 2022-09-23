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

import ApplicationRiskScoreModel


# Generated(value = ["decision.ftl", "ApplicationRiskScore"])
class ApplicationRiskScore(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "ApplicationRiskScore",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        try:
            # Start decision 'ApplicationRiskScore'
            applicationRiskScoreStartTime_ = int(time.time_ns()/1000)
            applicationRiskScoreArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            applicationRiskScoreArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, applicationRiskScoreArguments_)

            # Evaluate decision 'ApplicationRiskScore'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

            # End decision 'ApplicationRiskScore'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (int(time.time_ns()/1000) - applicationRiskScoreStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'ApplicationRiskScore' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet, eventListener_: jdmn.runtime.listener.EventListener.EventListener, externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor, cache_: jdmn.runtime.cache.Cache.Cache) -> typing.Optional[decimal.Decimal]:
        return ApplicationRiskScoreModel.ApplicationRiskScoreModel.instance().apply(None if (applicantData is None) else (applicantData.age), None if (applicantData is None) else (applicantData.maritalStatus), None if (applicantData is None) else (applicantData.employmentStatus), annotationSet_, eventListener_, externalExecutor_, cache_)
