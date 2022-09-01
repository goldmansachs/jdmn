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
import DecisionService_001
import DecisionService_002
import DecisionService_003
import Decision_004_1
import Decision_006_1
import Decision_007_1
import Decision_009_1
import Decision_011_1
import Decision_012_1
import Decision_013_1
import Decision_014_1


# Generated(value = ["junit.ftl", "0085-decision-services.dmn"])
class _0085DecisionServicesTest(unittest.TestCase, jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(self)

    def testCase001(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_001
        self.checkValues("foo", DecisionService_001.DecisionService_001.instance().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase002(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        decision_002_input: typing.Optional[str] = "baz"
        cache_.bind("decision_002_input", decision_002_input)

        # Check decision_002
        self.checkValues("foo baz", DecisionService_002.DecisionService_002.instance().apply(decision_002_input, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase003(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        decision_003_input_1: typing.Optional[str] = "B"
        cache_.bind("decision_003_input_1", decision_003_input_1)
        decision_003_input_2: typing.Optional[str] = "C"
        cache_.bind("decision_003_input_2", decision_003_input_2)
        inputData_003: typing.Optional[str] = "D"

        # Check decision_003
        self.checkValues("A B C D", DecisionService_003.DecisionService_003.instance().apply(inputData_003, decision_003_input_1, decision_003_input_2, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase004(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_004_1
        self.checkValues("foo", Decision_004_1.Decision_004_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase006(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_006_1
        self.checkValues("foo bar", Decision_006_1.Decision_006_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase007(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_007_1
        self.checkValues(True, Decision_007_1.Decision_007_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase009(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_009_1
        self.checkValues("foo bar", Decision_009_1.Decision_009_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase011(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_011_1
        self.checkValues("A B C D", Decision_011_1.Decision_011_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase012(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()

        # Check decision_012_1
        self.checkValues("A B C D", Decision_012_1.Decision_012_1().apply(annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase013(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        inputData_013_1: typing.Optional[str] = "C"

        # Check decision_013_1
        self.checkValues(jdmn.runtime.Context.Context().add("decisionService_013", "A B").add("decision_013_3", "D").add("inputData_013_1", "C"), Decision_013_1.Decision_013_1().apply(inputData_013_1, annotationSet_, eventListener_, externalExecutor_, cache_))

    def testCase014(self):
        annotationSet_ = jdmn.runtime.annotation.AnnotationSet.AnnotationSet()
        eventListener_ = jdmn.runtime.listener.NopEventListener.NopEventListener()
        externalExecutor_ = jdmn.runtime.external.DefaultExternalFunctionExecutor.DefaultExternalFunctionExecutor()
        cache_ = jdmn.runtime.cache.DefaultCache.DefaultCache()
        # Initialize input data
        inputData_014_1: typing.Optional[str] = "C"

        # Check decision_014_1
        self.checkValues(jdmn.runtime.Context.Context().add("decisionService_014", "A B").add("decision_014_3", "D").add("inputData_014_1", "C"), Decision_014_1.Decision_014_1().apply(inputData_014_1, annotationSet_, eventListener_, externalExecutor_, cache_))

    def checkValues(self, expected: typing.Any, actual: typing.Any):
        jdmn.runtime.Assert.Assert().assertEquals(expected, actual)
