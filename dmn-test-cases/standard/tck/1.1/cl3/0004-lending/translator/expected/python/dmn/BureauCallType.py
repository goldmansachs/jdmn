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

import PreBureauRiskCategory

import BureauCallTypeTable


# Generated(value = ["decision.ftl", "BureauCallType"])
class BureauCallType(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "BureauCallType",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.DECISION,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.INVOCATION,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNKNOWN,
        -1
    )

    def __init__(self, preBureauRiskCategory: PreBureauRiskCategory.PreBureauRiskCategory = None):
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)
        self.preBureauRiskCategory = PreBureauRiskCategory.PreBureauRiskCategory() if preBureauRiskCategory is None else preBureauRiskCategory

    def apply(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start decision 'BureauCallType'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            bureauCallTypeStartTime_ = int(time.time_ns()/1000)
            bureauCallTypeArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            bureauCallTypeArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, bureauCallTypeArguments_)

            # Evaluate decision 'BureauCallType'
            output_: typing.Optional[str] = self.evaluate(applicantData, context_)

            # End decision 'BureauCallType'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, bureauCallTypeArguments_, output_, (int(time.time_ns()/1000) - bureauCallTypeStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'BureauCallType' evaluation", e)
            return None

    def evaluate(self, applicantData: typing.Optional[type_.TApplicantData.TApplicantData], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        # Apply child decisions
        preBureauRiskCategory: typing.Optional[str] = self.preBureauRiskCategory.apply(applicantData, context_)

        return BureauCallTypeTable.BureauCallTypeTable.instance().apply(preBureauRiskCategory, context_)
