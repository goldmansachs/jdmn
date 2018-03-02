
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0013-sort.dmn"})
public class Test0013Sort extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Check sort1
        checkValues(asList(number("5"), number("4"), number("3"), number("1")), new Sort1().apply(listA, annotationSet_, eventListener_, externalExecutor_));
        // Check sort2
        checkValues(asList(new type.TRowImpl(number("1"), number("0"), number("1"), number("1")), new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10"))), new Sort2().apply(tableB, annotationSet_, eventListener_, externalExecutor_));
        // Check sort3
        checkValues(asList("10", "8", "9", "A", "Aa", "a"), new Sort3().apply(stringList, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
