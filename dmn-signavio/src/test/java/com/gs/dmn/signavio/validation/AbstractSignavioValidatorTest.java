/*
 Copyright 2016.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.validation.AbstractValidatorTest;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;
import java.net.URI;

public abstract class AbstractSignavioValidatorTest extends AbstractValidatorTest {
    @Override
    protected DMNModelRepository makeRepository(URI fileURI) {
        File input = new File(fileURI);
        Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(input);
        return new SignavioDMNModelRepository(pair);
    }
}