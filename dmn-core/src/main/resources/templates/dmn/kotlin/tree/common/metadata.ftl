<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro classAnnotation templateName drgElement>
@jakarta.annotation.Generated(value = ["${templateName}", "${transformer.escapeInString(modelRepository.name(drgElement))}"])
@${transformer.drgElementAnnotationClassName()}(
    namespace = "${javaPackageName}",
    name = "${modelRepository.name(drgElement)}",
    label = "${modelRepository.label(drgElement)}",
    elementKind = ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
    expressionKind = ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
    hitPolicy = ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
    rulesCount = ${modelRepository.rulesCount(drgElement)}
)
</#macro>

<#macro elementMetadataField drgElement>
        val ${transformer.drgElementMetadataFieldName()} : ${transformer.drgElementMetadataClassName()} = ${transformer.drgElementMetadataClassName()}(
            "${javaPackageName}",
            "${modelRepository.name(drgElement)}",
            "${modelRepository.label(drgElement)}",
            ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
            ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
            ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
            ${modelRepository.rulesCount(drgElement)}
        )
</#macro>