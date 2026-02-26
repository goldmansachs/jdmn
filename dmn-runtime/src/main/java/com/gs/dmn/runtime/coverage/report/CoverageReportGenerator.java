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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.coverage.trace.ModelCoverageTrace;
import com.gs.dmn.serialization.JsonSerializer;

import java.io.File;
import java.util.List;

public abstract class CoverageReportGenerator {
    public CoverageReport generateCoverageReport(File tracesFolder) {
        // Read all coverage files from target/coverage folder and generate a report
        CoverageReport report = makeReport();
        if (tracesFolder.exists()) {
            File[] coverageFiles = tracesFolder.listFiles((dir, name) -> name.endsWith(".json"));
            if (coverageFiles != null && coverageFiles.length > 0) {
                for (File coverageFile : coverageFiles) {
                    try {
                        List<ModelCoverageTrace> traces = JsonSerializer.OBJECT_MAPPER.readValue(coverageFile, new TypeReference<List<ModelCoverageTrace>>(){});
                        report.addTraces(traces);
                    } catch (Exception e) {
                        throw new DMNRuntimeException("Cannot read coverage file: " + coverageFile.getAbsolutePath(), e);
                    }
                }
            }
        }
        return report;
    }

    protected abstract CoverageReport makeReport();
}
