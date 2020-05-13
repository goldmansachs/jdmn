
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0016-some-every.dmn"})
public class Test0016SomeEvery extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<type.TItemPrice> priceTable2 = asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5")));

        // Check priceTable1
        checkValues(asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5"))), new PriceTable1().apply(annotationSet_, eventListener_, externalExecutor_));
        // Check everyGtTen1
        checkValues(false, new EveryGtTen1().apply(annotationSet_, eventListener_, externalExecutor_));
        // Check everyGtTen2
        checkValues(false, new EveryGtTen2().apply(priceTable2, annotationSet_, eventListener_, externalExecutor_));
        // Check someGtTen1
        checkValues(true, new SomeGtTen1().apply(annotationSet_, eventListener_, externalExecutor_));
        // Check someGtTen2
        checkValues(true, new SomeGtTen2().apply(priceTable2, annotationSet_, eventListener_, externalExecutor_));
        // Check everyGtTen3
        checkValues(false, new EveryGtTen3().apply(annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
