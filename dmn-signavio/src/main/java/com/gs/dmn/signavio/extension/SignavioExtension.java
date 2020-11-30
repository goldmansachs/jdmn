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

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.metadata.ExtensionElement;
import com.gs.dmn.runtime.metadata.MultiInstanceDecisionLogicExtension;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.serialization.DMNVersion.LATEST;

public class SignavioExtension {
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
            String serviceId = getAttributeByName((Element) extensions.get(0), "href");
            TDefinitions definitions = dmnModelRepository.getRootDefinitions();
            return decisionService(definitions, serviceId);
        }
    }

    private TDecisionService decisionService(TDefinitions definitions, String serviceId) {
        List<Object> elementList = findExtensions(definitions.getExtensionElements(), LATEST.getNamespace(), "decisionService");
        for(Object element: elementList) {
            Object value = ((JAXBElement<?>) element).getValue();
            if (value instanceof TDecisionService && dmnModelRepository.sameId((TNamedElement) value, serviceId)) {
                return (TDecisionService) value;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find Decision service '%s'", serviceId));
    }

    private boolean hasName(Object extension, String namespace, String tagName) {
        if (extension instanceof Element) {
            Element element = (Element) extension;
            String namespaceURI = element.getNamespaceURI();
            String name = element.getLocalName();
            return tagName.equals(name) &&
                    namespace.equals(namespaceURI);
        } else if (extension instanceof JAXBElement) {
            JAXBElement<?> element = (JAXBElement<?>)extension;
            String namespaceURI = element.getName().getNamespaceURI();
            String name = element.getName().getLocalPart();
            return tagName.equals(name) &&
                    namespace.equals(namespaceURI);
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
        Element decisionElement = (Element) extensions.get(0);
        String iterationExpression = getElementsByTagName(decisionElement, "iterationExpression").item(0).getTextContent();
        String iteratorShapeId = getElementsByTagName(decisionElement, "iteratorShapeId").item(0).getTextContent();
        String aggregationFunction = getElementsByTagName(decisionElement, "aggregationFunction").item(0).getTextContent();
        String topLevelDecisionId = getElementsByTagName(decisionElement, "topLevelDecisionId").item(0).getTextContent();

        TDRGElement iterator = findDRGElementByPartialId(iteratorShapeId);
        Aggregator aggregator = Aggregator.valueOf(aggregationFunction);
        TDRGElement topLevelDecision = findDRGElementByPartialId(topLevelDecisionId);

        return new MultiInstanceDecisionLogic(iterationExpression, iterator, aggregator, (TDecision) topLevelDecision);
    }

    //
    // Manifest
    //
    public ExtensionElement makeMultiInstanceExtension(TDecision decision) {
        MultiInstanceDecisionLogic multiInstanceDecisionLogic = multiInstanceDecisionLogic(decision);
        String topLevelDecisionId = multiInstanceDecisionLogic.getTopLevelDecision().getId();
        String aggregator = multiInstanceDecisionLogic.getAggregator().name();
        String iteratorId = multiInstanceDecisionLogic.getIterator().getId();
        String iterationExpression = multiInstanceDecisionLogic.getIterationExpression();
        return new MultiInstanceDecisionLogicExtension(iterationExpression, iteratorId, aggregator, topLevelDecisionId);
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
        for(String prefix: dmnModelRepository.getSchemaPrefixes()) {
            NodeList nodeList = element.getElementsByTagName(String.format("%s:%s", prefix, tagName));
            if (nodeList != null && nodeList.getLength() == 1) {
                return nodeList;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find Signavio extension '%s'", tagName));
    }
}
