/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.annotation;

import org.omg.spec.dmn._20180521.model.*;

public enum DRGElementKind {
    DECISION,
    BUSINESS_KNOWLEDGE_MODEL,
    KNOWLEDGE_SOURCE,
    INPUT_DATA,
    OTHER;

    public static DRGElementKind kindByClass(TDRGElement element) {
        if (element instanceof TDecision) {
            return DECISION;
        } else if (element instanceof TBusinessKnowledgeModel) {
            return BUSINESS_KNOWLEDGE_MODEL;
        } else if (element instanceof TInputData) {
            return INPUT_DATA;
        } else if (element instanceof TKnowledgeSource) {
            return KNOWLEDGE_SOURCE;
        }
        return OTHER;
    }
}
