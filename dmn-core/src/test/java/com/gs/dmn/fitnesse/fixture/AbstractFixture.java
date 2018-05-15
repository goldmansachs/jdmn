/**
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
package com.gs.dmn.fitnesse.fixture;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.DefaultDMNEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import fit.ColumnFixture;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;

public class AbstractFixture extends ColumnFixture {
    protected final DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();
    protected final StandardFEELLib lib;
    protected Scope scope;

    public AbstractFixture() {
        this.lib = (StandardFEELLib) dialectDefinition.createFEELLib();

        fitnesse.slim.converters.ConverterRegistry.addConverter(Scope.class, new ScopeConverter());
        fitnesse.slim.converters.ConverterRegistry.addConverter(Context.class, new ContextConverter());
        fitnesse.slim.converters.ConverterRegistry.addConverter(Duration.class, new DurationConverter());
        fitnesse.slim.converters.ConverterRegistry.addConverter(BigDecimal.class, new BigDecimalConverter());
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    protected FEELContext makeContext() {
        Environment environment = makeEnvironment(scope);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(scope);
        return FEELContext.makeContext(environment, runtimeEnvironment);
    }

    protected Environment makeEnvironment(Scope scope) {
        Environment environment = DefaultDMNEnvironmentFactory.instance().makeEnvironment();
        if (scope != null) {
            for(ScopeEntry entry: scope) {
                String name = entry.getName();
                Type type = makeType(entry.getType());
                environment.addDeclaration(DefaultDMNEnvironmentFactory.instance().makeVariableDeclaration(name, type));
            }
        }
        return environment;
    }

    protected Environment makeInputEntryEnvironment(Environment parent, Expression inputExpression) {
        Environment environment = DefaultDMNEnvironmentFactory.instance().makeEnvironment(parent, inputExpression);
        if (inputExpression != null) {
            environment.addDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, DefaultDMNEnvironmentFactory.instance().makeVariableDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
        }
        return environment;
    }

    protected RuntimeEnvironment makeRuntimeEnvironment(Scope scope) {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        if (scope != null) {
            for(ScopeEntry entry: scope) {
                String name = entry.getName();
                Type type = makeType(entry.getType());
                runtimeEnvironment.bind(name, makeValue(type, entry.getValue()));
            }
        }
        return runtimeEnvironment;
    }

    protected Type makeType(String type) {
        return TypeDeserializer.instance().deserialize(type);
    }

    private Object makeValue(Type type, Object value) {
        if (value == null) {
            return null;
        }
        if (type == NumberType.NUMBER) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return lib.number((String) value);
        } else if (type == BooleanType.BOOLEAN) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return Boolean.parseBoolean((String) value);
        } else if (type == DateType.DATE) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return lib.date((String) value);
        } else if (type == TimeType.TIME) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return lib.time((String) value);
        } else if (type == DateTimeType.DATE_AND_TIME) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return lib.dateAndTime((String) value);
        } else if (type == DurationType.YEARS_AND_MONTHS_DURATION || type == DurationType.DAYS_AND_TIME_DURATION) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            return lib.duration((String) value);
        }
        return value;
    }
}
