
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0013-sort.dmn"})
public class _0013SortTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize arguments
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Check 'sort1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList(number("5"), number("4"), number("3"), number("1")), new Sort1().apply(listA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize arguments
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Check 'sort2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList(new type.TRowImpl(number("1"), number("0"), number("1"), number("1")), new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10"))), new Sort2().apply(tableB, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize arguments
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Check 'sort3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("10", "8", "9", "A", "Aa", "a"), new Sort3().apply(stringList, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
