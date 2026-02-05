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
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContextBuilderTest {
    private final List<Pair<String, PathValue>> entries = Arrays.asList(
            Pair.of("{text=abc}", new PathValue("text", "abc")),
            Pair.of("{text=abc, user={name=Alice}}", new PathValue("user.name", "Alice")),
            Pair.of("{text=abc, user={age=30, name=Alice}}", new PathValue("user.age", 30)),
            Pair.of("{text=abc, user={age=30, name=Alice, tags=[admin]}}", new PathValue("user.tags[]", "admin")),
            Pair.of("{text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("user.tags[]", "beta")),
            Pair.of("{orders=[{id=o-1}], text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("orders[0].id", "o-1")),
            Pair.of("{orders=[{amount=12.5, id=o-1}], text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("orders[0].amount", 12.5)),
            Pair.of("{orders=[{amount=12.5, id=o-1}, {id=o-2}], text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("orders[1].id", "o-2")),
            Pair.of("{orders=[{amount=12.5, id=o-1}, {amount=7.25, id=o-2}], text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("orders[1].amount", 7.25)),
            Pair.of("{misc={emptyList=[first]}, orders=[{amount=12.5, id=o-1}, {amount=7.25, id=o-2}], text=abc, user={age=30, name=Alice, tags=[admin, beta]}}", new PathValue("misc.emptyList[]", "first"))
    );

    @Test
    public void testInsert() {
        Context context = new Context();
        for (Pair<String, PathValue> entry : entries) {
            PathValue pathValue = entry.getRight();
            ContextBuilder.insert(context, pathValue);
            assertEquals(entry.getLeft(), context.toString());
        }
    }

    @Test
    public void testBuild() {
        Context context = ContextBuilder.build(entries.stream().map(Pair::getRight).collect(Collectors.toList()));
        // Get the last entry expected value
        Pair<String, PathValue> entry = entries.get(entries.size() - 1);
        assertEquals(entry.getLeft(), context.toString());
    }

    @Test
    public void testInsertQualifiedNames() {
        Context context = new Context();
        ContextBuilder.insert(context, new PathValue("a.b", "1"));
        ContextBuilder.insert(context, new PathValue("'a.b'.c", "2"));
        assertEquals("{a={b=1}, a.b={c=2}}", context.toString());
    }

    @Test
    public void testInsertNulls() {
        Context context = new Context();
        ContextBuilder.insert(context, new PathValue("a.b[0].c", null));
        ContextBuilder.insert(context, new PathValue("a.b[1].c", 42));
        assertEquals("{a={b=[{c=null}, {c=42}]}}", context.toString());
    }

    @Test
    public void testInsertGapsInTheMiddle() {
        Context context = new Context();
        ContextBuilder.insert(context, new PathValue("a.b[0].c", null));
        ContextBuilder.insert(context, new PathValue("a.b[2].c", 42));
        assertEquals("{a={b=[{c=null}, null, {c=42}]}}", context.toString());
    }

    @Test
    public void testInsertGapsInTheEnd() {
        Context context = new Context();
        ContextBuilder.insert(context, new PathValue("b[2]", 42));
        assertEquals("{b=[null, null, 42]}", context.toString());
    }

    @Test
    public void testInsertContextInList() {
        Context context = new Context();
        ContextBuilder.insert(context, new PathValue("b[].c", 42));
        assertEquals("{b=[{c=42}]}", context.toString());
    }

    @Test
    public void testInsertEmpty() {
        Context context = new Context();
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("", 42)));
    }

    @Test
    public void testInsertInvalidListIndex() {
        Context context = new Context();
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("b[abc]", 42)));
    }

    @Test
    public void testInsertTypeConflictInListOfValues() {
        Context context = new Context();
        // Initial: List of values
        ContextBuilder.insert(context, new PathValue("b[0]", 42));
        ContextBuilder.insert(context, new PathValue("b[1]", 30));
        // Try: Insert Context into list
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("b[0].c", 100)));
    }

    @Test
    public void testInsertTypeConflictInListOfContext() {
        Context context = new Context();
        // Initial: List of Contexts
        ContextBuilder.insert(context, new PathValue("b[0].c", 42));
        // Try: Context
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("b.c", 100)));
    }

    @Test
    public void testInsertTypeConflictInContext() {
        Context context = new Context();
        // Initial: Simple value
        ContextBuilder.insert(context, new PathValue("b", 42));
        // Try: Context
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("b.c", 100)));
    }

    @Test
    public void testInsertTypeConflictInContextList() {
        Context context = new Context();
        // Initial: Context
        ContextBuilder.insert(context, new PathValue("a.b.c", 42));
        // Try: List
        assertThrows(IllegalArgumentException.class, () -> ContextBuilder.insert(context, new PathValue("a.b[]", 100)));
    }
}