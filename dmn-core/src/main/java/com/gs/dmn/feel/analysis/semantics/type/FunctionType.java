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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.feel.analysis.semantics.environment.Parameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.*;

public abstract class FunctionType extends Type {
    protected final List<FormalParameter> parameters = new ArrayList<>();
    protected final List<Type> parameterTypes = new ArrayList<>();
    protected Type returnType;

    protected FunctionType(List<FormalParameter> parameters, Type returnType) {
        this.returnType = returnType;
        if (parameters != null) {
            this.parameters.addAll(parameters);
            this.parameterTypes.addAll(parameters.stream().map(FormalParameter::getType).collect(Collectors.toList()));
        }
    }

    public List<FormalParameter> getParameters() {
        return this.parameters;
    }

    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public void setReturnType(Type returnType) {
        if (returnType != null) {
            this.returnType = returnType;
        }
    }

    @Override
    public boolean isValid() {
        if (this.returnType == null) {
            return false;
        }
        return this.parameterTypes.stream().allMatch(Type::isValid)
                && this.returnType.isValid();
    }

    public boolean isStaticTyped() {
        return this.parameters.stream().allMatch(p -> p.getType() != null);
    }

    public List<Pair<ParameterTypes, ParameterConversions>> matchCandidates(ParameterTypes parameterTypes) {
        if (parameterTypes instanceof PositionalParameterTypes) {
            List<Type> argumentTypes = ((PositionalParameterTypes) parameterTypes).getTypes();
            return matchCandidates(argumentTypes);
        } else {
            NamedParameterTypes namedSignature = (NamedParameterTypes) parameterTypes;
            List<Type> argumentTypes = new ArrayList<>();
            for(FormalParameter parameter: this.parameters) {
                Type type = namedSignature.getType(parameter.getName());
                if (type != null) {
                    argumentTypes.add(type);
                } else {
                    if (parameter instanceof Parameter) {
                        if (((Parameter) parameter).isOptional()) {
                        } else if (((Parameter) parameter).isVarArg()) {
                        } else {
                            return new ArrayList<>();
                        }
                    } else {
                        return new ArrayList<>();
                    }
                }
            }
            return matchCandidates(argumentTypes);
        }
    }

    protected ArrayList<Pair<ParameterTypes, ParameterConversions>> calculateCandidates(List<Type> parameterTypes, List<Type> argumentTypes) {
        // calculate candidates
        List<Pair<ParameterTypes, ParameterConversions>> candidates = new ArrayList<>();
        int argumentSize = argumentTypes.size();
        int conversionSize = ConversionKind.values().length;

        // For every sequence of conversions
        int[] conversionMap = init(argumentSize);
        conversionMap = next(conversionMap, argumentSize, conversionSize);
        while (conversionMap != null) {
            // Calculate new types and conversions for every argument
            List<Type> newTypes = new ArrayList<>();
            PositionalParameterConversions conversions = new PositionalParameterConversions();
            boolean different = false;
            for (int i = 0; i < argumentSize; i++) {
                // Compute new type and conversion
                ConversionKind kind = ConversionKind.values()[conversionMap[i]];
                Type argumentType = argumentTypes.get(i);
                Type newType = argumentType;
                Conversion conversion = new Conversion(NONE, newType);
                if (i < parameterTypes.size()) {
                    Type parameterType = parameterTypes.get(i);
                    if (!argumentType.conformsTo(parameterType)) {
                        if (kind == ELEMENT_TO_LIST) {
                            // When the type of the expression is T and the target type is List<T> the expression is converted to a singleton list.
                            if (parameterType instanceof ListType) {
                                if (argumentType.equivalentTo(((ListType) parameterType).getElementType())) {
                                    newType = new ListType(argumentType);
                                    conversion = new Conversion(kind, newType);

                                    different = true;
                                }
                            }
                        } else if (kind == LIST_TO_ELEMENT) {
                            // When the type of the expression is List<T>, the value of the expression is a singleton list and the target type is T,
                            // the expression is converted by unwraping the first element.
                            if (argumentType instanceof ListType) {
                                if (parameterType.equivalentTo(((ListType) argumentType).getElementType())) {
                                    newType = ((ListType) argumentType).getElementType();
                                    conversion = new Conversion(kind, newType);

                                    different = true;
                                }
                            }
                        } else if (kind == CONFORMS_TO) {
                            // When the type of the expression is T1, the target type is T2, and T1 conforms to T2 the value of expression
                            // remains unchanged. Otherwise the result is null.
                            if (!argumentType.conformsTo(parameterType)) {
                                newType = NullType.NULL;
                                conversion = new Conversion(kind, newType);

                                different = true;
                            }
                        }
                        newTypes.add(newType);
                        conversions.add(conversion);
                    }
                }
            }

            // Add new candidate
            if (different) {
                PositionalParameterTypes newSignature = new PositionalParameterTypes(newTypes);
                candidates.add(new Pair<>(newSignature, conversions));
            }

            // Next sequence
            conversionMap = next(conversionMap, argumentSize, conversionSize);
        }
        return new ArrayList<>(candidates);
    }

    // Initialise the sequence of vectors
    protected int[] init(int power) {
        int[] vector = new int[power];
        for (int i = 0; i < power; i++) {
            vector[i] = 0;
        }
        return vector;
    }

    // Generate next vector in sequence
    protected int[] next(int[] vector, int power, int cardinal) {
        for (int i = power - 1; i >= 0; i--) {
            if (vector[i] == cardinal - 1) {
                vector[i] = 0;
            } else {
                vector[i]++;
                break;
            }
        }
        // Check end of sequence
        boolean end = true;
        for (int i = 0; i < power; i++) {
            if (vector[i] != 0) {
                end = false;
                break;
            }
        }
        return end ? null : vector;
    }

    public abstract boolean match(ParameterTypes parameterTypes);

    protected abstract List<Pair<ParameterTypes, ParameterConversions>> matchCandidates(List<Type> argumentTypes);
}
