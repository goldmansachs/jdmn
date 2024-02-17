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
package com.gs.dmn.ast;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.runtime.DMNRuntimeException;

public interface ReferenceVisitor<C, R> {
    // References
    default R visit(DRGElementReference<? extends TDRGElement> reference, C context) {
        TDRGElement element = reference.getElement();
        R result;
        if (element == null) {
            result = null;
        } else if (element instanceof TInputData) {
            result = visitInputReference((DRGElementReference<TInputData>) reference, context);
        } else if (element instanceof TDecision) {
            result = visitDecisionReference((DRGElementReference<TDecision>) reference, context);
        } else if (element instanceof TBusinessKnowledgeModel) {
            result = visitBKMReference((DRGElementReference<TBusinessKnowledgeModel>) reference, context);
        } else if (element instanceof TDecisionService) {
            result = visitDSReference((DRGElementReference<TDecisionService>) reference, context);
        } else if (element instanceof TKnowledgeSource) {
            result = visitSourceReference((DRGElementReference<TKnowledgeSource>) reference, context);
        } else {
            throw new DMNRuntimeException("Not supported for class '%s'".formatted(element.getClass().getSimpleName()));
        }
        return result;
    }

    default R visitInvocable(DRGElementReference<? extends TInvocable> reference, C context) {
        TInvocable element = reference.getElement();
        R result;
        if (element == null) {
            throw new DMNRuntimeException("Cannot visit invocable '%s'".formatted(reference));
        } else if (element instanceof TBusinessKnowledgeModel) {
            result = visitBKMReference((DRGElementReference<TBusinessKnowledgeModel>) reference, context);
        } else if (element instanceof TDecisionService) {
            result = visitDSReference((DRGElementReference<TDecisionService>) reference, context);
        } else {
            throw new DMNRuntimeException("Not supported type '%s'".formatted(element.getClass().getSimpleName()));
        }
        return result;
    }

    default R visitInputReference(DRGElementReference<TInputData> reference, C context) {
        return null;
    }

    default R visitDecisionReference(DRGElementReference<TDecision> reference, C context) {
        return null;
    }

    default R visitBKMReference(DRGElementReference<TBusinessKnowledgeModel> reference, C context) {
        return null;
    }

    default R visitDSReference(DRGElementReference<TDecisionService> reference, C context) {
        return null;
    }

    default R visitSourceReference(DRGElementReference<TKnowledgeSource> reference, C context) {
        return null;
    }
}
