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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;

public class ProtoBufferPythonFactory extends ProtoBufferFactory {
    public ProtoBufferPythonFactory(BasicDMNToJavaTransformer transformer) {
        super(transformer);
    }

    @Override
    public String drgElementSignatureProto(TDRGElement element) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String drgElementArgumentListProto(TDRGElement element) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    protected String toNativeProtoType(String feelType) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
