package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tRow"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TRow extends com.gs.dmn.runtime.DMNType {
    static TRow toTRow(Object other) {
        if (other == null) {
            return null;
        } else if (TRow.class.isAssignableFrom(other.getClass())) {
            return (TRow)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TRowImpl result_ = new TRowImpl();
            result_.setCol1((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("col1"));
            result_.setCol2((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("col2"));
            result_.setCol3((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("col3"));
            result_.setCol4((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("col4"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTRow(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TRow.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("col1")
    java.math.BigDecimal getCol1();

    @com.fasterxml.jackson.annotation.JsonGetter("col2")
    java.math.BigDecimal getCol2();

    @com.fasterxml.jackson.annotation.JsonGetter("col3")
    java.math.BigDecimal getCol3();

    @com.fasterxml.jackson.annotation.JsonGetter("col4")
    java.math.BigDecimal getCol4();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("col1", getCol1());
        context.put("col2", getCol2());
        context.put("col3", getCol3());
        context.put("col4", getCol4());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TRow other = (TRow) o;
        if (this.getCol1() != null ? !this.getCol1().equals(other.getCol1()) : other.getCol1() != null) return false;
        if (this.getCol2() != null ? !this.getCol2().equals(other.getCol2()) : other.getCol2() != null) return false;
        if (this.getCol3() != null ? !this.getCol3().equals(other.getCol3()) : other.getCol3() != null) return false;
        if (this.getCol4() != null ? !this.getCol4().equals(other.getCol4()) : other.getCol4() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getCol1() != null ? this.getCol1().hashCode() : 0);
        result = 31 * result + (this.getCol2() != null ? this.getCol2().hashCode() : 0);
        result = 31 * result + (this.getCol3() != null ? this.getCol3().hashCode() : 0);
        result = 31 * result + (this.getCol4() != null ? this.getCol4().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("col1=" + getCol1());
        result_.append(", col2=" + getCol2());
        result_.append(", col3=" + getCol3());
        result_.append(", col4=" + getCol4());
        result_.append("}");
        return result_.toString();
    }
}
