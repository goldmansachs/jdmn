
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1157-implicit-conversions.dmn"})
public class _1157ImplicitConversionsTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("abc"), new ToSingletonList().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("abc", new FromSingletonList().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_a_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List Error'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new FromSingletonListError().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date To Date and Time'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), new FromDateToDateAndTime().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList(number("1")), ToSingletonListBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), FromSingletonListBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date To Date and Time BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList(date("2000-01-02")), ToSingletonListDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase008_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(date("2000-01-02"), FromSingletonListDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase009_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date to Date and Time DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase010_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Implicit Conversions CE'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("[1]-2-2000-12-01T00:00:00@UTC", new ImplicitConversionsCE().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase011_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Implicit Conversions FUNCT'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("[1]-2-2000-02-01T00:00:00@UTC", new ImplicitConversionsFUNCT().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
