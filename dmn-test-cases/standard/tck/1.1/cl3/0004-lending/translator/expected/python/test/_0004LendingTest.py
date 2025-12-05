import typing
import decimal
import datetime
import isodate
import unittest

import jdmn.runtime.Assert
import jdmn.runtime.DefaultDMNBaseDecision
import jdmn.runtime.ExecutionContext
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

    def testCase001_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Adjudication'
        self.checkValues("ACCEPT", Adjudication.Adjudication().apply(applicantData, bureauData, supportingDocuments, context_))

    def testCase001_2(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'ApplicationRiskScore'
        self.checkValues(self.number("130"), ApplicationRiskScore.ApplicationRiskScore().apply(applicantData, context_))

    def testCase001_3(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Pre-bureauRiskCategory'
        self.checkValues("LOW", PreBureauRiskCategory.PreBureauRiskCategory().apply(applicantData, context_))

    def testCase001_4(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'BureauCallType'
        self.checkValues("MINI", BureauCallType.BureauCallType().apply(applicantData, context_))

    def testCase001_5(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Post-bureauRiskCategory'
        self.checkValues("LOW", PostBureauRiskCategory.PostBureauRiskCategory().apply(applicantData, bureauData, context_))

    def testCase001_6(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'RequiredMonthlyInstallment'
        self.checkValues(self.number("1680.880325608555"), RequiredMonthlyInstallment.RequiredMonthlyInstallment().apply(requestedProduct, context_))

    def testCase001_7(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Pre-bureauAffordability'
        self.checkValues(True, PreBureauAffordability.PreBureauAffordability().apply(applicantData, requestedProduct, context_))

    def testCase001_8(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Eligibility'
        self.checkValues("ELIGIBLE", Eligibility.Eligibility().apply(applicantData, requestedProduct, context_))

    def testCase001_9(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Strategy'
        self.checkValues("BUREAU", Strategy.Strategy().apply(applicantData, requestedProduct, context_))

    def testCase001_10(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Post-bureauAffordability'
        self.checkValues(True, PostBureauAffordability.PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, context_))

    def testCase001_11(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        applicantData: typing.Optional[type_.TApplicantData.TApplicantData] = type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[type_.TRequestedProduct.TRequestedProduct] = type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[type_.TBureauData.TBureauData] = type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check 'Routing'
        self.checkValues("ACCEPT", Routing.Routing().apply(applicantData, bureauData, requestedProduct, context_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
