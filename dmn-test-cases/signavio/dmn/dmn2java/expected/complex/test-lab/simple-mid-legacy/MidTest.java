
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "id-5867fbcd9589cbc1aef6eef4c84a4e48"})
public class MidTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Mid mid = new Mid();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        List<java.math.BigDecimal> numz = asList(number("1"), number("2"));
        List<String> mid = this.mid.apply(numz, context_);

        checkValues(asList("child", "child"), mid);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        List<java.math.BigDecimal> numz = asList(number("50"), number("100"));
        List<String> mid = this.mid.apply(numz, context_);

        checkValues(asList("adult", "adult"), mid);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
