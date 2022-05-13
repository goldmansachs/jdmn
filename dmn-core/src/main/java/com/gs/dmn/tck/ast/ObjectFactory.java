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
package com.gs.dmn.tck.ast;

public class ObjectFactory {
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.omg.dmn.tck.marshaller._20160719
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestCases }
     * 
     */
    public TestCases createTestCases() {
        return new TestCases();
    }

    /**
     * Create an instance of {@link TestCase }
     *
     */
    public TestCase createTestCase() {
        return new TestCase();
    }

    /**
     * Create an instance of {@link Labels }
     *
     */
    public Labels createLabels() {
        return new Labels();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link InputNode }
     *
     */
    public InputNode createInputNode() {
        return new InputNode();
    }

    /**
     * Create an instance of {@link List }
     * 
     */
    public List createList() {
        return new List();
    }

    /**
     * Create an instance of {@link ResultNode }
     * 
     */
    public ResultNode createResultNode() {
        return new ResultNode();
    }

    /**
     * Create an instance of {@link ExtensionElements }
     *
     */
    public ExtensionElements createExtensionElements() {
        return new ExtensionElements();
    }
}
