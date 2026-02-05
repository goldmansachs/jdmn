
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "sort2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Sort2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<type.TRow> tableB;

    public Sort2Input_() {
    }

    public Sort2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object tableB = input_.get("tableB");
            setTableB((List<type.TRow>)((java.util.List)tableB).stream().map(x_ -> type.TRow.toTRow(x_)).collect(java.util.stream.Collectors.toList()));
        }
    }

    public List<type.TRow> getTableB() {
        return this.tableB;
    }

    public void setTableB(List<type.TRow> tableB) {
        this.tableB = tableB;
    }

}
