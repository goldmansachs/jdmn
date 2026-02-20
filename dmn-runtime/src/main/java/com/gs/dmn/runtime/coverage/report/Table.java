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
package com.gs.dmn.runtime.coverage.report;

import java.util.List;

public class Table {
    static final String CSV_SEPARATOR = ",";

    private final List<String> columnNames;
    private final List<List<String>> rows;

    public Table(List<String> columnNames, List<List<String>> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public List<String> toCsvLines() {
        List<String> csvLines = new java.util.ArrayList<>();
        csvLines.add(String.join(CSV_SEPARATOR, columnNames));
        rows.stream().forEach(row -> csvLines.add(String.join(CSV_SEPARATOR, row)));
        return csvLines;
    }
}
