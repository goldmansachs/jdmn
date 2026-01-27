
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compile"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompileInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String name;
    private List<java.lang.Number> numbers;
    private List<String> trafficLight;

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
