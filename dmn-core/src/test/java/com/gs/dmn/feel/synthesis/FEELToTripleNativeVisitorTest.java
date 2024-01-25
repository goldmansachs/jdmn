package com.gs.dmn.feel.synthesis;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.DMNContextKind;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.dialect.PureJavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.el.synthesis.triple.Triple;
import com.gs.dmn.el.synthesis.triple.Triples;
import com.gs.dmn.feel.EnvironmentEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FEELToTripleNativeVisitorTest extends AbstractTest {
    private final EnvironmentFactory environmentFactory;
    private final FEELLib<BigDecimal, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> lib;
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    private final ELTranslator<Type, DMNContext> feelTranslator;
    private FEELToTripleNativeVisitor nativeVisitor;

    public FEELToTripleNativeVisitorTest() {
        PureJavaTimeDMNDialectDefinition dialectDefinition = new PureJavaTimeDMNDialectDefinition();
        DMNModelRepository repository = makeRepository();
        InputParameters inputParameters = makeInputParameters();

        this.environmentFactory = dialectDefinition.createEnvironmentFactory();
        this.lib = dialectDefinition.createFEELLib();
        this.feelTranslator = dialectDefinition.createFEELTranslator(repository, inputParameters);
        this.dmnTransformer = dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters);
        this.nativeVisitor = new FEELToTripleNativeVisitor(this.dmnTransformer);
    }

    @Test
    public void testArithmeticExpression() {
        BigDecimal a = this.lib.number("1");
        BigDecimal b = this.lib.number("2");
        String s1 = "a";
        String s2 = "b";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("n1", NUMBER, a),
                new EnvironmentEntry("n2", NUMBER, b),
                new EnvironmentEntry("s1", STRING, s1),
                new EnvironmentEntry("s2", STRING, s2)
        );

        doExpressionTest(entries, "", "n1 + n2", "[Name(n1), Name(n2), Name(numericAdd), BuiltinFunctionInvocation(TripleReference(2), [TripleReference(0), TripleReference(1)])]", "TripleReference(3)");
        doExpressionTest(entries, "", "s1 + s2", "[Name(s1), Name(s2), Name(stringAdd), BuiltinFunctionInvocation(TripleReference(2), [TripleReference(0), TripleReference(1)])]", "TripleReference(3)");
    }

    private void doExpressionTest(List<EnvironmentEntry> entries, String inputExpressionText, String expressionText, String expectedTriples, String expectedRoot) {
        Expression<Type> inputExpression = null;
        DMNContext globalContext = makeContext(entries);
        if (!StringUtils.isEmpty(inputExpressionText)) {
            // Analyze input expression
            inputExpression = (Expression<Type>) this.feelTranslator.analyzeExpression(inputExpressionText, globalContext);
        }

        // Analyse expression
        DMNContext expressionContext = this.dmnTransformer.makeUnaryTestContext(inputExpression, globalContext);
        Expression<Type> expression = (Expression<Type>) this.feelTranslator.analyzeExpression(expressionText, expressionContext);

        // Translate to triples
        this.nativeVisitor = new FEELToTripleNativeVisitor(this.dmnTransformer);
        Triple rootTriple = (Triple) expression.accept(nativeVisitor, globalContext);
        Triples triples = nativeVisitor.getTriples();
        assertEquals(expectedTriples, triples.toString());
        assertEquals(expectedRoot, rootTriple.toString());
    }

    private DMNContext makeContext(List<EnvironmentEntry> entries) {
        DMNContext context = DMNContext.of(
                this.dmnTransformer.makeBuiltInContext(),
                DMNContextKind.LOCAL,
                getElement(),
                this.environmentFactory.emptyEnvironment(),
                RuntimeEnvironment.of());
        for (EnvironmentEntry entry : entries) {
            context.addDeclaration(this.environmentFactory.makeVariableDeclaration(entry.getName(), entry.getType()));
            context.bind(entry.getName(), entry.getValue());
        }
        return context;
    }

    private DMNModelRepository makeRepository() {
        return new DMNModelRepository();
    }

    protected TNamedElement getElement() {
        return null;
    }
}