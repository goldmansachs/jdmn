/*
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

public enum DRGElementKind {
    DECISION,
    DECISION_SERVICE,
    BUSINESS_KNOWLEDGE_MODEL,
    KNOWLEDGE_SOURCE,
    INPUT_DATA,
    OTHER;

    public static DRGElementKind kindByClass(Class drgElementClass) {
        String className = drgElementClass.getSimpleName();
        return kindByName(className);
    }

    static DRGElementKind kindByName(String className) {
        if ("TDecision".equals(className)) {
            return DECISION;
        } else if ("TDecisionService".equals(className)) {
            return DECISION_SERVICE;
        } else if ("TBusinessKnowledgeModel".equals(className)) {
            return BUSINESS_KNOWLEDGE_MODEL;
        } else if ("TInputData".equals(className)) {
            return INPUT_DATA;
        } else if ("TKnowledgeSource".equals(className)) {
            return KNOWLEDGE_SOURCE;
        }
        return OTHER;
    }
}
