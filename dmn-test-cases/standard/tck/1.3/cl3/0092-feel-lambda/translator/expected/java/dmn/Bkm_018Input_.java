
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm_018"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Bkm_018Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String p1;
    private String p2;

    public Bkm_018Input_() {
    }

    public Bkm_018Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object p1 = input_.get("p1");
            setP1((String)p1);
            Object p2 = input_.get("p2");
            setP2((String)p2);
        }
    }

    public String getP1() {
        return this.p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return this.p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

}
