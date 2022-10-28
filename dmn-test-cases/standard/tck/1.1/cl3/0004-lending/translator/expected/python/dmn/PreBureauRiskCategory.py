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

import ApplicationRiskScore

import PreBureauRiskCategoryTable


# Generated(value = ["decision.ftl", "'Pre-bureauRiskCategory'"])
class PreBureauRiskCategory(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "'Pre-bureauRiskCategory'",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, applicationRiskScore: ApplicationRiskScore.ApplicationRiskScore = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.applicationRiskScore = ApplicationRiskScore.ApplicationRiskScore() if applicationRiskScore is None else applicationRiskScore

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start decision ''Pre-bureauRiskCategory''
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            preBureauRiskCategoryStartTime_ = int(time.time_ns()/1000)
            preBureauRiskCategoryArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            preBureauRiskCategoryArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_)

            # Evaluate decision ''Pre-bureauRiskCategory''
            output_: typing.Optional[str] = self.evaluate(applicantData, context_)

            # End decision ''Pre-bureauRiskCategory''
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (int(time.time_ns()/1000) - preBureauRiskCategoryStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in ''Pre-bureauRiskCategory'' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        # Apply child decisions
        applicationRiskScore: typing.Optional[decimal.Decimal] = self.applicationRiskScore.apply(applicantData, context_)

        return PreBureauRiskCategoryTable.PreBureauRiskCategoryTable.instance().apply(None if (applicantData is None) else (applicantData.existingCustomer), applicationRiskScore, context_)
