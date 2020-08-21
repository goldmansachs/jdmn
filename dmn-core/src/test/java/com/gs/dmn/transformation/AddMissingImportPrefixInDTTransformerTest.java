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
package com.gs.dmn.transformation;

import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AddMissingImportPrefixInDTTransformerTest {
    private final AddMissingImportPrefixInDTTransformer transformer = new AddMissingImportPrefixInDTTransformer();
    private final Set<String> names = new LinkedHashSet<>(Arrays.asList("ident1", "ident2"));

    @Test
    public void testAddMissingPrefix() {
        List<Pair<String, String>> testCases = Arrays.asList(
                // Empty
                new Pair<>(null, null),
                new Pair<>("", ""),
                // String literal
                new Pair<>("\"ident1\"", "\"ident1\""),
                new Pair<>("ident1+ident2", "ident1.ident1 + ident2.ident2"),
                // Unary tests
                new Pair<>("> ident1+ident2", "> ident1.ident1 + ident2.ident2"),
                new Pair<>("starts with(?, ident1)", "starts with ( ? , ident1.ident1 )"),
                // Function name same as argument
                new Pair<>("ident1(?, ident1)", "ident1 ( ? , ident1.ident1 )"),
                // Member name same as source
                new Pair<>("ident1.ident1+4", "ident1.ident1 . ident1 + 4")
        );
        for (Pair<String, String> pair: testCases) {
            String text = pair.getLeft();
            String newText = transformer.addMissingPrefix(text, names);
            assertEquals(pair.getRight(), newText);
        }
    }

}