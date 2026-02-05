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
package com.gs.dmn.runtime.context;

import com.gs.dmn.runtime.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContextBuilder {
    private ContextBuilder() {
    }

    public static Context build(List<PathValue> entries) {
        Objects.requireNonNull(entries);
        Context root = new Context();
        for (PathValue pv : entries) {
            insert(root, pv);
        }
        return root;
    }

    protected static void insert(Context root, PathValue pathValue) {
        String path = pathValue.getPath();
        Object value = pathValue.getValue();
        List<String> segments = parsePathSegments(path);
        Context current = root;

        for (int i = 0; i < segments.size(); i++) {
            Segment segment = Segment.extractSegment(segments.get(i));

            boolean isLast = (i == segments.size() - 1);

            String key = segment.getKey();
            if (segment.isList()) {
                // list handling
                List<Object> list = ensureList(current, key);
                Integer index = segment.getIndex();
                if (isLast) {
                    if (index == null) {
                        list.add(value);
                    } else {
                        ensureListSize(list, index + 1);
                        list.set(index, value);
                    }
                } else {
                    // intermediate list element must be a Context
                    int useIndex;
                    if (index == null) {
                        // append new Context and use it
                        Context next = new Context();
                        list.add(next);
                        useIndex = list.size() - 1;
                    } else {
                        ensureListSize(list, index + 1);
                        Object el = list.get(index);
                        if (el == null) {
                            Context next = new Context();
                            list.set(index, next);
                            useIndex = index;
                        } else if (el instanceof Context) {
                            useIndex = index;
                        } else {
                            throw new IllegalArgumentException("Type conflict at list element '" + key + "[" + index + "]': expected Context");
                        }
                    }
                    current = (Context) list.get(useIndex);
                }
            } else {
                // context handling
                if (isLast) {
                    current.add(key, value);
                } else {
                    Object child = current.get(key);
                    if (child == null) {
                        Context next = new Context();
                        current.add(key, next);
                        current = next;
                    } else if (child instanceof Context) {
                        current = (Context) child;
                    } else {
                        throw new IllegalArgumentException("Type conflict at '" + key + "': expected Context");
                    }
                }
            }
        }
    }

    private static List<String> parsePathSegments(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        List<String> segments = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);

            if (c == '\'') {
                inQuotes = !inQuotes;
            } else if (c == '.' && !inQuotes) {
                if (current.length() > 0) {
                    segments.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            segments.add(current.toString());
        }

        return segments;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> ensureList(Context ctx, String key) {
        Object o = ctx.get(key);
        if (o == null) {
            List<Object> list = new ArrayList<>();
            ctx.add(key, list);
            return list;
        }
        if (o instanceof List) {
            return (List<Object>) o;
        }
        throw new IllegalArgumentException("Type conflict for key '" + key + "': expected List");
    }

    private static void ensureListSize(List<Object> list, int size) {
        while (list.size() < size) list.add(null);
    }
}