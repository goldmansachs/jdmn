
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "BureauCallTypeTable"})
public class BureauCallTypeTableRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String bureauCallTypeTable;

    public BureauCallTypeTableRuleOutput(boolean matched) {
        super(matched);
    }

    public String getBureauCallTypeTable() {
        return this.bureauCallTypeTable;
    }
    public void setBureauCallTypeTable(String bureauCallTypeTable) {
        this.bureauCallTypeTable = bureauCallTypeTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BureauCallTypeTableRuleOutput other = (BureauCallTypeTableRuleOutput) o;
        if (this.getBureauCallTypeTable() != null ? !this.getBureauCallTypeTable().equals(other.getBureauCallTypeTable()) : other.getBureauCallTypeTable() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getBureauCallTypeTable() != null ? this.getBureauCallTypeTable().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", bureauCallTypeTable='%s'", bureauCallTypeTable));
        result_.append(")");
        return result_.toString();
    }
}
