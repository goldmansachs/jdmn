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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;

import java.util.*;

public class TypeDeserializer {
    private final static TypeDeserializer INSTANCE = new TypeDeserializer();

    public static TypeDeserializer instance() {
        return INSTANCE;
    }

    public Type deserialize(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text, "(,=)", true);
        Stack<Object> stack = new Stack<>();
        int n = tokenizer.countTokens();
        for (int i = 0; i < n; i++) {
            String token = tokenizer.nextToken().trim();
            if ("enumeration".equals(token)) {
                stack.push(EnumerationType.ENUMERATION);
            } else if (FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(token) != null) {
                stack.push(FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(token));
            } else if (AnyType.class.getSimpleName().equals(token)) {
                stack.push(AnyType.ANY);
            } else if (NullType.class.getSimpleName().equals(token)) {
                stack.push(NullType.NULL);
            } else if (ContextType.class.getSimpleName().equals(token)) {
                stack.push(new ContextType());
            } else if (ItemDefinitionType.class.getSimpleName().equals(token)) {
                stack.push(new ItemDefinitionType(""));
            } else if (FEELFunctionType.class.getSimpleName().equals(token)) {
                stack.push(new FEELFunctionType(null, null, false));
            } else if (FormalParameter.class.getSimpleName().equals(token)) {
                stack.push(new FormalParameter(null, (Type)null));
            } else if (ListType.class.getSimpleName().equals(token)) {
                stack.push(new ListType());
            } else if (RangeType.class.getSimpleName().equals(token)) {
                stack.push(new RangeType());
            } else if (TupleType.class.getSimpleName().equals(token)) {
                stack.push(new TupleType());
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (",".equals(token)) {
            } else if ("=".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                List<Object> children = new ArrayList<>();
                while (!"(".equals(stack.peek())) {
                    children.add(0, stack.pop());
                }
                // )
                stack.pop();
                // Build type
                Object top = stack.peek();
                int size = children.size();
                if (top instanceof ContextType) {
                    for (int j = 0; j < size; j = j + 3) {
                        ((ContextType) top).addMember((String) children.get(j), Arrays.asList(), (Type) children.get(j + 2));
                    }
                } else if (top instanceof FEELFunctionType) {
                    List<FormalParameter> args = new ArrayList<>();
                    for(int j = 0; j < size - 2; j++) {
                        args.add((FormalParameter)children.get(j));
                    }
                    top = new FEELFunctionType(args, (Type)children.get(size - 2), Boolean.parseBoolean(children.get(size - 1).toString()));
                    stack.pop();
                    stack.push(top);
                } else if (top instanceof FormalParameter) {
                    List<Object> args = new ArrayList<>();
                    for (Object aChildren : children) {
                        args.add(aChildren);
                    }
                    top = new FormalParameter((String)args.get(0), args.size() == 1 ? null : (Type)args.get(1));
                    stack.pop();
                    stack.push(top);
                } else if (top instanceof ItemDefinitionType) {
                    int j = 0;
                    List<String> names = new ArrayList<>();
                    while (j < size) {
                        Object child = children.get(j);
                        if ("=".equals(child)) {
                            // Add member
                            String name = names.get(0);
                            List<String> aliases = names.size() == 1 ? Arrays.asList() : new ArrayList<>(names.subList(1, names.size()));
                            ((ItemDefinitionType) top).addMember(name, aliases, (Type) children.get(j + 1));
                            // Reset names
                            names.clear();
                            // Move cursor
                            j = j + 2;
                        } else {
                            // Add names
                            names.add(child.toString());
                            // Move cursor
                            j = j + 1;
                        }
                    }
                } else if (top instanceof ListType) {
                    top = new ListType((Type) children.get(0));
                    stack.pop();
                    stack.push(top);
                } else if (top instanceof RangeType) {
                    top = new RangeType((Type) children.get(0));
                    stack.pop();
                    stack.push(top);
                } else if (top instanceof TupleType) {
                    top = new TupleType((List) children);
                    stack.pop();
                    stack.push(top);
                }
            } else {
                // Name in context
                stack.push(token);
            }
        }
        return (Type) stack.pop();
    }
}
