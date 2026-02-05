
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "monthlyPayment"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class MonthlyPaymentInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number p;
    private java.lang.Number r;
    private java.lang.Number n;

    public MonthlyPaymentInput_() {
    }

    public MonthlyPaymentInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object p = input_.get("p");
            setP((java.lang.Number)p);
            Object r = input_.get("r");
            setR((java.lang.Number)r);
            Object n = input_.get("n");
            setN((java.lang.Number)n);
        }
    }

    public java.lang.Number getP() {
        return this.p;
    }

    public void setP(java.lang.Number p) {
        this.p = p;
    }

    public java.lang.Number getR() {
        return this.r;
    }

    public void setR(java.lang.Number r) {
        this.r = r;
    }

    public java.lang.Number getN() {
        return this.n;
    }

    public void setN(java.lang.Number n) {
        this.n = n;
    }

}
