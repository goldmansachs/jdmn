
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "AppendAllTest"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AppendAllTestInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> list1;
    private List<String> list2;

    public AppendAllTestInput_() {
    }

    public AppendAllTestInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object list1 = input_.get("list1");
            setList1((List<String>)list1);
            Object list2 = input_.get("list2");
            setList2((List<String>)list2);
        }
    }

    public List<String> getList1() {
        return this.list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }

    public List<String> getList2() {
        return this.list2;
    }

    public void setList2(List<String> list2) {
        this.list2 = list2;
    }

}
