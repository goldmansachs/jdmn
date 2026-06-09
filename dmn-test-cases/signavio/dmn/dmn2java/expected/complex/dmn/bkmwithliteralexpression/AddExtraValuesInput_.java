
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "addExtraValues"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AddExtraValuesInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> listOfNumbers;

    public AddExtraValuesInput_() {
    }

    public AddExtraValuesInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object listOfNumbers = input_.get("http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml#listOfNumbers");
            setListOfNumbers((List<java.lang.Number>)listOfNumbers);
        }
    }

    public List<java.lang.Number> getListOfNumbers() {
        return this.listOfNumbers;
    }

    public void setListOfNumbers(List<java.lang.Number> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

}
