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
package com.gs.dmn.signavio;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.extension.SignavioExtension;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.List;
import java.util.Set;

import static com.gs.dmn.serialization.DMNVersion.DMN_12;

public class SignavioDMNModelRepository extends DMNModelRepository {
    public final SignavioExtension extension = new SignavioExtension(this);

    public SignavioDMNModelRepository() {
        super();
    }

    public SignavioDMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(pair.getLeft(), pair.getRight());
    }

    public SignavioDMNModelRepository(TDefinitions definitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        super(definitions, prefixNamespaceMappings);
        List<Object> elementList = extension.findExtensions(definitions.getExtensionElements(), DMN_12.getNamespace(), "decisionService");
        for(Object element: elementList) {
            Object value = ((JAXBElement) element).getValue();
            if (value instanceof TDecisionService) {
                this.addElementMap((TDecisionService)value, definitions);
            }
        }
    }

    public SignavioExtension getExtension() {
        return extension;
    }

    public boolean isBKMLinkedToDecision(TNamedElement element) {
        return element instanceof TBusinessKnowledgeModel
                && getOutputDecision((TBusinessKnowledgeModel) element) != null;
    }

    public TDecision getOutputDecision(TBusinessKnowledgeModel element) {
        TDecisionService decisionService = getExtension().referencedService(element);
        return decisionService == null ? null : this.getOutputDecision(decisionService);
    }

    public boolean isMultiInstanceDecision(TDRGElement decision) {
        return extension.isMultiInstanceDecision(decision);
    }

    @Override
    public void collectInputDatas(TDRGElement element, Set<TInputData> inputDatas) {
        inputDatas.addAll(directInputDatas(element));
        // Add inputs used in iteration body / topLevelDecision
        if (isMultiInstanceDecision(element)) {
            MultiInstanceDecisionLogic multiInstanceDecisionLogic = extension.multiInstanceDecisionLogic(element);
            TDecision topLevelDecision = multiInstanceDecisionLogic.getTopLevelDecision();

            List<TInputData> inputDataSet = allInputDatas(topLevelDecision);
            inputDataSet.remove(multiInstanceDecisionLogic.getIterator());
            inputDatas.addAll(inputDataSet);
        }
        for (TDecision child : directSubDecisions(element)) {
            collectInputDatas(child, inputDatas);
        }
    }

    public void addItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition) {
        definitions.getItemDefinition().add(itemDefinition);
        this.itemDefinitions.add(itemDefinition);
    }
}
