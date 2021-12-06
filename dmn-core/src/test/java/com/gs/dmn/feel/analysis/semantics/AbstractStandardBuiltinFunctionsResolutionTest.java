package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.FEELAnalyzerImpl;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

public abstract class AbstractStandardBuiltinFunctionsResolutionTest extends AbstractBuiltinFunctionsResolutionTest {
    private final LazyEvaluationDetector lazyEvaluator = new NopLazyEvaluationDetector();
    private final BasicDMNToJavaTransformer basicTransformer = new StandardDMNDialectDefinition().createBasicTransformer(new DMNModelRepository(), lazyEvaluator, new InputParameters());
    private final FEELAnalyzerImpl feelAnalyzer = new FEELAnalyzerImpl(basicTransformer);
    private final DMNContext dmnContext = basicTransformer.makeBuiltInContext();

    @Override
    protected FEELAnalyzer getFEELAnalyzer() {
        return feelAnalyzer;
    }

    @Override
    protected DMNContext getDMNContext() {
        return dmnContext;
    }
}