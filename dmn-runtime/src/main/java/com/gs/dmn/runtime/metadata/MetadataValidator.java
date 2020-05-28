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
package com.gs.dmn.runtime.metadata;

import com.gs.dmn.serialization.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class MetadataValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataValidator.class);

    public boolean validate(String pkg, ClassLoader classLoader) {
        String metadataPath = pkg.replace('.', '/');
        String fileName = DMNMetadata.class.getSimpleName();
        try (InputStream metadataStream = classLoader.getResourceAsStream(String.format("%s/%s.json", metadataPath, fileName))) {
            DMNMetadata dmnMetadata = JsonSerializer.OBJECT_MAPPER.readValue(metadataStream, DMNMetadata.class);
            List<DRGElement> decisions = dmnMetadata.getDecisions();
            for (DRGElement decision : decisions) {
                String className = String.format("%s", decision.getJavaTypeName());
                Class<?> aClass = Class.forName(className, true, classLoader);
                boolean isGenerated = isGenerated(aClass);
                if (!isGenerated) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    private boolean isGenerated(Class<?> aClass) {
        return aClass.getAnnotation(com.gs.dmn.runtime.annotation.DRGElement.class) != null;
    }
}
