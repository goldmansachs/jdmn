
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decisionLitexp"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionLitexpInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> censored;
    private List<String> labels;
    private List<java.lang.Number> numz;
    private String redgreenblue1;
    private String redgreenblue2;
    private List<String> redgreenbluelist1;
    private List<String> redgreenbluelist2;

    public DecisionLitexpInput_() {
    }

    public DecisionLitexpInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object censored = input_.get("censored");
            setCensored((List<String>)censored);
            Object labels = input_.get("labels");
            setLabels((List<String>)labels);
            Object numz = input_.get("numz");
            setNumz((List<java.lang.Number>)numz);
            Object redgreenblue1 = input_.get("redgreenblue1");
            setRedgreenblue1((String)redgreenblue1);
            Object redgreenblue2 = input_.get("redgreenblue2");
            setRedgreenblue2((String)redgreenblue2);
            Object redgreenbluelist1 = input_.get("redgreenbluelist1");
            setRedgreenbluelist1((List<String>)redgreenbluelist1);
            Object redgreenbluelist2 = input_.get("redgreenbluelist2");
            setRedgreenbluelist2((List<String>)redgreenbluelist2);
        }
    }

    public List<String> getCensored() {
        return this.censored;
    }

    public void setCensored(List<String> censored) {
        this.censored = censored;
    }

    public List<String> getLabels() {
        return this.labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<java.lang.Number> getNumz() {
        return this.numz;
    }

    public void setNumz(List<java.lang.Number> numz) {
        this.numz = numz;
    }

    public String getRedgreenblue1() {
        return this.redgreenblue1;
    }

    public void setRedgreenblue1(String redgreenblue1) {
        this.redgreenblue1 = redgreenblue1;
    }

    public String getRedgreenblue2() {
        return this.redgreenblue2;
    }

    public void setRedgreenblue2(String redgreenblue2) {
        this.redgreenblue2 = redgreenblue2;
    }

    public List<String> getRedgreenbluelist1() {
        return this.redgreenbluelist1;
    }

    public void setRedgreenbluelist1(List<String> redgreenbluelist1) {
        this.redgreenbluelist1 = redgreenbluelist1;
    }

    public List<String> getRedgreenbluelist2() {
        return this.redgreenbluelist2;
    }

    public void setRedgreenbluelist2(List<String> redgreenbluelist2) {
        this.redgreenbluelist2 = redgreenbluelist2;
    }

}
