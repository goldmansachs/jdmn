
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0016-some-every.dmn"})
public class _0016SomeEveryTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_d7643a02-a8fc-4a6f-a8a9-5c2881afea70", "0016-some-every", 7);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'priceTable1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5"))), new PriceTable1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'everyGtTen1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.FALSE, new EveryGtTen1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'everyGtTen2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.FALSE, new EveryGtTen2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'someGtTen1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new SomeGtTen1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'someGtTen2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new SomeGtTen2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_6() {
        // Initialize inputs
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("priceTable2", priceTable2);

        // Check 'everyGtTen3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.FALSE, new EveryGtTen3().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.AfterAll
    static void saveTrace() {
        java.io.File traceDir = new java.io.File("target/coverage-traces");
        if (!traceDir.exists()) {
            traceDir.mkdirs();
        }
        java.io.File traceFile = new java.io.File(traceDir, "0016-some-every-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
