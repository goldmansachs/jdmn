
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "outputExecutionAnalysisResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class OutputExecutionAnalysisResultInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number inputValue;

    public OutputExecutionAnalysisResultInput_() {
    }

    public OutputExecutionAnalysisResultInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object inputValue = input_.get("http://www.provider.com/dmn/1.1/diagram/afb776a3dcf84f12b17e44405f5c80c5.xml#inputValue");
            setInputValue((java.lang.Number)inputValue);
        }
    }

    public java.lang.Number getInputValue() {
        return this.inputValue;
    }

    public void setInputValue(java.lang.Number inputValue) {
        this.inputValue = inputValue;
    }

}
