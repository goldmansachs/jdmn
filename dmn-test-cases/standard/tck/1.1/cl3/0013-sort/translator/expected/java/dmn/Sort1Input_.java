
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "sort1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Sort1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> listA;

    public Sort1Input_() {
    }

    public Sort1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object listA = input_.get("listA");
            setListA((List<java.lang.Number>)listA);
        }
    }

    public List<java.lang.Number> getListA() {
        return this.listA;
    }

    public void setListA(List<java.lang.Number> listA) {
        this.listA = listA;
    }

}
