
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm_011_1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Bkm_011_1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn1;
    private com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn2;

    public Bkm_011_1Input_() {
    }

    public Bkm_011_1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object fn1 = input_.get("fn1");
            setFn1((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)fn1);
            Object fn2 = input_.get("fn2");
            setFn2((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)fn2);
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> getFn1() {
        return this.fn1;
    }

    public void setFn1(com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn1) {
        this.fn1 = fn1;
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> getFn2() {
        return this.fn2;
    }

    public void setFn2(com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn2) {
        this.fn2 = fn2;
    }

}
