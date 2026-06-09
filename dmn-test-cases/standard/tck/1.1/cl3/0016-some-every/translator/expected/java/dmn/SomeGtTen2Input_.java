
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "someGtTen2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class SomeGtTen2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<type.TItemPrice> priceTable2;

    public SomeGtTen2Input_() {
    }

    public SomeGtTen2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object priceTable2 = input_.get("http://www.trisotech.com/definitions/_d7643a02-a8fc-4a6f-a8a9-5c2881afea70#priceTable2");
            setPriceTable2((List<type.TItemPrice>)((java.util.List)priceTable2).stream().map(x_ -> type.TItemPrice.toTItemPrice(x_)).collect(java.util.stream.Collectors.toList()));
        }
    }

    public List<type.TItemPrice> getPriceTable2() {
        return this.priceTable2;
    }

    public void setPriceTable2(List<type.TItemPrice> priceTable2) {
        this.priceTable2 = priceTable2;
    }

}
