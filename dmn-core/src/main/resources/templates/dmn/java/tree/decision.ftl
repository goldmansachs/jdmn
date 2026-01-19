<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#import "/tree/common/metadata.ftl" as metadata />
<#import "/tree/common/constructor.ftl" as constructor />
<#import "/tree/common/apply.ftl" as apply />
<#if javaPackageName?has_content>
package ${javaPackageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

<@metadata.classAnnotation "decision.ftl" drgElement/>
public class ${javaClassName} extends ${decisionBaseClass}<${transformer.drgElementOutputType(drgElement)}> {
    <@metadata.elementMetadataField drgElement />
    <@constructor.decisionConstructor drgElement javaClassName />

    <@apply.applyMethods drgElement />
    <@apply.evaluateExpressionMethod drgElement />
}
