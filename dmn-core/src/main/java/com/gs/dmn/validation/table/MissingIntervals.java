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
package com.gs.dmn.validation.table;

import com.gs.dmn.validation.SimpleDMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissingIntervals {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNValidator.class);

    private final Map<Integer, List<Interval>> map = new LinkedHashMap<>();

    public void addMissingInterval(Integer columnIndex, Interval missingInterval) {
        String indent = StringUtils.repeat("\t", columnIndex);
        LOGGER.info("{}Add missing interval '{}'", indent, missingInterval);

        map.computeIfAbsent(columnIndex, k -> new ArrayList<>());
        map.get(columnIndex).add(missingInterval);
    }

    public void putMissingInterval(Integer columnIndex, Interval missingInterval) {
        String indent = StringUtils.repeat("\t", columnIndex);
        LOGGER.info("{}Put missing interval '{}'", indent, missingInterval);

        map.computeIfAbsent(columnIndex, k -> new ArrayList<>());
        map.get(columnIndex).clear();
        map.get(columnIndex).add(missingInterval);
    }

    public List<Interval> getIntervals(int columnIndex) {
        return map.get(columnIndex);
    }

    public int size() {
        return map.entrySet().size();
    }

    @Override
    public String toString() {
        return map.entrySet().stream().map(e -> "%s -> %s".formatted(e.getKey(), e.getValue())).collect(Collectors.joining(", "));
    }
}
