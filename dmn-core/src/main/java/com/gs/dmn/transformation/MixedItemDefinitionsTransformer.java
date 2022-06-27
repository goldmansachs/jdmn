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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.*;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.TestCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.List;

public class MixedItemDefinitionsTransformer extends SimpleDMNTransformer<TestCases> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixedItemDefinitionsTransformer.class);

    private final BuildLogger logger;
    private boolean transformRepository = true;
    private final Visitor visitor = new MixedItemDefinitionsVisitor();

    public MixedItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public MixedItemDefinitionsTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        for (TDefinitions definitions : repository.getAllDefinitions()) {
            definitions.accept(visitor, null);
        }

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestCases>> transform(DMNModelRepository repository, List<TestCases> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }
}

class MixedItemDefinitionsVisitor extends DefaultDMNVisitor {
    @Override
    protected <C> QName visitTypeRef(QName typeRef, C context) {
        if (typeRef != null) {
            String localPart = typeRef.getLocalPart();
            if (localPart != null) {
                localPart = localPart.trim();
                if (localPart.startsWith("sig.")) {
                    return new QName(localPart.substring(4));
                }
            }
        }
        return typeRef;
    }

    @Override
    public <C> Object visit(TItemDefinition element, C context) {
        super.visit(element, context);

        if (isMixed(element) && isEmpty(element)) {
            QName typeRef = new QName("feel.Any");
            element.setTypeRef(typeRef);
        }

        return element;
    }

    boolean isMixed(TItemDefinition element) {
        if (element == null) {
            return false;
        }
        TDMNElement.ExtensionElements extensionElements = element.getExtensionElements();
        if (extensionElements == null) {
            return false;
        }
        for (Object any : extensionElements.getAny()) {
            if ("mixed".equals(any)) {
                return true;
            }
        }
        return false;
    }

    boolean isEmpty(TItemDefinition element) {
        if (element == null) {
            return false;
        }
        return element.getTypeRef() == null
                && element.getItemComponent().isEmpty()
                && element.getFunctionItem() == null;
    }
}