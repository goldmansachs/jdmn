
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "temporal"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TemporalInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.temporal.TemporalAccessor dateTime;

    public TemporalInput_() {
    }

    public TemporalInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateTime = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#dateTime");
            setDateTime((java.time.temporal.TemporalAccessor)dateTime);
        }
    }

    public java.time.temporal.TemporalAccessor getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(java.time.temporal.TemporalAccessor dateTime) {
        this.dateTime = dateTime;
    }

}
