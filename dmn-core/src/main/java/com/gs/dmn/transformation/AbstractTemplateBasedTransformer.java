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
package com.gs.dmn.transformation;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.transformation.formatter.JavaFormatter;
import com.gs.dmn.transformation.formatter.NopJavaFormatter;
import com.gs.dmn.transformation.template.TemplateProvider;
import freemarker.template.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractTemplateBasedTransformer extends AbstractFileTransformer {
    private static final Version VERSION = new Version("2.3.23");
    private static final JavaFormatter FORMATTER = new NopJavaFormatter();

    protected final TemplateProvider templateProvider;

    public AbstractTemplateBasedTransformer(TemplateProvider templateProvider, Map<String, String> inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
        this.templateProvider = templateProvider;
    }

    protected void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, File outputFile, boolean formatOutput) throws IOException, TemplateException {
        Configuration cfg = makeConfiguration(baseTemplatePath);
        Template template = cfg.getTemplate("/" + templateName);

        try (Writer fileWriter = new FileWriter(outputFile)) {
            template.process(params, fileWriter);
        }
        try {
            String text = FileUtils.readFileToString(outputFile);
            if (formatOutput) {
                text = FORMATTER.formatSource(text);
            }
            FileUtils.write(outputFile, text, false);
        } catch (Exception e) {
            logger.error(String.format("Formatting error for file %s", outputFile.getName()));
        }
    }

    private Configuration makeConfiguration(String basePackagePath) {
        Configuration cfg = new Configuration(VERSION);

        // Some recommended settings:
        cfg.setIncompatibleImprovements(VERSION);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(this.getClass(), basePackagePath);
        return cfg;
    }

    protected File makeOutputFile(Path outputPath, String relativeFilePath, String fileName, String fileExtension) {
        String absoluteFilePath = outputPath.toAbsolutePath().toString();
        if (!StringUtils.isBlank(relativeFilePath)) {
            absoluteFilePath += "/" + relativeFilePath;
        }
        absoluteFilePath += "/" + fileName + fileExtension;
        File outputFile = new File(absoluteFilePath);
        outputFile.getParentFile().mkdirs();
        return outputFile;
    }
}
