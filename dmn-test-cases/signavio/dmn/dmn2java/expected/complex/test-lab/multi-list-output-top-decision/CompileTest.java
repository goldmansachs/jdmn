
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "6776bb215482477ba041c80c9c559985/sid-78F19456-59E2-4ED8-BD1C-869E58B6D288"})
public class CompileTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final Compile compile = new Compile();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        List<java.lang.Number> numbers = asList(number("1"), number("2"));
        String name = "e";
        List<String> trafficLight = asList("Yellow", "Green");
        List<type.Compile> compile = this.compile.apply(name, numbers, trafficLight, context_);

        checkValues(asList(new type.CompileImpl(number("1"), "e", "Red")), compile);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
