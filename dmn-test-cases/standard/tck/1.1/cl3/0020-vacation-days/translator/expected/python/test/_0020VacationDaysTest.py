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

# DRG Elements to test
import TotalVacationDays


# Generated(value = ["junit.ftl", "0020-vacation-days.dmn"])
class _0020VacationDaysTest(unittest.TestCase, jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def testCase001(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("16")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("1")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("27"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase002(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("25")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("5")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("22"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase003(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("25")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("24"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase004(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("44")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("30")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase005(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("50")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("24"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase006(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("50")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("30")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase007(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        age: typing.Optional[decimal.Decimal] = self.number("60")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
