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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.rdf2dmn.json.*;
import com.gs.dmn.signavio.rdf2dmn.json.decision.*;
import com.gs.dmn.signavio.rdf2dmn.json.expression.Expression;
import com.gs.dmn.signavio.rdf2dmn.json.expression.FeelContext;
import com.gs.dmn.signavio.rdf2dmn.json.expression.FunctionCall;
import com.gs.dmn.signavio.rdf2dmn.json.expression.Reference;
import com.gs.dmn.signavio.rdf2dmn.json.relation.EnumerationProperty;
import com.gs.dmn.signavio.rdf2dmn.json.relation.Relation;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.AbstractFileTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20151101.model.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;

public class RDFToDMNTransformer extends AbstractFileTransformer {
    public static final String NUMBER_TYPE = "number";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String STRING_TYPE = "string";
    public static final String DATE_TYPE = "date";
    public static final String TIME_TYPE = "time";
    public static final String DATETIME_TYPE = "datetime";
    public static final String DURATION_TYPE = "duration";
    public static final String ENUMERATION_TYPE = "enumeration";

    protected static final List<String> FEEL_DATA_TYPES = Arrays.asList(
            NUMBER_TYPE, BOOLEAN_TYPE, STRING_TYPE, DATE_TYPE, TIME_TYPE, DATETIME_TYPE, DURATION_TYPE, ENUMERATION_TYPE
    );

    public static final String RDF_FILE_EXTENSION = ".xml";

    public static boolean isRDFFile(File file) {
        return file != null && file.isFile() && file.getName().endsWith(RDF_FILE_EXTENSION);
    }

    private static final Map<String, String> FUNCTION_RETURN_TYPE = new LinkedHashMap<>();
    static {
        FUNCTION_RETURN_TYPE.put("concat", STRING_TYPE);
        FUNCTION_RETURN_TYPE.put("count", NUMBER_TYPE);
    }

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private RDFModel rdfModel;
    private final RDFReader rdfReader;
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialectDefinition;
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final DMNReader dmnReader;
    private final DMNWriter dmnWriter;

    public RDFToDMNTransformer(InputParameters inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
        this.dialectDefinition = new SignavioDMNDialectDefinition();
        this.dmnTransformer = this.dialectDefinition.createBasicTransformer(new SignavioDMNModelRepository(), new NopLazyEvaluationDetector(), inputParameters);
        this.dmnReader = new DMNReader(logger, false);
        this.dmnWriter = new DMNWriter(logger);
        this.rdfReader = new RDFReader(logger);
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        if (inputFile == null) {
            return false;
        } else if (inputFile.isDirectory()) {
            return !inputFile.getName().endsWith(".svn");
        } else {
            return isRDFFile(inputFile);
        }
    }

    @Override
    protected void transformFile(File inputFile, File inputRoot, Path outputPath) {
        if (inputFile.isDirectory()) {
            if (shouldTransformFile(inputFile)) {
                logger.info(String.format("Scanning folder '%s'", inputFile.getPath()));
                File[] files = inputFile.listFiles();
                if (files != null) {
                    for (File child : files) {
                        transformFile(child, inputRoot, outputPath);
                    }
                }
            }
        } else {
            try {
                if (shouldTransformFile(inputFile)) {
                    logger.info(String.format("Transforming file '%s'", inputFile.getPath()));
                    transformLeaf(inputFile, inputRoot, outputPath);
                }
            } catch (Exception e) {
                throw new DMNRuntimeException(String.format("Failed to transform diagram '%s'", inputFile.getPath()), e);
            }
        }
    }

    private void transformLeaf(File child, File root, Path outputPath) {
        try (FileInputStream inputStream = new FileInputStream(child.toURI().getPath())) {
            File outputFolder = outputFolder(child, root, outputPath);
            File outputFile = new File(outputFolder, diagramName(child) + DMNConstants.DMN_FILE_EXTENSION);

            logger.info(String.format("Output folder '%s' ", outputFolder.getCanonicalPath()));
            logger.info(String.format("Output file %s ...", outputFile.getCanonicalPath()));

            TDefinitions element = transform(diagramName(child), inputStream);
            dmnWriter.write(element, outputFile, new DMNNamespacePrefixMapper(this.inputParameters.getNamespace(), this.inputParameters.getPrefix(), DMN_11));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error during transforming '%s'.", child.getName()), e);
        }
    }

    private TDefinitions transform(String name, InputStream inputStream) throws Exception {
        this.rdfModel = rdfReader.readModel(name, inputStream);

        TDefinitions root = OBJECT_FACTORY.createTDefinitions();
        root.setNamespace(DMN_11.getNamespace());
        root.setName(name);
        addItemDefinitions(root);
        addDRGElements(root);
        return root;
    }

    private String diagramName(File child) {
        String fileName = child.getName();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index);
    }

    private void addItemDefinitions(TDefinitions root) {
        for (Element decision : rdfModel.findAllDecision()) {
            addItemDefinitionsForDecision(root, decision);
        }
        for (Element inputData : rdfModel.findAllInputData()) {
            addItemDefinitionsForInputData(root, inputData);
        }
    }

    private void addItemDefinitionsForDecision(TDefinitions root, Element decision) {
        try {
            String decisionText = rdfModel.getDecision(decision);
            DecisionExpression expression = RDFModel.MAPPER.readValue(decisionText, DecisionExpression.class);
            if (expression instanceof DecisionTable) {
                HitPolicy hitPolicy = ((DecisionTable) expression).getHitPolicy();
                String aggregation = ((DecisionTable) expression).getAggregation();
                List<OutputClause> outputClauses = ((DecisionTable) expression).getOutputClauses();
                if (outputClauses.size() == 0) {
                    throw new IllegalArgumentException(String.format("Cannot create ItemDefinition for '%s' without OutputClauses", rdfModel.getAboutAttribute(decision)));
                } else if (outputClauses.size() == 1) {
                    // Make ItemDefinition for decision
                    String id = makeItemDefinitionId(decision);
                    String decisionName = rdfModel.getName(decision);
                    String name = makeItemDefinitionName(decisionName);
                    boolean isList = isMultipleHit(hitPolicy) && isMultipleCollect(aggregation);
                    OutputClause oc = outputClauses.get(0);
                    ItemDefinition itemDefinition = oc.getItemDefinition();
                    TItemDefinition decisionItemDefinition = makeItemDefinition(id, name, decisionName, isList, itemDefinition);
                    if ("count".equalsIgnoreCase(aggregation)) {
                        decisionItemDefinition.setTypeRef(makeQName(DMN_11.getFeelNamespace(), "number"));
                        decisionItemDefinition.setAllowedValues(null);
                        decisionItemDefinition.getItemComponent().clear();
                    }

                    // Add it to definitions
                    root.getItemDefinition().add(decisionItemDefinition);
                } else {
                    // Make root ItemDefinition for decision
                    TItemDefinition decisionItemDefinition = OBJECT_FACTORY.createTItemDefinition();
                    decisionItemDefinition.setId(makeItemDefinitionId(decision));
                    String decisionName = rdfModel.getName(decision);
                    decisionItemDefinition.setLabel(decisionName);
                    decisionItemDefinition.setName(makeItemDefinitionName(decisionName));

                    decisionItemDefinition.setIsCollection(isMultipleHit(hitPolicy));

                    for (OutputClause oc : outputClauses) {
                        // Make component for clause
                        String id = makeItemDefinitionId(oc, decision);
                        String name = makeItemDefinitionName(oc);
                        String label = oc.getItemDefinition().getName();
                        boolean isList = false;
                        ItemDefinition itemDefinition = oc.getItemDefinition();
                        TItemDefinition itemComponent = makeItemDefinition(id, name, label, isList, itemDefinition);

                        // Add component
                        decisionItemDefinition.getItemComponent().add(itemComponent);
                    }

                    // Add root to definitions
                    root.getItemDefinition().add(decisionItemDefinition);
                }
            } else if (expression instanceof LiteralExpression) {
                // Make ItemDefinition for decision
                String id = makeItemDefinitionId(decision);
                String decisionName = rdfModel.getName(decision);
                boolean isList = false;
                String name = makeItemDefinitionName(decisionName);
                TItemDefinition decisionItemDefinition = makeItemDefinition(id, name, decisionName, isList, ((LiteralExpression) expression).getItemDefinition());

                // Add it to definitions
                root.getItemDefinition().add(decisionItemDefinition);
            } else {
                throw new IllegalArgumentException(String.format("Cannot create ItemDefinition for '%s'", rdfModel.getAboutAttribute(decision)));
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(e);
        }
    }

    private TItemDefinition makeItemDefinition(String id, String name, String label, boolean isList, ItemDefinition itemDefinition) {
        String type = itemDefinition.getType();

        if (isFEELType(type)) {
            TItemDefinition itemComponent = OBJECT_FACTORY.createTItemDefinition();
            itemComponent.setId(id);
            itemComponent.setLabel(label);
            itemComponent.setName(name);

            itemComponent.setTypeRef(makeQName(DMN_11.getFeelNamespace(), convertType(type)));
            itemComponent.setIsCollection(isList || itemDefinition.isList());

            List<EnumItem> enumItems = itemDefinition.getEnumItems();
            if (enumItems != null) {
                TUnaryTests value = OBJECT_FACTORY.createTUnaryTests();
                value.setText(transformAllowedValues(enumItems));
                itemComponent.setAllowedValues(value);
            }
            return itemComponent;
        } else if ("complex".equals(type)) {
            // Make root ItemDefinition for decision
            TItemDefinition decisionItemDefinition = OBJECT_FACTORY.createTItemDefinition();
            decisionItemDefinition.setId(id);
            decisionItemDefinition.setLabel(label);
            decisionItemDefinition.setName(name);
            decisionItemDefinition.setIsCollection(isList || itemDefinition.isList());

            List<ItemDefinitionRelation> relations = itemDefinition.getRelations();
            for(ItemDefinitionRelation rel: relations) {
                String relId = rel.getRelationId();
                String relName = rel.getGitemTitle();
                String relLabel = rel.getTitle();
                boolean relIsList = rel.getValue().getList();
                String relType = rel.getValue().getType();

                TItemDefinition relComponent = OBJECT_FACTORY.createTItemDefinition();
                relComponent.setId(relId);
                relComponent.setLabel(relLabel);
                relComponent.setName(relName);

                relComponent.setTypeRef(makeQName(DMN_11.getFeelNamespace(), convertType(relType)));
                relComponent.setIsCollection(relIsList);

                decisionItemDefinition.getItemComponent().add(relComponent);
            }

            return decisionItemDefinition;
        } else {
            throw new UnsupportedOperationException(String.format("Not supported type '%s'", type));
        }
    }

    private Boolean isMultipleHit(HitPolicy hitPolicy) {
        return HitPolicy.noOrder == hitPolicy
                || HitPolicy.outputOrder == hitPolicy
                || HitPolicy.ruleOrder == hitPolicy;
    }

    private boolean isMultipleCollect(String aggregation) {
        return !
                (
                    "count".equalsIgnoreCase(aggregation) || "min".equalsIgnoreCase(aggregation)
                    || "max".equalsIgnoreCase(aggregation) || "sum".equalsIgnoreCase(aggregation)
                )
        ;
    }

    private void addItemDefinitionsForInputData(TDefinitions root, Element resource) {
        if (rdfModel.isInputData(resource)) {
            String typeName = rdfModel.getFEELType(resource);

            if (hasRelations(resource)) {
                TItemDefinition itemDefinition = OBJECT_FACTORY.createTItemDefinition();
                itemDefinition.setId(makeItemDefinitionId(resource));
                itemDefinition.setLabel(rdfModel.getLabel(resource));
                itemDefinition.setName(makeItemDefinitionName(resource));

                // isList flag
                Boolean isCollection = rdfModel.isList(resource);
                itemDefinition.setIsCollection(isCollection);

                List<Relation> relations = rdfModel.getRelationList(rdfModel.getRelations(resource));
                for(Relation relation: relations) {
                    TItemDefinition itemComponent = OBJECT_FACTORY.createTItemDefinition();
                    itemComponent.setId(makeItemDefinitionId(relation));
                    itemComponent.setLabel(relation.getTitle());
                    itemComponent.setName(makeItemDefinitionName(relation));

                    String type = relation.getValue().getType();
                    if (FEEL_DATA_TYPES.contains(type)) {
                        itemComponent.setTypeRef(makeQName(DMN_11.getFeelNamespace(), type));
                        if (relation.getValue() instanceof EnumerationProperty) {
                            List<EnumItem> enumItems = ((EnumerationProperty)relation.getValue()).getEnumItems();
                            if (enumItems != null) {
                                TUnaryTests value = OBJECT_FACTORY.createTUnaryTests();
                                value.setText(transformAllowedValues(enumItems));
                                itemComponent.setAllowedValues(value);
                            }
                        }
                    } else {
                        throw new UnsupportedOperationException(String.format("Not supported FEEL type '%s'", type));
                    }

                    itemComponent.setIsCollection(relation.isList());

                    itemDefinition.getItemComponent().add(itemComponent);
                }

                root.getItemDefinition().add(itemDefinition);
            } else if (isFEELType(typeName)) {
                TItemDefinition itemDefinition = OBJECT_FACTORY.createTItemDefinition();
                itemDefinition.setId(makeItemDefinitionId(resource));
                itemDefinition.setLabel(rdfModel.getLabel(resource));
                itemDefinition.setName(makeItemDefinitionName(resource));

                // Set type
                itemDefinition.setTypeRef(makeQName(DMN_11.getFeelNamespace(), typeName));

                // isList flag
                Boolean isCollection = rdfModel.isList(resource);
                itemDefinition.setIsCollection(isCollection);

                // Enumerated values
                TUnaryTests value = OBJECT_FACTORY.createTUnaryTests();
                String enumItems = rdfModel.getObject(resource, "enumitems");
                if (!StringUtils.isBlank(enumItems)) {
                    value.setText(transformAllowedValues(enumItems));
                    itemDefinition.setAllowedValues(value);
                }

                root.getItemDefinition().add(itemDefinition);
            }
        } else {
            throw new IllegalArgumentException(String.format("Cannot create ItemDefinition for '%s'", rdfModel.getAboutAttribute(resource)));
        }
    }

    private String makeItemDefinitionId(Element decision) {
        return String.format("item-definition-%s", rdfModel.getAboutAttribute(decision));
    }

    private String makeItemDefinitionId(OutputClause oc, Element decision) {
        return String.format("item-definition-%s-%s", rdfModel.getAboutAttribute(decision), oc.getId());
    }

    private String makeItemDefinitionId(Relation relation) {
        return String.format("item-definition-%s-%s", relation.getTitle().toLowerCase(), relation.getRelationId());
    }

    private String makeItemDefinitionName(Element resource) {
        try {
            String name = rdfModel.getName(resource);
            return makeItemDefinitionName(name);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot make ItemDefinition for resource '%s'", rdfModel.getAboutAttribute(resource)), e);
        }
    }

    private String makeItemDefinitionName(Relation relation) {
        return makeItemDefinitionName(relation.getTitle());
    }

    private String makeItemDefinitionName(OutputClause oc) {
        try {
            String name = oc.getItemDefinition().getName();
            return makeItemDefinitionName(name);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot make ItemDefinition for output clause '%s'", oc.getId()), e);
        }
    }

    String makeItemDefinitionName(String name) {
        return dmnTransformer.nativeFriendlyVariableName(name);
    }

    String makeDecisionName(String name) {
        return makeItemDefinitionName(name);
    }

    String makeDecisionVariableName(String name) {
        return makeDecisionName(name);
    }

    private void addDRGElements(TDefinitions root) {
        for (Element decision : rdfModel.findAllDecision()) {
            TDecision tDecision = makeDecision(root, decision);
            root.getDrgElement().add(OBJECT_FACTORY.createDecision(tDecision));
        }
        for (Element inputData : rdfModel.findAllInputData()) {
            TInputData tInputData = makeInputData(inputData);
            root.getDrgElement().add(OBJECT_FACTORY.createInputData(tInputData));
        }
    }

    private TDecision makeDecision(TDefinitions root, Element resource) {
        TDecision decision = OBJECT_FACTORY.createTDecision();
        setNamedElementProperties(decision, resource);

        addInformationRequirements(decision, resource);

        decision.setVariable(makeDecisionVariable(resource, decision));

        TExpression expression = makeExpression(root, resource);
        if (expression instanceof TDecisionTable) {
            decision.setExpression(OBJECT_FACTORY.createDecisionTable((TDecisionTable) expression));
        } else if (expression instanceof TFunctionDefinition) {
            decision.setExpression(OBJECT_FACTORY.createFunctionDefinition((TFunctionDefinition) expression));
        } else if (expression instanceof TRelation) {
            decision.setExpression(OBJECT_FACTORY.createRelation((TRelation) expression));
        } else if (expression instanceof TList) {
            decision.setExpression(OBJECT_FACTORY.createList((TList) expression));
        } else if (expression instanceof TContext) {
            decision.setExpression(OBJECT_FACTORY.createContext((TContext) expression));
        } else if (expression instanceof TInvocation) {
            decision.setExpression(OBJECT_FACTORY.createInvocation((TInvocation) expression));
        } else if (expression instanceof TLiteralExpression) {
            decision.setExpression(OBJECT_FACTORY.createLiteralExpression((TLiteralExpression) expression));
        } else {
            throw new DMNRuntimeException("Incorrect expression type");
        }

        return decision;
    }

    private void addInformationRequirements(TDecision tDecision, Element decision) {
        List<TInformationRequirement> tInformationRequirements = tDecision.getInformationRequirement();

        // For all related targets
        List<Element> objects = rdfModel.findAllObjects(decision, "target");
        for (Element object : objects) {
            String irId = rdfModel.getResourceAttribute(object);
            // Find related InformationRequirement
            List<Element> allInformationRequirement = rdfModel.findAllInformationRequirement();
            for (Element informationRequirement : allInformationRequirement) {
                if (irId.equals(rdfModel.getAboutAttribute(informationRequirement))) {
                    // Find related InputData or Decision
                    Node target1 = informationRequirement.getElementsByTagName("target").item(0);
                    String relatedElementId = target1.getAttributes().getNamedItem("rdf:resource").getNodeValue();
                    Element target = rdfModel.findDescriptionById(relatedElementId);

                    boolean isDecision = rdfModel.isDecision(target);
                    TInformationRequirement tInformationRequirement = makeInformationRequirement(rdfModel.getAboutAttribute(target), isDecision);
                    tInformationRequirements.add(tInformationRequirement);
                }
            }
        }
    }

    private TInformationItem makeDecisionVariable(Element resource, TDecision decision) {
        TInformationItem item = OBJECT_FACTORY.createTInformationItem();
        item.setId(makeDecisionVariableId(resource));
        item.setName(makeDecisionVariableName(decision.getName()));
        item.setLabel(decision.getName());
        QName typeRef = makeQName(resource);
        item.setTypeRef(typeRef);
        return item;
    }

    private String makeDecisionVariableId(Element resource) {
        return String.format("decision-variable-%s", rdfModel.getAboutAttribute(resource));
    }

    private TExpression makeDecisionTable(TDefinitions root, Element decision, DecisionTable decisionTable) {
        TDecisionTable tDecisionTable = OBJECT_FACTORY.createTDecisionTable();
        tDecisionTable.setId(makeDecisionExpressionId(decision));
        setHitPolicy(decisionTable, tDecisionTable);
        setAggregation(decisionTable, tDecisionTable);
        addInputClauses(decision, decisionTable, tDecisionTable);
        addOutputClauses(decision, decisionTable, tDecisionTable);
        addRules(root, decision, decisionTable, tDecisionTable);
        return tDecisionTable;
    }

    private TExpression makeLiteralExpression(TDefinitions root, Element decision, LiteralExpression expression) {
        TLiteralExpression tLiteralExpression = OBJECT_FACTORY.createTLiteralExpression();
        tLiteralExpression.setId(makeDecisionExpressionId(decision));
        tLiteralExpression.setText(text(expression));
        String expressionLanguage = expression.getExpressionLanguage();
        if (StringUtils.isBlank(expressionLanguage)) {
            tLiteralExpression.setExpressionLanguage(AbstractDMNToNativeTransformer.FREE_TEXT_LANGUAGE);
        }
        return tLiteralExpression;
    }

    private String makeDecisionExpressionId(Element decision) {
        return String.format("decision-expression-%s", rdfModel.getAboutAttribute(decision));
    }

    private void setHitPolicy(DecisionTable decisionTable, TDecisionTable tDecisionTable) {
        HitPolicy hitPolicy = decisionTable.getHitPolicy();
        switch (hitPolicy) {
            case unique:
                tDecisionTable.setHitPolicy(THitPolicy.UNIQUE);
                break;
            case any:
                tDecisionTable.setHitPolicy(THitPolicy.ANY);
                break;
            case first:
                tDecisionTable.setHitPolicy(THitPolicy.FIRST);
                break;
            case priority:
                tDecisionTable.setHitPolicy(THitPolicy.PRIORITY);
                break;
            case noOrder:
                tDecisionTable.setHitPolicy(THitPolicy.COLLECT);
                break;
            case outputOrder:
                tDecisionTable.setHitPolicy(THitPolicy.OUTPUT_ORDER);
                break;
            case ruleOrder:
                tDecisionTable.setHitPolicy(THitPolicy.RULE_ORDER);
                break;
            default:
                tDecisionTable.setHitPolicy(THitPolicy.UNIQUE);
                break;
        }
    }

    private void setAggregation(DecisionTable decisionTable, TDecisionTable tDecisionTable) {
        String aggregation = decisionTable.getAggregation();
        if ("sum".equalsIgnoreCase(aggregation)) {
            tDecisionTable.setAggregation(TBuiltinAggregator.SUM);
        } else if ("count".equalsIgnoreCase(aggregation)) {
            tDecisionTable.setAggregation(TBuiltinAggregator.COUNT);
        } else if ("min".equalsIgnoreCase(aggregation)) {
            tDecisionTable.setAggregation(TBuiltinAggregator.MIN);
        } else if ("max".equalsIgnoreCase(aggregation)) {
            tDecisionTable.setAggregation(TBuiltinAggregator.MAX);
        }
    }

    private void addInputClauses(Element decision, DecisionTable decisionTable, TDecisionTable tDecisionTable) {
        List<TInputClause> tInputClauses = tDecisionTable.getInput();
        for (InputClause inputClause : decisionTable.getInputClauses()) {
            Expression expression = inputClause.getExpression();
            if (expression instanceof Reference) {
                TInputClause tInputClause = OBJECT_FACTORY.createTInputClause();
                tInputClause.setId(makeInputClauseId(inputClause, decision));

                TLiteralExpression tExpression = OBJECT_FACTORY.createTLiteralExpression();
                tExpression.setId(makeInputClauseExpressionId(inputClause, decision));

                Reference reference = (Reference) expression;
                tExpression.setTypeRef(makeQName(reference));
                tExpression.setText(text(reference));
                tInputClause.setInputExpression(tExpression);

                tInputClauses.add(tInputClause);
            } else if (expression instanceof FunctionCall) {
                FunctionCall functionCall = (FunctionCall) expression;

                TInputClause tInputClause = OBJECT_FACTORY.createTInputClause();
                tInputClause.setId(makeInputClauseId(inputClause, decision));

                TLiteralExpression tExpression = OBJECT_FACTORY.createTLiteralExpression();
                tExpression.setId(makeInputClauseExpressionId(inputClause, decision));
                tExpression.setTypeRef(makeQName(functionCall));
                tExpression.setText(text(functionCall));
                tInputClause.setInputExpression(tExpression);

                tInputClauses.add(tInputClause);
            } else {
                throw new UnsupportedOperationException(String.format("'%s' not supported in decision '%s'", expression.getClass().getSimpleName(), rdfModel.getAboutAttribute(decision)));
            }
        }
    }

    private String text(DecisionExpression text) {
        Visitor visitor = new ToDMNVisitor(rdfModel, inputParameters);
        Context params = new Context();
        return text.accept(visitor, params);
    }

    private String text(Expression text) {
        Visitor visitor = new ToDMNVisitor(rdfModel, inputParameters);
        Context params = new Context();
        return text.accept(visitor, params);
    }

    private String text(FunctionCall functionCall) {
        String functionId = functionCall.getFunctionId();
        String text = functionId + "(";
        List<Expression> parameters = functionCall.getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            text += parameters.stream().map(this::text).collect(Collectors.joining(", "));
        }
        text += ")";
        return text;
    }

    private String text(Reference reference) {
        String resourceId = reference.getShapeId();
        try {
            Element resource = rdfModel.findDescriptionById(resourceId);
            StringBuilder text = new StringBuilder(dmnTransformer.nativeFriendlyVariableName(rdfModel.getName(resource)));
            List<String> pathElements = reference.getPathElements();
            if (pathElements != null) {
                for (String pathElement : pathElements) {
                    String name = rdfModel.pathName(resource, pathElement);
                    if (!StringUtils.isBlank(name)) {
                        text.append(".").append(dmnTransformer.nativeFriendlyVariableName(name));
                    }
                }
            }
            return text.toString();
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot build path for reference '%s'", resourceId), e);
        }
    }

    private String makeInputClauseId(InputClause inputClause, Element decision) {
        return String.format("input-clause-%s-%s", rdfModel.getAboutAttribute(decision), inputClause.getId());
    }

    private String makeInputClauseExpressionId(InputClause inputClause, Element decision) {
        return String.format("input-clause-exp-%s-%s", rdfModel.getAboutAttribute(decision), inputClause.getId());
    }

    private void addOutputClauses(Element decision, DecisionTable decisionTable, TDecisionTable tDecisionTable) {
        List<TOutputClause> tOutputClauses = tDecisionTable.getOutput();
        List<OutputClause> outputClauses = decisionTable.getOutputClauses();
        String decisionName = rdfModel.getName(decision);
        if (outputClauses.size() == 0) {
            throw new DMNRuntimeException(String.format("No OutputClauses for decision '%s'", decisionName));
        } else if (outputClauses.size() == 1) {
            OutputClause outputClause = outputClauses.get(0);
            ItemDefinition itemDefinition = outputClause.getItemDefinition();
            String outputClauseName = itemDefinition.getName();
            if (StringUtils.isBlank(outputClauseName)) {
                outputClauseName = decisionName;
            }

            TOutputClause tOutputClause = OBJECT_FACTORY.createTOutputClause();
            tOutputClause.setId(makeOutputClauseId(outputClause, decision));
            tOutputClause.setName(makeOutputClauseName(outputClauseName));
            tOutputClause.setLabel(rdfModel.getLabel(decision));

            String type = itemDefinition.getType();
            if (isFEELType(type)) {
                tOutputClause.setTypeRef(makeQName(DMN_11.getFeelNamespace(), convertType(type)));
                List<EnumItem> enumItems = itemDefinition.getEnumItems();
                if (enumItems != null) {
                    TUnaryTests unaryTests = new TUnaryTests();
                    unaryTests.setText(transformAllowedValues(enumItems));
                    tOutputClause.setOutputValues(unaryTests);
                }
            } else {
                tOutputClause.setTypeRef(makeQName(this.inputParameters.getNamespace(), type));
            }
            tOutputClauses.add(tOutputClause);
        } else {
            for (OutputClause outputClause : outputClauses) {
                try {
                    ItemDefinition itemDefinition = outputClause.getItemDefinition();
                    String outputClauseName = itemDefinition.getName();

                    TOutputClause tOutputClause = OBJECT_FACTORY.createTOutputClause();
                    tOutputClause.setId(makeOutputClauseId(outputClause, decision));
                    tOutputClause.setName(makeOutputClauseName(outputClauseName));
                    tOutputClause.setLabel(outputClauseName);

                    String type = itemDefinition.getType();
                    if (isFEELType(type)) {
                        tOutputClause.setTypeRef(makeQName(DMN_11.getFeelNamespace(), convertType(type)));
                        List<EnumItem> enumItems = itemDefinition.getEnumItems();
                        if (enumItems != null) {
                            TUnaryTests unaryTests = new TUnaryTests();
                            unaryTests.setText(transformAllowedValues(enumItems));
                            tOutputClause.setOutputValues(unaryTests);
                        }
                    } else {
                        tOutputClause.setTypeRef(makeQName(this.inputParameters.getNamespace(), type));
                    }
                    tOutputClauses.add(tOutputClause);
                } catch (Exception e) {
                    throw new DMNRuntimeException(String.format("Cannot process OutputClause '%s'", outputClause.getId()), e);
                }
            }
        }
    }

    private String convertType(String type) {
        return ENUMERATION_TYPE.equals(type) ? STRING_TYPE : type;
    }

    private String makeOutputClauseId(OutputClause oc, Element decision) {
        return String.format("output-clause-%s-%s", rdfModel.getAboutAttribute(decision), oc.getId());
    }

    private String makeOutputClauseName(String name) {
        return makeItemDefinitionName(name);
    }

    private void addRules(TDefinitions root, Element decision, DecisionTable decisionTable, TDecisionTable tDecisionTable) {
        List<TDecisionRule> tRules = tDecisionTable.getRule();
        List<Rule> rules = decisionTable.getRules();
        for (Rule rule : rules) {
            TDecisionRule tDecisionRule = OBJECT_FACTORY.createTDecisionRule();
            tDecisionRule.setId(makeRuleId(rule, decision));

            List<TUnaryTests> inputEntry = tDecisionRule.getInputEntry();
            List<Expression> conditions = rule.getConditions();
            Visitor visitor = new ToDMNVisitor(rdfModel, inputParameters);
            for (int conditionIndex = 0; conditionIndex < conditions.size(); conditionIndex++) {
                Expression condition = conditions.get(conditionIndex);
                TUnaryTests unaryTests = OBJECT_FACTORY.createTUnaryTests();
                Context context = new FeelContext(decision, rdfModel, decisionTable, conditionIndex, true);
                unaryTests.setText(condition.accept(visitor, context));
                inputEntry.add(unaryTests);
            }

            List<TLiteralExpression> outputEntry = tDecisionRule.getOutputEntry();
            List<Expression> conclusions = rule.getConclusions();
            for (int conclusionIndex = 0; conclusionIndex < conclusions.size(); conclusionIndex++) {
                Expression conclusion = conclusions.get(conclusionIndex);
                TLiteralExpression expression = OBJECT_FACTORY.createTLiteralExpression();
                Context context = new FeelContext(decision, rdfModel, decisionTable, conclusionIndex, false);
                String accept = conclusion.accept(visitor, context);
                expression.setText(accept);
                outputEntry.add(expression);
            }

            tRules.add(tDecisionRule);
        }
    }

    private String makeRuleId(Rule rule, Element decision) {
        return String.format("rule-%s-%s", rdfModel.getAboutAttribute(decision), rule.getId());
    }

    private TInputData makeInputData(Element resource) {
        TInputData inputData = OBJECT_FACTORY.createTInputData();
        setNamedElementProperties(inputData, resource);
        inputData.setVariable(makeInputDataVariable(resource, inputData));
        return inputData;
    }

    private TInformationItem makeInputDataVariable(Element resource, TInputData inputData) {
        TInformationItem item = OBJECT_FACTORY.createTInformationItem();
        item.setId(makeInputDataVariableId(resource));
        item.setName(inputData.getName());
        item.setLabel(inputData.getName());
        item.setTypeRef(makeQName(this.inputParameters.getNamespace(), makeItemDefinitionName(resource)));
        return item;
    }

    private boolean hasRelations(Element resource) {
        return !StringUtils.isBlank(rdfModel.getRelations(resource));
    }

    private boolean isFEELType(String type) {
        return FEEL_DATA_TYPES.contains(type);
    }

    private String makeInputDataVariableId(Element resource) {
        return String.format("input-data-variable-%s", rdfModel.getAboutAttribute(resource));
    }

    private String transformAllowedValues(List<EnumItem> enumItems) {
        return enumItems.stream().map(ei -> String.format("\"%s\"", ei.getTitle())).collect(Collectors.joining(", "));
    }

    private String transformAllowedValues(String enumItems) {
        try {
            List<AllowedValue> allowedValues = RDFModel.MAPPER.readValue(enumItems, new TypeReference<List<AllowedValue>>() {
            });
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < allowedValues.size(); i++) {
                AllowedValue allowedValue = allowedValues.get(i);
                if (i != 0) {
                    result.append(", ");
                }
                result.append(String.format("\"%s\"", allowedValue.getTitle()));
            }
            return result.toString();
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot process AllowedValues for '%s'", enumItems), e);
        }
    }

    private TInformationRequirement makeInformationRequirement(String resourceId, boolean isDecision) {
        TInformationRequirement ir = OBJECT_FACTORY.createTInformationRequirement();
        TDMNElementReference reference = OBJECT_FACTORY.createTDMNElementReference();
        reference.setHref(resourceId);
        if (isDecision) {
            ir.setRequiredDecision(reference);
        } else {
            ir.setRequiredInput(reference);
        }
        return ir;
    }

    private TExpression makeExpression(TDefinitions root, Element decision) {
        String decisionText = rdfModel.getDecision(decision);
        if (!StringUtils.isBlank(decisionText)) {
            try {
                DecisionExpression expression = RDFModel.MAPPER.readValue(decisionText, DecisionExpression.class);
                if (expression instanceof DecisionTable) {
                    return makeDecisionTable(root, decision, (DecisionTable) expression);
                } else if (expression instanceof LiteralExpression) {
                    return makeLiteralExpression(root, decision, (LiteralExpression)expression);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot make expression for decision '%s'", decision.getAttribute("id")));
                }
            } catch (IOException e) {
                throw new DMNRuntimeException(String.format("Cannot make expression for decision %s", decision.getAttribute("id")), e);
            }
        } else {
            return null;
        }
    }

    private void setNamedElementProperties(TNamedElement element, Element resource) {
        element.setId(rdfModel.getAboutAttribute(resource));
        element.setLabel(rdfModel.getLabel(resource));
        element.setName(makeDecisionName(rdfModel.getName(resource)));
    }

    private QName makeQName(Reference reference) {
        String resourceId = reference.getShapeId();
        Element resource = rdfModel.findDescriptionById(resourceId);
        return makeQName(resource);
    }

    private QName makeQName(FunctionCall functionCall) {
        String functionId = functionCall.getFunctionId();
        String returnType = FUNCTION_RETURN_TYPE.get(functionId);
        if (returnType != null) {
            return makeQName(DMN_11.getFeelNamespace(), STRING_TYPE);
        }
        throw new UnsupportedOperationException(String.format("'%s' function not supported yet", functionId));
    }

    private QName makeQName(Element resource) {
        return makeQName(this.inputParameters.getNamespace(), makeItemDefinitionName(resource));
    }

    private QName makeQName(String namespace, String name) {
        String cleanName = dmnTransformer.nativeFriendlyName(name);
        return new QName(namespace, cleanName);
    }
}
