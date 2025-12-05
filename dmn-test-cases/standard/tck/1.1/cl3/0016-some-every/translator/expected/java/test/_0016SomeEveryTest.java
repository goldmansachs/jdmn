
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0016-some-every.dmn"})
public class _0016SomeEveryTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'priceTable1'
        checkValues(asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5"))), new PriceTable1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'everyGtTen1'
        checkValues(Boolean.FALSE, new EveryGtTen1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'everyGtTen2'
        checkValues(Boolean.FALSE, new EveryGtTen2().apply(priceTable2, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'someGtTen1'
        checkValues(Boolean.TRUE, new SomeGtTen1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'someGtTen2'
        checkValues(Boolean.TRUE, new SomeGtTen2().apply(priceTable2, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_6() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'everyGtTen3'
        checkValues(Boolean.FALSE, new EveryGtTen3().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
