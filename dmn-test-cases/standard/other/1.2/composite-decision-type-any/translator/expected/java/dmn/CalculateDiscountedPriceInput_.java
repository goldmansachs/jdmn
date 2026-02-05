
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "calculateDiscountedPrice"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CalculateDiscountedPriceInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number price;
    private type.Student student;

    public CalculateDiscountedPriceInput_() {
    }

    public CalculateDiscountedPriceInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object price = input_.get("price");
            setPrice((java.lang.Number)price);
            Object student = input_.get("student");
            setStudent(type.Student.toStudent(student));
        }
    }

    public java.lang.Number getPrice() {
        return this.price;
    }

    public void setPrice(java.lang.Number price) {
        this.price = price;
    }

    public type.Student getStudent() {
        return this.student;
    }

    public void setStudent(type.Student student) {
        this.student = student;
    }

}
