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
package com.gs.dmn.serialization.xstream.v1_5;

import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.VersionXStreamMarshaller;
import com.gs.dmn.serialization.xstream.v1_1.*;
import com.gs.dmn.serialization.xstream.v1_2.AuthorityRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_2.BusinessKnowledgeModelConverter;
import com.gs.dmn.serialization.xstream.v1_2.ContextEntryConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionRuleConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionServiceConverter;
import com.gs.dmn.serialization.xstream.v1_2.DecisionTableConverter;
import com.gs.dmn.serialization.xstream.v1_2.FunctionDefinitionConverter;
import com.gs.dmn.serialization.xstream.v1_2.ImportConverter;
import com.gs.dmn.serialization.xstream.v1_2.ImportedValuesConverter;
import com.gs.dmn.serialization.xstream.v1_2.InformationRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_2.KnowledgeRequirementConverter;
import com.gs.dmn.serialization.xstream.v1_3.DefinitionsConverter;
import com.gs.dmn.serialization.xstream.v1_3.FunctionItemConverter;
import com.gs.dmn.serialization.xstream.v1_3.GroupConverter;
import com.gs.dmn.serialization.xstream.v1_3.ItemDefinitionConverter;
import com.gs.dmn.serialization.xstream.v1_4.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.util.List;

public class XStreamMarshaller extends VersionXStreamMarshaller {
    private static final StaxDriver STAX_DRIVER = makeStaxDriver(DMNVersion.DMN_15);

    public XStreamMarshaller() {
        super(DMNVersion.DMN_15);
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        super(DMNVersion.DMN_15, extensionRegisters);
    }

    @Override
    protected XStream newXStream() {
        XStream xStream = createXStream();

        registerCommonAliases(xStream);

        registerNew12Parts(xStream);

        registerNew13Parts(xStream);

        register13DMNDIParts(xStream);

        registerNew14Parts(xStream);

        xStream.registerConverter(new AssociationConverter(xStream, this.version));
        xStream.registerConverter(new AuthorityRequirementConverter(xStream, this.version));
        xStream.registerConverter(new BindingConverter(xStream, this.version));
        xStream.registerConverter(new BusinessKnowledgeModelConverter(xStream, this.version));
        xStream.registerConverter(new ContextConverter(xStream, this.version));
        xStream.registerConverter(new ContextEntryConverter(xStream, this.version));
        xStream.registerConverter(new DecisionConverter(xStream, this.version));
        xStream.registerConverter(new DecisionRuleConverter(xStream, this.version));
        xStream.registerConverter(new DecisionServiceConverter(xStream, this.version));
        xStream.registerConverter(new DecisionTableConverter(xStream, this.version));
        xStream.registerConverter(new DefinitionsConverter(xStream, this.version));
        xStream.registerConverter(new DMNElementReferenceConverter(xStream, this.version));
        xStream.registerConverter(new GroupConverter(xStream, this.version));
        xStream.registerConverter(new FunctionDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new ImportConverter(xStream, this.version));
        xStream.registerConverter(new ImportedValuesConverter(xStream, this.version));
        xStream.registerConverter(new InformationItemConverter(xStream, this.version));
        xStream.registerConverter(new InformationRequirementConverter(xStream, this.version));
        xStream.registerConverter(new InputClauseConverter(xStream, this.version));
        xStream.registerConverter(new InputDataConverter(xStream, this.version));
        xStream.registerConverter(new InvocationConverter(xStream, this.version));
        xStream.registerConverter(new ItemDefinitionConverter(xStream, this.version));
        xStream.registerConverter(new KnowledgeRequirementConverter(xStream, this.version));
        xStream.registerConverter(new KnowledgeSourceConverter(xStream, this.version));
        xStream.registerConverter(new LiteralExpressionConverter(xStream, this.version));
        xStream.registerConverter(new OrganizationUnitConverter(xStream, this.version));
        xStream.registerConverter(new OutputClauseConverter(xStream, this.version));
        xStream.registerConverter(new PerformanceIndicatorConverter(xStream, this.version));
        xStream.registerConverter(new RelationConverter(xStream, this.version));
        xStream.registerConverter(new TextAnnotationConverter(xStream, this.version));
        xStream.registerConverter(new UnaryTestsConverter(xStream, this.version));
        xStream.registerConverter(new FunctionItemConverter(xStream, this.version));

        xStream.registerConverter(new ChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new TypedChildExpressionConverter(xStream, this.version));
        xStream.registerConverter(new ForConverter(xStream, this.version));
        xStream.registerConverter(new EveryConverter(xStream, this.version));
        xStream.registerConverter(new SomeConverter(xStream, this.version));
        xStream.registerConverter(new ConditionalConverter(xStream, this.version));
        xStream.registerConverter(new FilterConverter(xStream, this.version));

        xStream.registerConverter(new QNameConverter(this.version));
        xStream.registerConverter(new DMNListConverter(xStream, this.version));
        xStream.registerConverter(new ElementCollectionConverter(xStream, this.version));
        xStream.registerConverter(new ExtensionElementsConverter(xStream, this.version, extensionRegisters));

        xStream.ignoreUnknownElements();

        registerExtensionConverters(xStream);

        return xStream;
    }

    @Override
    protected HierarchicalStreamDriver getStaxDriver() {
        return STAX_DRIVER;
    }
}
