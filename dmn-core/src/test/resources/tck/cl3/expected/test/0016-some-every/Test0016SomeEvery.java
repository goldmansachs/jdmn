
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
        List<type.TItemPrice> priceTable1Output = new PriceTable1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(new type.TItemPriceImpl("widget", number("25")), new type.TItemPriceImpl("sprocket", number("15")), new type.TItemPriceImpl("trinket", number("1.5"))), priceTable1Output);
        // Check everyGtTen1
        Boolean everyGtTen1Output = new EveryGtTen1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, everyGtTen1Output);
        // Check everyGtTen2
        Boolean everyGtTen2Output = new EveryGtTen2().apply(priceTable2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, everyGtTen2Output);
        // Check someGtTen1
        Boolean someGtTen1Output = new SomeGtTen1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, someGtTen1Output);
        // Check someGtTen2
        Boolean someGtTen2Output = new SomeGtTen2().apply(priceTable2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, someGtTen2Output);
        // Check everyGtTen3
        Boolean everyGtTen3Output = new EveryGtTen3().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, everyGtTen3Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
