
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "datetimeFormula"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DatetimeFormulaInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor datetime;

    public DatetimeFormulaInput_() {
    }

    public DatetimeFormulaInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object datetime = input_.get("http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml#datetime");
            setDatetime((java.time.temporal.TemporalAccessor)datetime);
        }
    }

    public java.time.temporal.TemporalAccessor getDatetime() {
        return this.datetime;
    }

    public void setDatetime(java.time.temporal.TemporalAccessor datetime) {
        this.datetime = datetime;
    }

}
