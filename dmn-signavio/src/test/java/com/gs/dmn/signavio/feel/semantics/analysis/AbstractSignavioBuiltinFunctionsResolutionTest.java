package com.gs.dmn.signavio.feel.semantics.analysis;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.FEELAnalyzerImpl;
import com.gs.dmn.feel.analysis.semantics.AbstractBuiltinFunctionsResolutionTest;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

public abstract class AbstractSignavioBuiltinFunctionsResolutionTest extends AbstractBuiltinFunctionsResolutionTest {
    private final LazyEvaluationDetector lazyEvaluator = new NopLazyEvaluationDetector();
    private final BasicDMNToJavaTransformer basicTransformer = new JavaTimeSignavioDMNDialectDefinition().createBasicTransformer(new SignavioDMNModelRepository(), lazyEvaluator, new InputParameters());
    private final FEELAnalyzerImpl feelAnalyzer = new FEELAnalyzerImpl(basicTransformer);
    private final DMNContext dmnContext = basicTransformer.makeBuiltInContext();

    @Override
    protected ELAnalyzer<Type, DMNContext> getFEELAnalyzer() {
        return feelAnalyzer;
    }

    @Override
    protected DMNContext getDMNContext() {
        return dmnContext;
    }
}