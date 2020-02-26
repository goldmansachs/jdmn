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
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.extension.SignavioExtension;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.DMNVersion.DMN_12;

public class SignavioDMNModelRepository extends DMNModelRepository {
    private String schemaNamespace = "http://www.signavio.com/schema/dmn/1.1/";
    private String[] schemaPrefixes = new String[] {
            "signavio", "sigExt"
    };

    private QName diagramId = new QName(schemaNamespace, "diagramId");
    private QName shapeId = new QName(schemaNamespace, "shapeId");

    public final SignavioExtension extension = new SignavioExtension(this);

    public SignavioDMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions(), new PrefixNamespaceMappings());
    }

    public SignavioDMNModelRepository(TDefinitions definitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this(new Pair<>(definitions, prefixNamespaceMappings));
    }

    public SignavioDMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(Arrays.asList(pair));
    }

    public SignavioDMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair, String schemaNamespace) {
        this(pair);
        this.schemaNamespace = schemaNamespace;
        this.diagramId = new QName(schemaNamespace, "diagramId");
        this.shapeId = new QName(schemaNamespace, "shapeId");
    }

    public SignavioDMNModelRepository(List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs) {
        super(pairs);
        for (Pair<TDefinitions, PrefixNamespaceMappings> pair: pairs) {
            TDefinitions definitions = pair.getLeft();
            List<Object> elementList = extension.findExtensions(definitions.getExtensionElements(), DMN_12.getNamespace(), "decisionService");
            for(Object element: elementList) {
                Object value = ((JAXBElement) element).getValue();
                if (value instanceof TDecisionService) {
                    this.addElementMap((TDecisionService)value, definitions);
                }
            }
        }
    }

    public String getSchemaNamespace() {
        return schemaNamespace;
    }

    public String[] getSchemaPrefixes() {
        return schemaPrefixes;
    }

    public QName getDiagramIdQName() {
        return diagramId;
    }

    public QName getShapeIdQName() {
        return shapeId;
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
    protected List<DRGElementReference<TInputData>> collectAllInputDatas(DRGElementReference<? extends TDRGElement> parentReference) {
        TDRGElement parent = parentReference.getElement();
        ImportPath parentImportPath = parentReference.getImportPath();
        List<DRGElementReference<TInputData>> result = new ArrayList<>();
        result.addAll(directInputDatas(parent));
        // Add inputs used in iteration body / topLevelDecision
        if (isMultiInstanceDecision(parent)) {
            MultiInstanceDecisionLogic multiInstanceDecisionLogic = extension.multiInstanceDecisionLogic(parent);
            TDecision topLevelDecision = multiInstanceDecisionLogic.getTopLevelDecision();
            DRGElementReference<? extends TDRGElement> topLevelReference = new DRGElementReference<>(topLevelDecision);

            List<DRGElementReference<TInputData>> inputDataList = allInputDatas(topLevelReference);
            inputDataList.removeIf(tInputDataDMNReference -> tInputDataDMNReference.getElement() == multiInstanceDecisionLogic.getIterator());
            result.addAll(inputDataList);
        }

        // Process direct children
        List<TDMNElementReference> childReferences = requiredDecisionReferences(parent);
        for (TDMNElementReference reference: childReferences) {
            TDecision child = findDecisionByRef(parent, reference.getHref());
            if (child != null) {
                String importName = findImportName(parent, reference);
                TDefinitions model = this.getModel(child);
                DRGElementReference childReference = new DRGElementReference(child, new ImportPath(parentImportPath, importName));
                List<DRGElementReference<TInputData>> inputReferences = collectAllInputDatas(childReference);
                result.addAll(inputReferences);
            } else {
                throw new DMNRuntimeException(String.format("Cannot find Decision for '%s' in parent '%s'", reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public void addItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition) {
        definitions.getItemDefinition().add(itemDefinition);
        this.itemDefinitions.add(itemDefinition);
    }
}
