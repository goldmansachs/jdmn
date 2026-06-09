
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compile"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompileInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String name;
    private List<java.lang.Number> numbers;
    private List<String> trafficLight;

    public CompileInput_() {
    }

    public CompileInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object name = input_.get("http://www.provider.com/dmn/1.1/diagram/6776bb215482477ba041c80c9c559985.xml#name");
            setName((String)name);
            Object numbers = input_.get("http://www.provider.com/dmn/1.1/diagram/6776bb215482477ba041c80c9c559985.xml#numbers");
            setNumbers((List<java.lang.Number>)numbers);
            Object trafficLight = input_.get("http://www.provider.com/dmn/1.1/diagram/6776bb215482477ba041c80c9c559985.xml#trafficLight");
            setTrafficLight((List<String>)trafficLight);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<java.lang.Number> getNumbers() {
        return this.numbers;
    }

    public void setNumbers(List<java.lang.Number> numbers) {
        this.numbers = numbers;
    }

    public List<String> getTrafficLight() {
        return this.trafficLight;
    }

    public void setTrafficLight(List<String> trafficLight) {
        this.trafficLight = trafficLight;
    }

}
