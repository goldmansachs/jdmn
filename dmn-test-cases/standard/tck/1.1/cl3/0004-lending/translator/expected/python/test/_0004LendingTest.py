import typing
import decimal
import datetime
import isodate
import unittest

import jdmn.runtime.Assert
import jdmn.runtime.DefaultDMNBaseDecision
import jdmn.runtime.annotation.AnnotationSet
import jdmn.runtime.cache.DefaultCache
import jdmn.runtime.external.DefaultExternalFunctionExecutor
import jdmn.runtime.listener.NopEventListener

# Complex input datas
import type_.Monthly
import type_.MonthlyImpl
import type_.TApplicantData
import type_.TApplicantDataImpl
import type_.TBureauData
import type_.TBureauDataImpl
import type_.TRequestedProduct
import type_.TRequestedProductImpl
# DRG Elements to test
import Adjudication
import ApplicationRiskScore
import PreBureauRiskCategory
import BureauCallType
import PostBureauRiskCategory
import RequiredMonthlyInstallment
import PreBureauAffordability
import Eligibility
import Strategy
import PostBureauAffordability
import Routing


# Generated(value = ["junit.ftl", "0004-lending.dmn"])
class _0004LendingTest(unittest.TestCase, jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def testCase001(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check Adjudication
        self.checkValues("ACCEPT", Adjudication.Adjudication().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check ApplicationRiskScore
        self.checkValues(self.number("130"), ApplicationRiskScore.ApplicationRiskScore().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Pre-bureauRiskCategory'
        self.checkValues("LOW", PreBureauRiskCategory.PreBureauRiskCategory().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check BureauCallType
        self.checkValues("MINI", BureauCallType.BureauCallType().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Post-bureauRiskCategory'
        self.checkValues("LOW", PostBureauRiskCategory.PostBureauRiskCategory().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check RequiredMonthlyInstallment
        self.checkValues(self.number("1680.880325608555"), RequiredMonthlyInstallment.RequiredMonthlyInstallment().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Pre-bureauAffordability'
        self.checkValues(True, PreBureauAffordability.PreBureauAffordability().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Eligibility
        self.checkValues("ELIGIBLE", Eligibility.Eligibility().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Strategy
        self.checkValues("BUREAU", Strategy.Strategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Post-bureauAffordability'
        self.checkValues(True, PostBureauAffordability.PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Routing
        self.checkValues("ACCEPT", Routing.Routing().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
