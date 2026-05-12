package parentlinked;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "80afa9e878bb4885a8f5be36b6f16abc/sid-89A9C1A5-1701-4289-9DFE-9FF6223528C7"})
public class SomethingElseTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Object> {
    private final parentlinked.SomethingElse somethingElse = new parentlinked.SomethingElse();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.lang.Number num = number("1");
        java.lang.Number somethingElse = this.somethingElse.apply(num, context_);

        checkValues(number("12"), somethingElse);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
