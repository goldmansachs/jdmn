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
package com.gs.dmn.transformation.native_;

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.native_.expression.NativeExpressionFactory;
import com.gs.dmn.transformation.native_.statement.NativeStatementFactory;
import org.omg.spec.dmn._20191111.model.TDRGElement;


public interface NativeFactory extends NativeExpressionFactory, NativeStatementFactory {
    String TO_PROTO_CONVERSION_METHOD = "toProto";

    String extractParameterFromRequestMessage(TDRGElement element, Pair<String, Type> parameter, boolean staticContext);

    String extractMemberFromProtoValue(String protoValue, Type type, boolean staticContext);
}
