
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "3652588c6383423c9774f4dfd4393cb1/sid-074482BF-264A-48BD-976A-707710B53881"})
public class BigMidTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Object> {
    private final BigMid bigMid = new BigMid();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        type.TestPeopleType testPeopleType = new type.TestPeopleTypeImpl(asList(new type.TestPersonTypeImpl(number("23"), "a", asList("Sad", "Quiet"))));
        List<Boolean> bigMid = this.bigMid.apply(testPeopleType, context_);

        checkValues(asList(Boolean.TRUE), bigMid);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        type.TestPeopleType testPeopleType = new type.TestPeopleTypeImpl(asList(new type.TestPersonTypeImpl(number("43"), "g", asList("Happy", "Tall")), new type.TestPersonTypeImpl(number("5"), "l", asList("Short", "Loud", "Happy"))));
        List<Boolean> bigMid = this.bigMid.apply(testPeopleType, context_);

        checkValues(asList(Boolean.TRUE, Boolean.FALSE), bigMid);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
