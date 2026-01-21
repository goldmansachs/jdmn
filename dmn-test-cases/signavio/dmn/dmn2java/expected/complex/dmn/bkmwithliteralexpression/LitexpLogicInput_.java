
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "litexpLogic"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class LitexpLogicInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private List<String> blacklist;
        private List<java.lang.Number> listOfNumbers;
        private List<String> names;
        private String rgb1;
        private List<String> rgb1List;
        private String rgb2;
        private List<String> rgb2List;

    public List<String> getBlacklist() {
        return this.blacklist;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public List<java.lang.Number> getListOfNumbers() {
        return this.listOfNumbers;
    }

    public void setListOfNumbers(List<java.lang.Number> listOfNumbers) {
        this.listOfNumbers = listOfNumbers;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getRgb1() {
        return this.rgb1;
    }

    public void setRgb1(String rgb1) {
        this.rgb1 = rgb1;
    }

    public List<String> getRgb1List() {
        return this.rgb1List;
    }

    public void setRgb1List(List<String> rgb1List) {
        this.rgb1List = rgb1List;
    }

    public String getRgb2() {
        return this.rgb2;
    }

    public void setRgb2(String rgb2) {
        this.rgb2 = rgb2;
    }

    public List<String> getRgb2List() {
        return this.rgb2List;
    }

    public void setRgb2List(List<String> rgb2List) {
        this.rgb2List = rgb2List;
    }

}
