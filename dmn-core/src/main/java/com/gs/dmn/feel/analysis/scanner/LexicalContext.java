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
package com.gs.dmn.feel.analysis.scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalContext {
    private final List<String> names = new ArrayList<>();

    public LexicalContext(String... names) {
        if (names != null) {
            this.names.addAll(Arrays.asList(names));
        }
        sort();
    }

    public LexicalContext(List<String> names) {
        if (names != null) {
            this.names.addAll(names);
        }
        sort();
    }

    public List<String> getNames() {
        return this.names;
    }

    private void sort() {
        this.names.sort((o1, o2) -> o2.length() - o1.length());
    }
}
