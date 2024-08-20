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

import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.runtime.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.*;

public abstract class FunctionType implements com.gs.dmn.el.analysis.semantics.type.FunctionType {
    public static final FunctionType ANY_FUNCTION = new FunctionType(Collections.emptyList(), ANY) {
        @Override
        public boolean equivalentTo(Type other) {
            return this == other;
        }

        @Override
        public boolean conformsTo(Type other) {
            return equivalentTo(other);
        }

        @Override
        public boolean match(ParameterTypes<Type> parameterTypes) {
            return false;
        }

        @Override
        protected List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(List argumentTypes) {
            return null;
        }
    };

    protected final List<FormalParameter<Type>> parameters = new ArrayList<>();
    protected final List<Type> parameterTypes = new ArrayList<>();
    protected Type returnType;

    protected FunctionType(List<FormalParameter<Type>> parameters, Type returnType) {
        this.returnType = returnType;
        if (parameters != null) {
            this.parameters.addAll(parameters);
            this.parameterTypes.addAll(parameters.stream().map(FormalParameter::getType).collect(Collectors.toList()));
        }
    }

    public List<FormalParameter<Type>> getParameters() {
        return this.parameters;
    }

    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public Type getReturnType() {
        return this.returnType;
    }

    public void setReturnType(Type returnType) {
        if (returnType != null) {
            this.returnType = returnType;
        }
    }

    @Override
    public boolean isFullySpecified() {
        if (Type.isNull(this.returnType)) {
            return false;
        }
        return this.parameterTypes.stream().noneMatch(com.gs.dmn.el.analysis.semantics.type.Type::isNullOrAny)
                && !com.gs.dmn.el.analysis.semantics.type.Type.isNullOrAny(this.returnType);
    }

    public List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(ParameterTypes<Type> parameterTypes) {
        if (parameterTypes instanceof PositionalParameterTypes) {
            List<Type> argumentTypes = ((PositionalParameterTypes<Type>) parameterTypes).getTypes();
            return matchCandidates(argumentTypes);
        } else {
            NamedParameterTypes<Type> namedSignature = (NamedParameterTypes<Type>) parameterTypes;
            List<Type> argumentTypes = new ArrayList<>();
            for(FormalParameter<Type> parameter: this.parameters) {
                Type type = namedSignature.getType(parameter.getName());
                if (!com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
                    argumentTypes.add(type);
                } else if (!(parameter.isOptional() || parameter.isVarArg())) {
                    return new ArrayList<>();
                }
            }
            return matchCandidates(argumentTypes);
        }
    }

    protected ArrayList<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> calculateCandidates(List<Type> parameterTypes, List<Type> argumentTypes) {
        // calculate candidates
        Set<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> candidates = new LinkedHashSet<>();
        int argumentSize = argumentTypes.size();
        ConversionKind[] candidateConversions = FUNCTION_RESOLUTION_CANDIDATES;
        int conversionSize = candidateConversions.length;

        // For every sequence of conversions
        int[] conversionMap = init(argumentSize);
        // Skip the exact match
        conversionMap = next(conversionMap, argumentSize, conversionSize);
        while (conversionMap != null) {
            // Calculate new types and conversions for every argument
            List<Type> newTypes = new ArrayList<>();
            PositionalParameterConversions<Type> conversions = new PositionalParameterConversions<>();
            boolean different = false;
            boolean succsefulCandidate = true;
            for (int i = 0; i < argumentSize; i++) {
                // Compute new type and conversion
                ConversionKind kind = candidateConversions[conversionMap[i]];
                Type argumentType = argumentTypes.get(i);
                Type newArgumentType = argumentType;
                Conversion<Type> conversion = new Conversion<>(NONE, newArgumentType);
                if (i < parameterTypes.size()) {
                    Type parameterType = parameterTypes.get(i);
                    if (kind == NONE) {
                        // No conversion
                    } else if (kind == ELEMENT_TO_SINGLETON_LIST) {
                        // When the type of the expression is T and the target type is List<T> the expression is converted to a singleton list.
                        if (parameterType instanceof ListType) {
                            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentType, ((ListType) parameterType).getElementType())) {
                                newArgumentType = new ListType(argumentType);
                                conversion = new Conversion<>(kind, newArgumentType);
                                different = true;
                            }
                        }
                    } else if (kind == SINGLETON_LIST_TO_ELEMENT) {
                        // When the type of the expression is List<T>, the value of the expression is a singleton list and the target type is T,
                        // the expression is converted by unwraping the first element.
                        if (argumentType instanceof ListType) {
                            if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(parameterType, ((ListType) argumentType).getElementType())) {
                                newArgumentType = ((ListType) argumentType).getElementType();
                                conversion = new Conversion<>(kind, newArgumentType);

                                different = true;
                            }
                        }
                    } else if (kind == DATE_TO_UTC_MIDNIGHT) {
                        // When the type of the expression is date, the value of the expression is a date and the target type is date and time,
                        // the expression is converted to UTC midnight data and time.
                        if (com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(argumentType, DATE) && com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(parameterType, DATE_AND_TIME)) {
                            newArgumentType = DATE_AND_TIME;
                            conversion = new Conversion<>(kind, newArgumentType);

                            different = true;
                        }
                    } else {
                        throw new SemanticError(String.format("Conversion '%s' is not supported yet", kind));
                    }
                    // Check if new argument type matches
                    boolean newArgumentTypeOk = Type.conformsTo(newArgumentType, parameterType);
                    if (!newArgumentTypeOk) {
                        succsefulCandidate = false;
                        break;
                    } else {
                        newTypes.add(newArgumentType);
                        conversions.add(conversion);
                    }
                }
            }

            // Add new candidate
            if (different && succsefulCandidate) {
                PositionalParameterTypes<Type> newSignature = new PositionalParameterTypes<>(newTypes);
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

    protected boolean compatible(ParameterTypes<Type> parameterTypes, List<FormalParameter<Type>> parameters) {
        if (parameterTypes instanceof PositionalParameterTypes) {
            return compatible((PositionalParameterTypes<Type>) parameterTypes, parameters);
        } else {
            return compatible((NamedParameterTypes<Type>) parameterTypes, parameters);
        }
    }

    private boolean compatible(PositionalParameterTypes<Type> parameterTypes, List<FormalParameter<Type>> parameters) {
        if (parameterTypes.size() != parameters.size()) {
            return false;
        }
        for (int i = 0; i < parameters.size(); i++) {
            Type formalParameterType = parameters.get(i).getType();
            Type argumentType = parameterTypes.getTypes().get(i);
            if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentType, formalParameterType)) {
                return false;
            }
        }
        return true;
    }

    private boolean compatible(NamedParameterTypes<Type> parameterTypes, List<FormalParameter<Type>> parameters) {
        if (parameterTypes.size() != parameters.size()) {
            return false;
        }
        for (FormalParameter<Type> formalParameter : parameters) {
            Type argumentType = parameterTypes.getType(formalParameter.getName());
            Type parameterType = formalParameter.getType();
            if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(argumentType, parameterType)) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean match(ParameterTypes<Type> parameterTypes);

    protected abstract List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> matchCandidates(List<Type> argumentTypes);
}
