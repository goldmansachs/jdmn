<#include "drgElementCommon.ftl">
<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

<@importRequiredBKMs drgElement />
@javax.annotation.Generated(value = {"bkm.ftl", "${modelRepository.name(drgElement)}"})
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

    public static ${transformer.drgElementOutputType(drgElement)} ${transformer.bkmFunctionName(drgElement)}(${transformer.drgElementSignatureExtra(transformer.drgElementSignature(drgElement))}) {
        return INSTANCE.apply(${transformer.drgElementArgumentsExtra(transformer.drgElementArgumentList(drgElement))});
    }

    private ${transformer.drgElementOutputType(drgElement)} apply(${transformer.drgElementSignatureExtra(transformer.drgElementSignature(drgElement))}) {
        <@applyMethodBody drgElement />
    }
    <@evaluateExpressionMethod drgElement />
}
