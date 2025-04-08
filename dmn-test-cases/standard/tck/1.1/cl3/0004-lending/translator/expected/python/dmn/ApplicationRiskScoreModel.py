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

import ApplicationRiskScoreModelRuleOutput


# Generated(value = ["bkm.ftl", "ApplicationRiskScoreModel"])
class ApplicationRiskScoreModel(jdmn.runtime.DefaultDMNBaseDecision.DefaultDMNBaseDecision):
    DRG_ELEMENT_METADATA: jdmn.runtime.listener.DRGElement.DRGElement = jdmn.runtime.listener.DRGElement.DRGElement(
        "",
        "ApplicationRiskScoreModel",
        "",
        jdmn.runtime.annotation.DRGElementKind.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        jdmn.runtime.annotation.ExpressionKind.ExpressionKind.DECISION_TABLE,
        jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT,
        11
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

    def apply(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        try:
            # Start BKM 'ApplicationRiskScoreModel'
            annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
            eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
            externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
            cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
            applicationRiskScoreModelStartTime_ = int(time.time_ns()/1000)
            applicationRiskScoreModelArguments_ = jdmn.runtime.listener.Arguments.Arguments()
            applicationRiskScoreModelArguments_.put("Age", age)
            applicationRiskScoreModelArguments_.put("MaritalStatus", maritalStatus)
            applicationRiskScoreModelArguments_.put("EmploymentStatus", employmentStatus)
            eventListener_.startDRGElement(self.DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_)

            # Evaluate BKM 'ApplicationRiskScoreModel'
            output_: typing.Optional[decimal.Decimal] = self.evaluate(age, maritalStatus, employmentStatus, context_)

            # End BKM 'ApplicationRiskScoreModel'
            eventListener_.endDRGElement(self.DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_, output_, (int(time.time_ns()/1000) - applicationRiskScoreModelStartTime_))

            return output_
        except Exception as e:
            self.logError("Exception caught in 'ApplicationRiskScoreModel' evaluation", e)
            return None

    def evaluate(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> typing.Optional[decimal.Decimal]:
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        # Apply rules and collect results
        ruleOutputList_ = jdmn.runtime.RuleOutputList.RuleOutputList()
        ruleOutputList_.add(self.rule0(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule1(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule2(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule3(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule4(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule5(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule6(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule7(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule8(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule9(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(self.rule10(age, maritalStatus, employmentStatus, context_))

        # Return results based on hit policy
        output_: typing.Optional[decimal.Decimal]
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = None
        else:
            ruleOutputs_: typing.List[jdmn.runtime.RuleOutput.RuleOutput] = ruleOutputList_.applyMultiple(jdmn.runtime.annotation.HitPolicy.HitPolicy.COLLECT)
            output_ = self.sum(list(map(lambda x_: x_.applicationRiskScoreModel, ruleOutputs_)))

        return output_

    def rule0(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(0, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanAnd(self.numericGreaterEqualThan(age, self.number("18")), self.numericLessEqualThan(age, self.number("21"))),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("32")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule1(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(1, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanAnd(self.numericGreaterEqualThan(age, self.number("22")), self.numericLessEqualThan(age, self.number("25"))),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("35")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule2(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(2, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanAnd(self.numericGreaterEqualThan(age, self.number("26")), self.numericLessEqualThan(age, self.number("35"))),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("40")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule3(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(3, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.booleanAnd(self.numericGreaterEqualThan(age, self.number("36")), self.numericLessEqualThan(age, self.number("49"))),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("43")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule4(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(4, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            self.numericGreaterEqualThan(age, self.number("50")),
            True,
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("48")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule5(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(5, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            self.stringEqual(maritalStatus, "S"),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("25")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule6(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(6, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            self.stringEqual(maritalStatus, "M"),
            True
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("45")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule7(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(7, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            self.stringEqual(employmentStatus, "UNEMPLOYED")
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("15")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule8(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(8, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            self.stringEqual(employmentStatus, "EMPLOYED")
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("45")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule9(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(9, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            self.stringEqual(employmentStatus, "SELF-EMPLOYED")
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("36")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_

    def rule10(self, age: typing.Optional[decimal.Decimal], maritalStatus: typing.Optional[str], employmentStatus: typing.Optional[str], context_: jdmn.runtime.ExecutionContext.ExecutionContext) -> jdmn.runtime.RuleOutput.RuleOutput:
        # Rule metadata
        drgRuleMetadata: jdmn.runtime.listener.Rule.Rule = jdmn.runtime.listener.Rule.Rule(10, "")

        # Rule start
        annotationSet_: jdmn.runtime.annotation.AnnotationSet.AnnotationSet = None if context_ is None else context_.annotations
        eventListener_: jdmn.runtime.listener.EventListener.EventListener = None if context_ is None else context_.eventListener
        externalExecutor_: jdmn.runtime.external.ExternalFunctionExecutor.ExternalFunctionExecutor = None if context_ is None else context_.externalFunctionExecutor
        cache_: jdmn.runtime.cache.Cache.Cache = None if context_ is None else context_.cache
        eventListener_.startRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

        # Apply rule
        output_: ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput.ApplicationRiskScoreModelRuleOutput(False)
        if (self.ruleMatches(eventListener_, drgRuleMetadata,
            True,
            True,
            self.stringEqual(employmentStatus, "STUDENT")
        )):
            # Rule match
            eventListener_.matchRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata)

            # Compute output
            output_.setMatched(True)
            output_.applicationRiskScoreModel = self.number("18")

        # Rule end
        eventListener_.endRule(self.DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
