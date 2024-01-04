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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;

import java.util.List;
import java.util.stream.Collectors;

public class TypeRefValidationContext extends ValidationContext {
    private final BasicDMNToJavaTransformer dmnTransformer;
    private final List<Pair<TDRGElement, Type>> errorReport;

    public TypeRefValidationContext(DMNModelRepository repository, BasicDMNToJavaTransformer dmnTransformer, List<Pair<TDRGElement, Type>> errorReport) {
        super(repository);
        this.dmnTransformer = dmnTransformer;
        this.errorReport = errorReport;
    }

    @Override
    public List<String> getErrors() {
        return this.errorReport.stream().map(p -> makeError(p, repository)).collect(Collectors.toList());
    }

    public BasicDMNToJavaTransformer getDmnTransformer() {
        return dmnTransformer;
    }

    public List<Pair<TDRGElement, Type>> getErrorReport() {
        return errorReport;
    }

    private String makeError(Pair<TDRGElement, Type> pair, DMNModelRepository repository) {
        TDRGElement element = pair.getLeft();
        TDefinitions model = repository.getModel(element);
        Type type = pair.getRight();
        TInformationItem variable = repository.variable(element);
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());

        String hint = Type.isNull(type) ? "" : String.format(". The inferred type is '%s'", type);
        return SimpleDMNValidator.makeError(repository, model, element, String.format("Cannot find typeRef '%s'", typeRef.toString()) + hint);
    }
}
