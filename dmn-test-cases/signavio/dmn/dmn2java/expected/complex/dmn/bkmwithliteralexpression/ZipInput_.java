
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> blacklist;
    private List<java.lang.Number> listOfNumbers;
    private List<String> names;
    private String rgb1;
    private List<String> rgb1List;
    private String rgb2;
    private List<String> rgb2List;

    public ZipInput_() {
    }

    public ZipInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object blacklist = input_.get("blacklist");
            setBlacklist((List<String>)blacklist);
            Object listOfNumbers = input_.get("ListOfNumbers");
            setListOfNumbers((List<java.lang.Number>)listOfNumbers);
            Object names = input_.get("names");
            setNames((List<String>)names);
            Object rgb1 = input_.get("rgb1");
            setRgb1((String)rgb1);
            Object rgb1List = input_.get("rgb1 list");
            setRgb1List((List<String>)rgb1List);
            Object rgb2 = input_.get("rgb2");
            setRgb2((String)rgb2);
            Object rgb2List = input_.get("rgb2 list");
            setRgb2List((List<String>)rgb2List);
        }
    }

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
