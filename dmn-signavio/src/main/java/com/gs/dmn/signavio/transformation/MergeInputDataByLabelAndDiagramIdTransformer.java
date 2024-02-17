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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TInputData;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.InputParameterDefinition;

public class MergeInputDataByLabelAndDiagramIdTransformer extends AbstractMergeInputDataTransformer {
    public MergeInputDataByLabelAndDiagramIdTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public MergeInputDataByLabelAndDiagramIdTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    protected String equivalenceKey(TInputData inputData, DMNModelRepository repository) {
        if (isIterator(inputData, repository)) {
            return "%s-%s".formatted(diagramId(inputData, repository), shapeId(inputData, repository));
        } else {
            return "%s-%s".formatted(diagramId(inputData, repository), inputData.getLabel().trim());
        }
    }

    @Override
    protected String equivalenceKey(InputParameterDefinition parameter) {
        // requirement name is the label of corresponding InputData
        return "%s-%s".formatted(parameter.getDiagramId(), parameter.getRequirementName().trim());
    }

    private String diagramId(TInputData inputData, DMNModelRepository repository) {
        SignavioDMNModelRepository signavioRepository = (SignavioDMNModelRepository) repository;
        return signavioRepository.getDiagramId(inputData);
    }

    private String shapeId(TInputData inputData, DMNModelRepository repository) {
        SignavioDMNModelRepository signavioRepository = (SignavioDMNModelRepository) repository;
        return signavioRepository.getShapeId(inputData);
    }
}
