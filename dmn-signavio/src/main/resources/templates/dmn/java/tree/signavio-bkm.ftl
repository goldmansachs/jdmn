<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#include "signavio-drgElementCommon.ftl">
<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-bkm.ftl", "${transformer.escapeInString(modelRepository.name(drgElement))}"})
@${transformer.drgElementAnnotationClassName()}(
    namespace = "${javaPackageName}",
    name = "${modelRepository.name(drgElement)}",
    label = "${modelRepository.label(drgElement)}",
    elementKind = ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
    expressionKind = ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
    hitPolicy = ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
    rulesCount = ${modelRepository.rulesCount(drgElement)}
)
public class ${javaClassName} extends ${decisionBaseClass} {
    public static final ${transformer.drgElementMetadataClassName()} ${transformer.drgElementMetadataFieldName()} = new ${transformer.drgElementMetadataClassName()}(
        "${javaPackageName}",
        "${modelRepository.name(drgElement)}",
        "${modelRepository.label(drgElement)}",
        ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
        ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
        ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
        ${modelRepository.rulesCount(drgElement)}
    );

    public static final ${javaClassName} INSTANCE = new ${javaClassName}();

    private ${javaClassName}() {
    }

    public static ${transformer.drgElementOutputType(drgElement)} ${transformer.bkmFunctionName(drgElement)}(${transformer.drgElementSignatureExtraCache(drgElement)}) {
        return INSTANCE.apply(${transformer.drgElementArgumentListExtraCache(drgElement)});
    }

    private ${transformer.drgElementOutputType(drgElement)} apply(${transformer.drgElementSignatureExtraCache(drgElement)}) {
        <@applyMethodBody drgElement />
    }
    <@evaluateExpressionMethod drgElement />
}
