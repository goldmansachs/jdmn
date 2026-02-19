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

# DRG Elements to test
import org.gs.TotalVacationDays


# Generated(value = ["junit.ftl", "0020-vacation-days.dmn"])
class _0020VacationDaysTest01Test(unittest.TestCase, jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def testCase001_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("16")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("1")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("27"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase002_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("25")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("5")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("22"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase003_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("25")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("24"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase004_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("44")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("30")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase005_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("50")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("24"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase006_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("50")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("30")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def testCase007_1(self):
        context_ = jdmn.runtime.ExecutionContext.ExecutionContext()
        cache_ = context_.cache
        # Initialize arguments
        age: typing.Optional[decimal.Decimal] = self.number("60")
        yearsOfService: typing.Optional[decimal.Decimal] = self.number("20")

        # Check 'Total Vacation Days'
        self.checkValues(self.number("30"), org.gs.TotalVacationDays.TotalVacationDays().apply(age, yearsOfService, context_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
