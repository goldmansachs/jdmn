
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "importedLogicDates"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ImportedLogicDatesInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate date;

    public ImportedLogicDatesInput_() {
    }

    public ImportedLogicDatesInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object date = input_.get("date");
            setDate((java.time.LocalDate)date);
        }
    }

    public java.time.LocalDate getDate() {
        return this.date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

}
