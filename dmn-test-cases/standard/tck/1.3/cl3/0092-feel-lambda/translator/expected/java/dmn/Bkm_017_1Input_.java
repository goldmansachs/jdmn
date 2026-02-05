
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm_017_1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Bkm_017_1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private com.gs.dmn.runtime.LambdaExpression<Boolean> fn1;

    public Bkm_017_1Input_() {
    }

    public Bkm_017_1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object fn1 = input_.get("fn1");
            setFn1((com.gs.dmn.runtime.LambdaExpression<Boolean>)fn1);
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> getFn1() {
        return this.fn1;
    }

    public void setFn1(com.gs.dmn.runtime.LambdaExpression<Boolean> fn1) {
        this.fn1 = fn1;
    }

}
