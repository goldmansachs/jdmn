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
package com.gs.dmn.maven.configuration;

import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.configuration.DefaultPlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DMNTransformerComponentConfigurationTest extends AbstractMojoConfigurationTest {
    private static final String ELEMENT_NAME = (new DMNTransformerComponent().getElementName());

    @Test
    public void testBasicConfiguration() throws Exception {
        PlexusConfiguration config = new DefaultPlexusConfiguration(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION)
                .addChild("key1", "value1")
                .addChild("key2", "value2");

        PlexusConfiguration configuration = generateComponentConfiguration("componentName", ELEMENT_NAME, config);
        OptionallyConfigurableMojoComponent component = parseConfiguration(configuration, DMNTransformerComponent.class);

        assertEquivalentConfiguration(component,
                """
                {
                  "key1": "value1",
                  "key2": "value2"
                }\
                """);
    }

    @Test
    public void testNullConfiguration() throws Exception {
        PlexusConfiguration configuration = generateComponentConfiguration("componentName", ELEMENT_NAME, null);
        OptionallyConfigurableMojoComponent component = parseConfiguration(configuration, DMNTransformerComponent.class);

        assertEquivalentConfiguration(component, "{ }");
    }

    @Test
    public void testMissingComponentName() throws Exception {
        Assertions.assertThrows(ComponentConfigurationException.class, () -> {
            PlexusConfiguration config = new DefaultPlexusConfiguration(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION)
                    .addChild("key1", "value1")
                    .addChild("key2", "value2");

            PlexusConfiguration configuration = generateComponentConfiguration(null, ELEMENT_NAME, config);
            parseConfiguration(configuration, DMNTransformerComponent.class);

            Assertions.fail("Test is expected to fail; mandatory component name was not provided");
        });
    }

    @Test
    public void testNestedConfiguration() throws Exception {
        PlexusConfiguration inner = new DefaultPlexusConfiguration("innerConfig");
        inner.addChild("innerKey1", "innerValue1");

        PlexusConfiguration intermediate = new DefaultPlexusConfiguration("intermediateConfig");
        intermediate.addChild("intermediateKey1", "intermediateValue1");
        intermediate.addChild(inner);

        PlexusConfiguration config = new DefaultPlexusConfiguration(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION);
        config.addChild("key1", "value1");
        config.addChild(intermediate);

        PlexusConfiguration configuration = generateComponentConfiguration("componentName", ELEMENT_NAME, config);
        OptionallyConfigurableMojoComponent component = parseConfiguration(configuration, DMNTransformerComponent.class);

        assertEquivalentConfiguration(component, """
                {
                  "key1": "value1",
                  "intermediateConfig": {
                    "intermediateKey1": "intermediateValue1",
                    "innerConfig": {
                      "innerKey1": "innerValue1"\s
                    }
                  }
                }\
                """);
    }

    @Test
    public void testDuplicateKeyConfiguration() throws Exception {
        PlexusConfiguration config = new DefaultPlexusConfiguration(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION)
                .addChild("key1", "value1")
                .addChild("key2", "value2a")
                .addChild("key2", "value2b")
                .addChild("key2", "value2c")
                .addChild("key3", "value3");

        PlexusConfiguration configuration = generateComponentConfiguration("componentName", ELEMENT_NAME, config);
        OptionallyConfigurableMojoComponent component = parseConfiguration(configuration, DMNTransformerComponent.class);

        assertEquivalentConfiguration(component, """
                {
                  "key1": "value1",
                  "key2": [ "value2a", "value2b", "value2c" ],
                  "key3": "value3"
                }\
                """);
    }

    @Test
    public void testMixedCompoundListConfiguration() throws Exception {
        PlexusConfiguration inner = new DefaultPlexusConfiguration("nestedList");
        inner.addChild("innerKey1", "innerValue1");
        inner.addChild("innerKey1", "innerValue2");

        PlexusConfiguration nestedData = new DefaultPlexusConfiguration("key2");
        nestedData.addChild("nestedValue1", "value1");
        nestedData.addChild("nestedValue2", "value2");
        nestedData.addChild(inner);

        PlexusConfiguration config = new DefaultPlexusConfiguration(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION);
        config.addChild("key1", "value1");
        config.addChild("key2", "singleValue");
        config.addChild(nestedData);

        PlexusConfiguration configuration = generateComponentConfiguration("componentName", ELEMENT_NAME, config);
        OptionallyConfigurableMojoComponent component = parseConfiguration(configuration, DMNTransformerComponent.class);

        assertEquivalentConfiguration(component, """
                {
                  "key1": "value1",
                  "key2": [
                    "singleValue",
                    {
                      "nestedList": { "innerKey1": [ "innerValue1", "innerValue2" ] },
                      "nestedValue1": "value1",
                      "nestedValue2": "value2"
                    }
                  ]
                }\
                """);
    }

}
