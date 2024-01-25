package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.DateType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.tck.ast.AnySimpleType;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TCKValueTranslatorTest {
    private final StandardDMNDialectDefinition dialect = new StandardDMNDialectDefinition();
    private final BasicDMNToNativeTransformer<Type, DMNContext> transformer = dialect.createBasicTransformer(new DMNModelRepository(), new NopLazyEvaluationDetector(), new InputParameters());
    private final DefaultFEELLib feelLib = new DefaultFEELLib();
    private final TCKValueTranslator<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> translator = new TCKValueTranslator<>(transformer, feelLib);

    @Test
    public void testToNativeExpression() {
        doTest(AnySimpleType.of("123"), NumberType.NUMBER, "number(\"123\")");
        doTest(AnySimpleType.of("\n123"), NumberType.NUMBER, "number(\"123\")");
        doTest(AnySimpleType.of("abc"), StringType.STRING, "\"abc\"");
        doTest(AnySimpleType.of(" abc"), StringType.STRING, "\" abc\"");
        doTest(AnySimpleType.of("\na\nbc\n"), StringType.STRING, "\"\\na\\nbc\\n\"");
        doTest(AnySimpleType.of("true\n"), BooleanType.BOOLEAN, "Boolean.TRUE");
        doTest(AnySimpleType.of("2021-02-03"), DateType.DATE, "date(\"2021-02-03\")");
        doTest(AnySimpleType.of("2021-02-03 "), DateType.DATE, "date(\"2021-02-03\")");
    }

    private void doTest(AnySimpleType value, Type type, String expected) {
        ValueType valueType = new ValueType();
        valueType.setValue(value);
        assertEquals(expected, translator.toNativeExpression(valueType, type, null));
    }
}