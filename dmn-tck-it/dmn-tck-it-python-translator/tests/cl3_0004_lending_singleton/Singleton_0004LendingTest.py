import typing
import unittest
import decimal
import datetime

import jdmn.runtime.Assert
import jdmn.runtime.DefaultDMNBaseDecision
import jdmn.runtime.annotation.AnnotationSet
import jdmn.runtime.cache.DefaultCache
import jdmn.runtime.external.DefaultExternalFunctionExecutor
import jdmn.runtime.listener.NopEventListener

# Complex input datas
import generated.tck.cl3_0004_lending_singleton.type_.Monthly
import generated.tck.cl3_0004_lending_singleton.type_.MonthlyImpl
import generated.tck.cl3_0004_lending_singleton.type_.TApplicantData
import generated.tck.cl3_0004_lending_singleton.type_.TApplicantDataImpl
import generated.tck.cl3_0004_lending_singleton.type_.TBureauData
import generated.tck.cl3_0004_lending_singleton.type_.TBureauDataImpl
import generated.tck.cl3_0004_lending_singleton.type_.TRequestedProduct
import generated.tck.cl3_0004_lending_singleton.type_.TRequestedProductImpl
# DRG Elements to test
import generated.tck.cl3_0004_lending_singleton.Adjudication
import generated.tck.cl3_0004_lending_singleton.ApplicationRiskScore
import generated.tck.cl3_0004_lending_singleton.PreBureauRiskCategory
import generated.tck.cl3_0004_lending_singleton.BureauCallType
import generated.tck.cl3_0004_lending_singleton.PostBureauRiskCategory
import generated.tck.cl3_0004_lending_singleton.RequiredMonthlyInstallment
import generated.tck.cl3_0004_lending_singleton.PreBureauAffordability
import generated.tck.cl3_0004_lending_singleton.Eligibility
import generated.tck.cl3_0004_lending_singleton.Strategy
import generated.tck.cl3_0004_lending_singleton.PostBureauAffordability
import generated.tck.cl3_0004_lending_singleton.Routing


# Generated(value = ["junit.ftl", "0004-lending.dmn"])
class Singleton_0004LendingTest(unittest.TestCase, jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def testCase001(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        applicantData: typing.Optional[generated.tck.cl3_0004_lending_singleton.type_.TApplicantData.TApplicantData] = generated.tck.cl3_0004_lending_singleton.type_.TApplicantDataImpl.TApplicantDataImpl(self.number("35"), "EMPLOYED", True, "M", generated.tck.cl3_0004_lending_singleton.type_.MonthlyImpl.MonthlyImpl(self.number("2000"), self.number("6000"), self.number("0")))
        requestedProduct: typing.Optional[generated.tck.cl3_0004_lending_singleton.type_.TRequestedProduct.TRequestedProduct] = generated.tck.cl3_0004_lending_singleton.type_.TRequestedProductImpl.TRequestedProductImpl(self.number("350000"), "STANDARD LOAN", self.number("0.0395"), self.number("360"))
        bureauData: typing.Optional[generated.tck.cl3_0004_lending_singleton.type_.TBureauData.TBureauData] = generated.tck.cl3_0004_lending_singleton.type_.TBureauDataImpl.TBureauDataImpl(False, self.number("649"))
        supportingDocuments: typing.Optional[str] = "YES"

        # Check Adjudication
        self.checkValues("ACCEPT", generated.tck.cl3_0004_lending_singleton.Adjudication.Adjudication.instance().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check ApplicationRiskScore
        self.checkValues(self.number("130"), generated.tck.cl3_0004_lending_singleton.ApplicationRiskScore.ApplicationRiskScore.instance().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Pre-bureauRiskCategory'
        self.checkValues("LOW", generated.tck.cl3_0004_lending_singleton.PreBureauRiskCategory.PreBureauRiskCategory.instance().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check BureauCallType
        self.checkValues("MINI", generated.tck.cl3_0004_lending_singleton.BureauCallType.BureauCallType.instance().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Post-bureauRiskCategory'
        self.checkValues("LOW", generated.tck.cl3_0004_lending_singleton.PostBureauRiskCategory.PostBureauRiskCategory.instance().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check RequiredMonthlyInstallment
        self.checkValues(self.number("1680.880325608555"), generated.tck.cl3_0004_lending_singleton.RequiredMonthlyInstallment.RequiredMonthlyInstallment.instance().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Pre-bureauAffordability'
        self.checkValues(True, generated.tck.cl3_0004_lending_singleton.PreBureauAffordability.PreBureauAffordability.instance().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Eligibility
        self.checkValues("ELIGIBLE", generated.tck.cl3_0004_lending_singleton.Eligibility.Eligibility.instance().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Strategy
        self.checkValues("BUREAU", generated.tck.cl3_0004_lending_singleton.Strategy.Strategy.instance().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check 'Post-bureauAffordability'
        self.checkValues(True, generated.tck.cl3_0004_lending_singleton.PostBureauAffordability.PostBureauAffordability.instance().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        # Check Routing
        self.checkValues("ACCEPT", generated.tck.cl3_0004_lending_singleton.Routing.Routing.instance().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
