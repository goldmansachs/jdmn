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
package com.gs.dmn.signavio.extension;

import com.gs.dmn.ast.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.metadata.ExtensionElement;
import com.gs.dmn.runtime.metadata.MultiInstanceDecisionLogicExtension;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.serialization.xstream.ReferencedService;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.serialization.DMNVersion.LATEST;
import static com.gs.dmn.transformation.DMNToManifestTransformer.uniqueId;

public class SignavioExtension {
    public static final String SIG_EXT_NAMESPACE = "http://www.signavio.com/schema/dmn/1.1/";

    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioExtension(SignavioDMNModelRepository dmnModelRepository) {
        this.dmnModelRepository = dmnModelRepository;
    }

    //
    // Decision services
    //
    public TDecisionService referencedService(TBusinessKnowledgeModel bkm) {
        TDMNElement.ExtensionElements extensionElements = bkm.getExtensionElements();
        List<Object> extensions = findExtensions(extensionElements, dmnModelRepository.getSchemaNamespace(), "referencedService");
        if (extensions.isEmpty()) {
            return null;
        } else {
            Object extension = extensions.get(0);
            if (extension instanceof ReferencedService) {
                ReferencedService referencedService = (ReferencedService) extension;
                String serviceId = referencedService.getHref();
                TDefinitions definitions = dmnModelRepository.getRootDefinitions();
                return decisionService(definitions, serviceId);
            } else {
                String serviceId = getAttributeByName((Element) extension, "href");
                TDefinitions definitions = dmnModelRepository.getRootDefinitions();
                return decisionService(definitions, serviceId);
            }
        }
    }

    private TDecisionService decisionService(TDefinitions definitions, String serviceId) {
        List<Object> elementList = findExtensions(definitions.getExtensionElements(), LATEST.getNamespace(), "decisionService");
        for (Object element: elementList) {
            if (element instanceof TDecisionService && dmnModelRepository.sameId((TNamedElement) element, serviceId)) {
                return (TDecisionService) element;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find Decision service '%s'", serviceId));
    }

    private boolean hasName(Object extension, String namespace, String tagName) {
        if (extension instanceof Element) {
            Element element = (Element) extension;
            String namespaceURI = element.getNamespaceURI();
            String name = element.getLocalName();
            return tagName.equals(name) && namespace.equals(namespaceURI);
        } else if (extension instanceof TDecisionService) {
            return tagName.equals("decisionService");
        } else if (extension instanceof ReferencedService) {
            return tagName.equals("referencedService");
        } else if (extension instanceof com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic) {
            return tagName.equals("MultiInstanceDecisionLogic");
        }
        return false;
    }

    //
    // Multi Instance decisions
    //
    public boolean isMultiInstanceDecision(TDRGElement element) {
        try {
            List<Object> extensions = findExtensions(element.getExtensionElements(), dmnModelRepository.getSchemaNamespace(), "MultiInstanceDecisionLogic");
            return extensions != null && extensions.size() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public MultiInstanceDecisionLogic multiInstanceDecisionLogic(TDRGElement element) {
        List<Object> extensions = findExtensions(element.getExtensionElements(), dmnModelRepository.getSchemaNamespace(), "MultiInstanceDecisionLogic");
        Object extension = extensions.get(0);
        String iterationExpression;
        String iteratorShapeId;
        String aggregationFunction;
        String topLevelDecisionId;
        if (extension instanceof com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic) {
            com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic decisionElement = (com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic) extension;
            iterationExpression = decisionElement.getIterationExpression();
            iteratorShapeId = decisionElement.getIteratorShapeId();
            aggregationFunction = decisionElement.getAggregationFunction();
            topLevelDecisionId = decisionElement.getTopLevelDecisionId();
        } else {
            Element decisionElement = (Element) extension;
            iterationExpression = getElementsByTagName(decisionElement, "iterationExpression").item(0).getTextContent();
            iteratorShapeId = getElementsByTagName(decisionElement, "iteratorShapeId").item(0).getTextContent();
            aggregationFunction = getElementsByTagName(decisionElement, "aggregationFunction").item(0).getTextContent();
            topLevelDecisionId = getElementsByTagName(decisionElement, "topLevelDecisionId").item(0).getTextContent();
        }

        TDRGElement iterator = findDRGElementByPartialId(iteratorShapeId);
        Aggregator aggregator = Aggregator.valueOf(aggregationFunction);
        TDRGElement topLevelDecision = findDRGElementByPartialId(topLevelDecisionId);

        return new MultiInstanceDecisionLogic(iterationExpression, iterator, aggregator, (TDecision) topLevelDecision);
    }

    //
    // Manifest
    //
    public ExtensionElement makeMultiInstanceExtension(TDecision decision, TDefinitions importingModel, boolean multiModels) {
        MultiInstanceDecisionLogic multiInstanceDecisionLogic = multiInstanceDecisionLogic(decision);
        String topLevelDecisionId = multiInstanceDecisionLogic.getTopLevelDecision().getId();
        String topLevelDecisionUniqueId = uniqueId(topLevelDecisionId, importingModel, multiModels);
        String aggregator = multiInstanceDecisionLogic.getAggregator().name();
        String iteratorId = multiInstanceDecisionLogic.getIterator().getId();
        String iteratorUniqueId = uniqueId(iteratorId, importingModel, multiModels);
        String iterationExpression = multiInstanceDecisionLogic.getIterationExpression();
        return new MultiInstanceDecisionLogicExtension(iterationExpression, iteratorUniqueId, aggregator, topLevelDecisionUniqueId);
    }

    //
    // Common
    //
    private TDRGElement findDRGElementByPartialId(String iteratorShapeId) {
        String suffix = iteratorShapeId.substring(3);
        for (TDefinitions definitions: this.dmnModelRepository.getAllDefinitions()) {
            for (TDRGElement element: this.dmnModelRepository.findDRGElements(definitions)) {
                if (element.getId().endsWith(suffix)) {
                    return element;
                }
            }
        }
        return null;
    }

    public List<Object> findExtensions(TDMNElement.ExtensionElements extensionElements, String namespace, String extensionName) {
        List<Object> extensions = new ArrayList<>();
        if (extensionElements != null && extensionElements.getAny() != null && !extensionElements.getAny().isEmpty()) {
            List<Object> any = extensionElements.getAny();
            for (Object extension : any) {
                if (hasName(extension, namespace, extensionName)) {
                    extensions.add(extension);
                }
            }
        }
        return extensions;
    }

    private String getAttributeByName(Element element, String attributeName) {
        String attribute = element.getAttribute(attributeName);
        if(StringUtils.isNotEmpty(attribute)) {
            return attribute;
        }
        throw new IllegalArgumentException(String.format("Cannot find attribute name extension '%s'", attributeName));
    }

    private NodeList getElementsByTagName(Element element, String tagName) {
        for (String prefix: dmnModelRepository.getSchemaPrefixes()) {
            String qTagName = XMLConstants.DEFAULT_NS_PREFIX.equals(prefix) ? tagName : String.format("%s:%s", prefix, tagName);
            NodeList nodeList = element.getElementsByTagName(qTagName);
            if (nodeList.getLength() == 1) {
                return nodeList;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find Signavio extension '%s'", tagName));
    }
}
