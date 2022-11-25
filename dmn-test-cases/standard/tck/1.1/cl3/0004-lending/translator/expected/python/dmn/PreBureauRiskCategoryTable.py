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

import PreBureauRiskCategoryTableRuleOutput


# Generated(value = ["bkm.ftl", "Pre-bureauRiskCategoryTable"])
class PreBureauRiskCategoryTable(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "Pre-bureauRiskCategoryTable",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE,
        8
    )
    _instance = None

    def __init__(self):
        raise RuntimeError("Call instance() instead")

    @classmethod
    def instance(cls):
        if cls._instance is None:
            cls._instance = cls.__new__(cls)
            jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision.__init__(cls._instance)
        return cls._instance

    def apply(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start BKM 'Pre-bureauRiskCategoryTable'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            preBureauRiskCategoryTableStartTime_ = int(time.time_ns()/1000)
            preBureauRiskCategoryTableArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            preBureauRiskCategoryTableArguments_.put("ExistingCustomer", existingCustomer)
            preBureauRiskCategoryTableArguments_.put("ApplicationRiskScore", applicationRiskScore)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_)

            # Evaluate BKM 'Pre-bureauRiskCategoryTable'
            output_: typing.Optional[str] = self.evaluate(existingCustomer, applicationRiskScore, context_)

            # End BKM 'Pre-bureauRiskCategoryTable'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_, output_, (int(time.time_ns()/1000) - preBureauRiskCategoryTableStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'Pre-bureauRiskCategoryTable' evaluation", e)
            return None

    def evaluate(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule1(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule2(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule3(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule4(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule5(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule6(existingCustomer, applicationRiskScore, context_))
        ruleOutputList_.add(self.rule7(existingCustomer, applicationRiskScore, context_))

        # Return results based on hit policy
        output_: typing.Optional[str]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE)
            output_ = None if ruleOutput_ is None else ruleOutput_.preBureauRiskCategoryTable

        return output_

    def rule0(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericLessThan(applicationRiskScore, self.number("100")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("100")), self.numericLessThan(applicationRiskScore, self.number("120"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("120")), self.numericLessThan(applicationRiskScore, self.number("130"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule3(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(3, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericGreaterThan(applicationRiskScore, self.number("130")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "VERY LOW"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 3, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule4(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(4, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericLessThan(applicationRiskScore, self.number("80")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "DECLINE"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 4, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule5(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(5, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("80")), self.numericLessThan(applicationRiskScore, self.number("90"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 5, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule6(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(6, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("90")), self.numericLessEqualThan(applicationRiskScore, self.number("110"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 6, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule7(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(7, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput.PreBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericGreaterThan(applicationRiskScore, self.number("110")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.preBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("Pre-bureauRiskCategoryTable", 7, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
