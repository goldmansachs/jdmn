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

import PostBureauRiskCategoryTableRuleOutput


# Generated(value = ["bkm.ftl", "'Post-bureauRiskCategoryTable'"])
class PostBureauRiskCategoryTable(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "'Post-bureauRiskCategoryTable'",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE,
        13
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

    def apply(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        try:
            # Start BKM ''Post-bureauRiskCategoryTable''
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            postBureauRiskCategoryTableStartTime_ = int(time.time_ns()/1000)
            postBureauRiskCategoryTableArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            postBureauRiskCategoryTableArguments_.put("ExistingCustomer", existingCustomer)
            postBureauRiskCategoryTableArguments_.put("ApplicationRiskScore", applicationRiskScore)
            postBureauRiskCategoryTableArguments_.put("CreditScore", creditScore)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_)

            # Evaluate BKM ''Post-bureauRiskCategoryTable''
            output_: typing.Optional[str] = self.evaluate(existingCustomer, applicationRiskScore, creditScore, context_)

            # End BKM ''Post-bureauRiskCategoryTable''
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_, output_, (int(time.time_ns()/1000) - postBureauRiskCategoryTableStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in ''Post-bureauRiskCategoryTable'' evaluation", e)
            return None

    def evaluate(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[str]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule1(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule2(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule3(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule4(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule5(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule6(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule7(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule8(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule9(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule10(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule11(existingCustomer, applicationRiskScore, creditScore, context_))
        ruleOutputList_.add(self.rule12(existingCustomer, applicationRiskScore, creditScore, context_))

        # Return results based on hit policy
        output_: typing.Optional[str]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutput_: jdmn.runtime.RuleOutput.RuleOutput = ruleOutputList_.applySingle(jdmn.runtime.annotation.HitPolicy.HitPolicy.UNIQUE)
            output_ = None if ruleOutput_ is None else ruleOutput_.postBureauRiskCategoryTable

        return output_

    def rule0(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericLessThan(applicationRiskScore, self.number("120"))),
            (self.numericLessThan(creditScore, self.number("590")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 0, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericLessThan(applicationRiskScore, self.number("120"))),
            (self.booleanAnd(self.numericGreaterEqualThan(creditScore, self.number("590")), self.numericLessEqualThan(creditScore, self.number("610"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 1, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericLessThan(applicationRiskScore, self.number("120"))),
            (self.numericGreaterThan(creditScore, self.number("610")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 2, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule3(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(3, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("120")), self.numericLessEqualThan(applicationRiskScore, self.number("130")))),
            (self.numericLessThan(creditScore, self.number("600")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 3, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule4(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(4, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("120")), self.numericLessEqualThan(applicationRiskScore, self.number("130")))),
            (self.booleanAnd(self.numericGreaterEqualThan(creditScore, self.number("600")), self.numericLessEqualThan(creditScore, self.number("625"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 4, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule5(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(5, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.booleanAnd(self.numericGreaterEqualThan(applicationRiskScore, self.number("120")), self.numericLessEqualThan(applicationRiskScore, self.number("130")))),
            (self.numericGreaterThan(creditScore, self.number("625")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 5, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule6(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(6, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, False)),
            (self.numericGreaterThan(applicationRiskScore, self.number("130"))),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "VERY LOW"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 6, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule7(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(7, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericLessEqualThan(applicationRiskScore, self.number("100"))),
            (self.numericLessThan(creditScore, self.number("580")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 7, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule8(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(8, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericLessEqualThan(applicationRiskScore, self.number("100"))),
            (self.booleanAnd(self.numericGreaterEqualThan(creditScore, self.number("580")), self.numericLessEqualThan(creditScore, self.number("600"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 8, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule9(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(9, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericLessEqualThan(applicationRiskScore, self.number("100"))),
            (self.numericGreaterThan(creditScore, self.number("600")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 9, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule10(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(10, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericGreaterThan(applicationRiskScore, self.number("100"))),
            (self.numericLessThan(creditScore, self.number("590")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "HIGH"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 10, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule11(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(11, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericGreaterThan(applicationRiskScore, self.number("100"))),
            (self.booleanAnd(self.numericGreaterEqualThan(creditScore, self.number("590")), self.numericLessEqualThan(creditScore, self.number("615"))))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 11, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule12(self, existingCustomer: typing.Optional[bool], applicationRiskScore: typing.Optional[decimal.Decimal], creditScore: typing.Optional[decimal.Decimal], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(12, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput.PostBureauRiskCategoryTableRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            (self.booleanEqual(existingCustomer, True)),
            (self.numericGreaterThan(applicationRiskScore, self.number("100"))),
            (self.numericGreaterThan(creditScore, self.number("615")))
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.postBureauRiskCategoryTable = "LOW"

            # Add annotation
            annotationSet_.addAnnotation("'Post-bureauRiskCategoryTable'", 12, "")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
