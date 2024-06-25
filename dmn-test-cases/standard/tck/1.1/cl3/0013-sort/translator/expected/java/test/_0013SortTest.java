
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0013-sort.dmn"})
public class _0013SortTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        List<java.math.BigDecimal> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Check 'sort1'
        checkValues(asList(number("5"), number("4"), number("3"), number("1")), new Sort1().apply(listA, context_));
        // Check 'sort2'
        checkValues(asList(new type.TRowImpl(number("1"), number("0"), number("1"), number("1")), new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10"))), new Sort2().apply(tableB, context_));
        // Check 'sort3'
        checkValues(asList("10", "8", "9", "A", "Aa", "a"), new Sort3().apply(stringList, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
