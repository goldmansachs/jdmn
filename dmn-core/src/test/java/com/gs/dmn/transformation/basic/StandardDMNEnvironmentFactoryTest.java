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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardDMNEnvironmentFactoryTest extends AbstractTest {
    private static final JavaTimeDMNDialectDefinition DIALECT_DEFINITION = new JavaTimeDMNDialectDefinition();
    private static final InputParameters INPUT_PARAMETERS = new InputParameters();
    private StandardDMNEnvironmentFactory environmentFactory;
    private final DMNSerializer dmnSerializer = DIALECT_DEFINITION.createDMNSerializer(new NopBuildLogger(), inputParameters);

    @Test
    public void testToFEELType() {
        // Read DMN models
        DMNModelRepository repository = readModels("other/1.5/cycles-no-prefix/translator/");

        // Find model-a and model-b
        TDefinitions modelA = repository.findModelByName("model-a");
        assertNotNull(modelA);
        TDefinitions modelB = repository.findModelByName("model-b");
        assertNotNull(modelB);

        // Find ItemDefinitions to test
        TItemDefinition t1 = findItemDefinition(modelA, "t1");
        TItemDefinition t2 = findItemDefinition(modelA, "t2");
        TItemDefinition other = findItemDefinition(modelB, "other");
        TItemDefinition node = findItemDefinition(modelA, "node");
        TItemDefinition key = findItemDefinition(node, "key");
        TItemDefinition defReference = findItemDefinition(node, "def");
        TItemDefinition defDefinition = findItemDefinition(modelB, "def");
        TItemDefinition next = findItemDefinition(node, "next");

        // Create factory
        BasicDMNToNativeTransformer<Type, DMNContext> transformer = DIALECT_DEFINITION.createBasicTransformer(repository, new NopLazyEvaluationDetector(), INPUT_PARAMETERS);
        this.environmentFactory = new StandardDMNEnvironmentFactory(transformer);

        // Test model-a
        assertThrows(SemanticError.class, () -> {
            assertNull(this.environmentFactory.toFEELType(t1));
        });
        assertThrows(SemanticError.class, () -> {
            assertNull(this.environmentFactory.toFEELType(t2));
        });
        assertThrows(SemanticError.class, () -> {
            assertNull(this.environmentFactory.toFEELType(node));
        });
        assertEquals(StringType.STRING, this.environmentFactory.toFEELType(key));
        assertEquals(StringType.STRING, this.environmentFactory.toFEELType(defReference));
        assertThrows(SemanticError.class, () -> {
            assertNull(this.environmentFactory.toFEELType(next));
        });

        // Test model-b
        assertThrows(SemanticError.class, () -> {
            assertNull(this.environmentFactory.toFEELType(other));
        });
        assertEquals(StringType.STRING, this.environmentFactory.toFEELType(defDefinition));

        // Test other toFEELType() methods
        assertThrows(SemanticError.class, () -> {
            assertEquals(StringType.STRING, this.environmentFactory.toFEELType(modelA, "key"));
        });
        assertEquals(StringType.STRING, this.environmentFactory.toFEELType(modelB, "def"));
        assertThrows(SemanticError.class, () -> {
            assertEquals(StringType.STRING, this.environmentFactory.toFEELType(modelA, QualifiedName.toQualifiedName("", "key")));
        });
        assertEquals(StringType.STRING, this.environmentFactory.toFEELType(modelB, QualifiedName.toQualifiedName("", "def")));
    }

    private TItemDefinition findItemDefinition(TDefinitions modelA, String name) {
        return modelA.getItemDefinition().stream().filter(i -> i.getName().equals(name)).findFirst().orElseThrow();
    }

    private TItemDefinition findItemDefinition(TItemDefinition parent, String name) {
        return parent.getItemComponent().stream().filter(i -> i.getName().equals(name)).findFirst().orElseThrow();
    }

    @Override
    protected URI resource(String path) {
        return tckResource(path);
    }

    private DMNModelRepository readModels(String pathName) {
        URI resource = resource(pathName);
        File input = new File(resource);
        List<TDefinitions> definitionsList = this.dmnSerializer.readModels(input);
        return new DMNModelRepository(definitionsList);
    }
}