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
package com.gs.dmn.ast.visitor;

import com.gs.dmn.ast.Visitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;

public abstract class AbstractVisitor<C, R> implements Visitor<C, R> {
    protected final BuildLogger logger;
    protected final ErrorHandler errorHandler;

    protected AbstractVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        this.logger = logger;
        this.errorHandler = errorHandler;
    }
}
