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
package com.gs.dmn.signavio.rdf2dmn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.rdf2dmn.json.EnumItem;
import com.gs.dmn.signavio.rdf2dmn.json.ItemDefinition;
import com.gs.dmn.signavio.rdf2dmn.json.decision.DecisionExpression;
import com.gs.dmn.signavio.rdf2dmn.json.decision.DecisionTable;
import com.gs.dmn.signavio.rdf2dmn.json.decision.OutputClause;
import com.gs.dmn.signavio.rdf2dmn.json.relation.Relation;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RDFModel {
    public static final String INPUT_DATA = "InputData";
    public static final String DECISION = "Decision";
    public static final String INFORMATION_REQUIREMENT = "InformationRequirement";

    private static final String ORYX_NS = "http://oryx-editor.org/";
    private static final String RAZEL_NS = "http://raziel.org/";

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setVisibility(MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    private final Document model;

    public RDFModel(Document model) {
        this.model = model;
    }

    public String getAboutAttribute(Element resource) {
        return resource.getAttribute("rdf:about").replace("#", "");
    }

    public String getResourceAttribute(Element resource) {
        return resource.getAttribute("rdf:resource").replace("#", "");
    }

    public String getName(Element resource) {
        return getObject(resource, "name");
    }

    public String getLabel(Element resource) {
        return getObject(resource, "name");
    }

    public String getEnumItems(Element resource) {
        return getObject(resource, "enumitems");
    }

    public String getDecision(Element resource) {
        return getObject(resource, "decision");
    }

    public String getRelations(Element resource) {
        return getObject(resource, "relations");
    }

    public List<Relation> getRelationList(String relationsString) {
        try {
            return MAPPER.readValue(relationsString, MAPPER.getTypeFactory().constructCollectionType(List.class, Relation.class));
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot deserialize relations '%s'", relationsString), e);
        }
    }

    public List<EnumItem> getEnumItemList(String enumItemsString) {
        try {
            return MAPPER.readValue(enumItemsString, MAPPER.getTypeFactory().constructCollectionType(List.class, EnumItem.class));
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot deserialize enumItems '%s'", enumItemsString), e);
        }
    }

    public String getObject(Element resource, String propertyName) {
        NodeList elementsByTagName = resource.getElementsByTagName(propertyName);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node item = elementsByTagName.item(i);
            String textContent = item.getTextContent();
            if (!StringUtils.isBlank(textContent)) {
                return textContent;
            }
        }
        return null;
    }

    public boolean isDecision(Element resource) {
        return isElementType(resource, DECISION);
    }

    public boolean isInputData(Element resource) {
        return isElementType(resource, INPUT_DATA);
    }

    public List<Element> findAllInformationRequirement() {
        return findAll(INFORMATION_REQUIREMENT);
    }

    private List<Element> findAll(String type) {
        List<Element> result = new ArrayList<>();
        NodeList descriptions = model.getDocumentElement().getElementsByTagName("rdf:Description");
        for (int i = 0; i < descriptions.getLength(); i++) {
            Node description = descriptions.item(i);
            NodeList properties = description.getChildNodes();
            for (int j = 0; j < properties.getLength(); j++) {
                Node tag = properties.item(j);
                if (tag != null) {
                    String nodeName = tag.getNodeName();
                    if ("type".equalsIgnoreCase(nodeName)) {
                        String nodeValue = tag.getTextContent();
                        if (type.equals(nodeValue)) {
                            result.add((Element) description);
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<Element> findAllDecision() {
        return findAll(DECISION);
    }

    public List<Element> findAllInputData() {
        return findAll(INPUT_DATA);
    }

    public Element findDecisionById(String shapeId) {
        List<Element> allInputData = findAllDecision();
        for(Element element: allInputData) {
            if (getAboutAttribute(element).equals(shapeId)) {
                return element;
            }
        }
        return null;
    }

    public Element findInputDataById(String shapeId) {
        List<Element> allInputData = findAllInputData();
        for(Element element: allInputData) {
            if (getAboutAttribute(element).equals(shapeId)) {
                return element;
            }
        }
        return null;
    }

    private boolean isElementType(Element resource, String type) {
        NodeList children = resource.getElementsByTagName("type");
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            String textContent = child.getTextContent();
            if (type.equals(textContent)) {
                return true;
            }
        }
        return false;
    }

    public String getFEELType(Element resource) {
        List<String> types = getTypes(resource);
        List<String> foundTypes = types.stream().filter(FEELType.FEEL_TYPE_NAMES::contains).collect(Collectors.toList());
        return foundTypes.size() == 1 ? foundTypes.get(0) : null;
    }

    private List<String> getTypes(Element resource) {
        List<String> types = new ArrayList<>();
        NodeList children = resource.getElementsByTagName("type");
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            String textContent = child.getTextContent();
            types.add(textContent);
        }
        return types;
    }

    public List<Element> findAllObjects(Element decision, String propertyName) {
        List<Element> nodes = new ArrayList<>();
        NodeList nodeList = decision.getElementsByTagName(propertyName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodes.add((Element) nodeList.item(i));
        }
        return nodes;
    }

    public String pathName(Element element, String outputId) {
        try {
            if (isDecision(element)) {
                String decisionText = getDecision(element);
                DecisionExpression expression = MAPPER.readValue(decisionText, DecisionExpression.class);
                if (expression instanceof DecisionTable) {
                    for (OutputClause oc : ((DecisionTable) expression).getOutputClauses()) {
                        ItemDefinition itemDefinition = oc.getItemDefinition();
                        if (outputId.equals(oc.getId())) {
                            return itemDefinition.getName();
                        }
                    }
                }
            } else if (isInputData(element)) {
                String relationsText = getRelations(element);
                List<Relation> relations = MAPPER.readValue(relationsText, new TypeReference<List<Relation>>(){});
                for(Relation relation: relations) {
                    if (relation.getValue().getPathElements().contains(Integer.valueOf(outputId))) {
                        return relation.getValue().getName();
                    }
                }
            }
            throw new DMNRuntimeException(String.format("Cannot find output '%s' in element '%s'", outputId, element.getAttribute("id")));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find output '%s' in element '%s'", outputId, element.getAttribute("id")), e);
        }
    }

    boolean isList(Element resource) {
        return Boolean.parseBoolean(getObject(resource, "islist"));
    }

    public Element findDescriptionById(String resourceId) {
        if (!resourceId.startsWith("#")) {
            resourceId = "#" + resourceId;
        }
        NodeList descriptions = model.getDocumentElement().getElementsByTagName("rdf:Description");
        for (int i = 0; i < descriptions.getLength(); i++) {
            Node description = descriptions.item(i);
            Node about = description.getAttributes().getNamedItem("rdf:about");
            if (resourceId.equals(about.getNodeValue())) {
                return (Element) description;
            }
        }
        return null;
    }

    public boolean hasSingleOutput(String decisionId) {
        try {
            Element decision = findDecisionById(decisionId);
            String decisionText = getDecision(decision);
            DecisionExpression expression;
            expression = RDFModel.MAPPER.readValue(decisionText, DecisionExpression.class);
            if (expression instanceof DecisionTable) {
                List<OutputClause> outputClauses = ((DecisionTable) expression).getOutputClauses();
                return outputClauses.size() == 1;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}