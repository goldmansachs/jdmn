package parentlinked;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "somethingElse"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class SomethingElseInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number num;

    public SomethingElseInput_() {
    }

    public SomethingElseInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object num = input_.get("http://www.provider.com/dmn/1.1/diagram/c3d5f975281b4d2f829ee2c77b320f01.xml#num");
            setNum((java.lang.Number)num);
        }
    }

    public java.lang.Number getNum() {
        return this.num;
    }

    public void setNum(java.lang.Number num) {
        this.num = num;
    }

}
