<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#--
    Import
-->
<#macro importStatements drgElement>
<@importRuntimeStatements drgElement/>
<@importModelElementStatements drgElement/>
</#macro>

<#macro importRuntimeStatements drgElement>
import typing
import decimal
import datetime
import isodate
import time

import ${transformer.jdmnRootPackage()}.runtime.Context
import ${transformer.jdmnRootPackage()}.runtime.DefaultDMNBaseDecision
import ${transformer.jdmnRootPackage()}.runtime.ExecutionContext
import ${transformer.jdmnRootPackage()}.runtime.LambdaExpression
import ${transformer.jdmnRootPackage()}.runtime.LazyEval
import ${transformer.jdmnRootPackage()}.runtime.Pair
import ${transformer.jdmnRootPackage()}.runtime.Range
import ${transformer.jdmnRootPackage()}.runtime.RuleOutput
import ${transformer.jdmnRootPackage()}.runtime.RuleOutputList

import ${transformer.jdmnRootPackage()}.runtime.annotation.Annotation
import ${transformer.jdmnRootPackage()}.runtime.annotation.AnnotationSet
import ${transformer.jdmnRootPackage()}.runtime.annotation.DRGElementKind
import ${transformer.jdmnRootPackage()}.runtime.annotation.ExpressionKind
import ${transformer.jdmnRootPackage()}.runtime.annotation.HitPolicy

import ${transformer.jdmnRootPackage()}.runtime.cache.Cache

import ${transformer.jdmnRootPackage()}.runtime.external.ExternalFunctionExecutor

import ${transformer.jdmnRootPackage()}.runtime.listener.Arguments
import ${transformer.jdmnRootPackage()}.runtime.listener.DRGElement
import ${transformer.jdmnRootPackage()}.runtime.listener.EventListener
import ${transformer.jdmnRootPackage()}.runtime.listener.Rule
</#macro>

<#macro importModelElementStatements drgElement>
<#if transformer.hasComplexInputDatas(drgElement)>

<@importComplexInputDatas drgElement/>
</#if>
<#if transformer.hasDirectSubDecisions(drgElement)>

<@importSubDecisions drgElement/>
</#if>
<#if transformer.hasDirectSubInvocables(drgElement)>

<@importSubInvocables drgElement/>
</#if>
<@importRecursiveBKM drgElement/>
<#if modelRepository.isDecisionTableExpression(drgElement)>

import ${transformer.qualifiedModuleName(javaPackageName, transformer.ruleOutputClassName(drgElement))}
</#if>
</#macro>

<#macro importComplexInputDatas drgElement>
    <#list transformer.drgElementComplexInputClassNames(drgElement)>
        <#items as module>
import ${module}
        </#items>
    </#list>
</#macro>

<#macro importSubDecisions drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
import ${transformer.qualifiedModuleName(subDecision)}
        </#items>
    </#list>
</#macro>

<#macro importSubInvocables drgElement>
    <#list modelRepository.directSubInvocables(drgElement)>
        <#items as bkm>
import ${transformer.qualifiedModuleName(bkm)}
        </#items>
    </#list>
</#macro>

<#macro importRecursiveBKM drgElement>
    <#if modelRepository.isRecursiveBKM(drgElement)>

import ${transformer.qualifiedModuleName(drgElement)}
    </#if>
</#macro>
