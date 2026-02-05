
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "datetimeOperators"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DatetimeOperatorsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor datetime;

    public DatetimeOperatorsInput_() {
    }

    public DatetimeOperatorsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object datetime = input_.get("datetime");
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
