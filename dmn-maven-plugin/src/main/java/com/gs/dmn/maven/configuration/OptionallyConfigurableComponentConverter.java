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

import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.AbstractConfigurationConverter;
import org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionallyConfigurableComponentConverter extends AbstractConfigurationConverter {
    public OptionallyConfigurableComponentConverter() {
    }

    @Override
    public boolean canConvert(Class<?> type) {
        return OptionallyConfigurableMojoComponent.class.isAssignableFrom(type);
    }

    @Override
    public Object fromConfiguration(ConverterLookup lookup, PlexusConfiguration configuration, Class<?> type,
                                    Class<?> enclosingType, ClassLoader loader, ExpressionEvaluator evaluator,
                                    ConfigurationListener listener) throws ComponentConfigurationException {
        OptionallyConfigurableMojoComponent component;

        if (configuration == null) {
            throw new ComponentConfigurationException("Cannot instantiate component from null configuration");
        }

        // Instantiate the type as defined in Mojo configuration
        try {
            component = (OptionallyConfigurableMojoComponent) type.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new ComponentConfigurationException(String.format(
                    "Cannot instantiate configurable component type \"%s\" (%s)", type.getName(), ex.getMessage()), ex);
        }

        if (component == null) {
            throw new ComponentConfigurationException(String.format(
                    "Failed to instantiate new configurable component type \"%s\"", type.getName()));
        }

        // Verify that we are deserializing the expected content type
        if (!configuration.getName().equals(component.getElementName())) {
            throw new ComponentConfigurationException(String.format(
                    "Invalid component element \"%s\"; component definition accepts only \"%s\" elements", configuration.getName(), component.getElementName()));
        }

        // Deserialize from either simple or compound data depending on structure of the input configuration
        if (configuration.getChildCount() == 0) {
            return configureSimpleComponent(component, configuration);
        }
        else {
            return configureCompoundComponent(component, configuration);
        }
    }

    private OptionallyConfigurableMojoComponent configureSimpleComponent(OptionallyConfigurableMojoComponent component,
                                                                         PlexusConfiguration configuration) {
        component.setName(configuration.getValue());
        return component;
    }

    private OptionallyConfigurableMojoComponent configureCompoundComponent(OptionallyConfigurableMojoComponent component,
                                                                           PlexusConfiguration configuration) throws ComponentConfigurationException {
        PlexusConfiguration name = configuration.getChild(OptionallyConfigurableMojoComponent.ELEMENT_NAME, false);
        if (name == null || name.getValue() == null) {
            throw new ComponentConfigurationException(String.format(
                    "Cannot configure component; \"%s\" property must be provided", OptionallyConfigurableMojoComponent.ELEMENT_NAME));
        }

        component.setName(name.getValue());

        PlexusConfiguration config = configuration.getChild(OptionallyConfigurableMojoComponent.ELEMENT_CONFIGURATION, false);
        if (config != null) {
            component.setConfiguration(generateConfigurationMap(config));
        }

        return component;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> generateConfigurationMap(PlexusConfiguration configuration) {
        Map<String, Object> node = new HashMap<>();

        for (PlexusConfiguration child : configuration.getChildren()) {
            // Expand node to a list where duplicate keys exist
            Object existingNode = node.get(child.getName());
            if (existingNode != null) {
                if (!(existingNode instanceof List)) {
                    List<Object> listNode = new ArrayList<>();
                    listNode.add(existingNode);
                    node.put(child.getName(), listNode);
                }

                List<Object> target = ((List)node.get(child.getName()));
                target.add(generateChildNode(child));
            }
            else {
                node.put(child.getName(), generateChildNode(child));
            }
        }

        return node;
    }

    private Object generateChildNode(PlexusConfiguration configuration) {
        if (configuration.getChildCount() != 0) {
            return generateConfigurationMap(configuration);
        }
        else {
            return configuration.getValue();
        }
    }
}
