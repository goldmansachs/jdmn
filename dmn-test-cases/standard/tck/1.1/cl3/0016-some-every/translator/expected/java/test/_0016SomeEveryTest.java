
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0016-some-every.dmn"})
public class _0016SomeEveryTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check 'priceTable1'
        checkValues(asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5"))), new PriceTable1().apply(context_));
        // Check 'everyGtTen1'
        checkValues(Boolean.FALSE, new EveryGtTen1().apply(context_));
        // Check 'everyGtTen2'
        checkValues(Boolean.FALSE, new EveryGtTen2().apply(priceTable2, context_));
        // Check 'someGtTen1'
        checkValues(Boolean.TRUE, new SomeGtTen1().apply(context_));
        // Check 'someGtTen2'
        checkValues(Boolean.TRUE, new SomeGtTen2().apply(priceTable2, context_));
        // Check 'everyGtTen3'
        checkValues(Boolean.FALSE, new EveryGtTen3().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
