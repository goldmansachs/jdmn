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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.configuration.DefaultPlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.junit.Assert;

public abstract class AbstractMojoConfigurationTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OptionallyConfigurableComponentConverter converter = new OptionallyConfigurableComponentConverter();

    protected PlexusConfiguration generateComponentConfiguration(String componentName, String elementName, PlexusConfiguration configuration) {
        PlexusConfiguration componentConfig = new DefaultPlexusConfiguration(elementName);

        if (componentName != null) {
            componentConfig.addChild(OptionallyConfigurableMojoComponent.ELEMENT_NAME, componentName);
        }

        if (configuration != null) {
            componentConfig.addChild(configuration);
        }

        return componentConfig;
    }

    protected OptionallyConfigurableMojoComponent parseConfiguration(PlexusConfiguration configuration, Class<?> target) throws ComponentConfigurationException {
        return (OptionallyConfigurableMojoComponent)converter.fromConfiguration(null, configuration, target,
                null, null, null, null);
    }

    protected void assertEquivalentConfiguration(OptionallyConfigurableMojoComponent component, String serialisedExpected) throws Exception {
        Object expected = mapper.readValue(serialisedExpected, Object.class);

        Assert.assertEquals("Component configuration does not match expected values", expected, component.getConfiguration());
    }
}
