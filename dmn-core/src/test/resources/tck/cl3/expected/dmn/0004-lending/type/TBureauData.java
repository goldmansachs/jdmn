package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tBureauData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TBureauData extends com.gs.dmn.runtime.DMNType {
    static TBureauData toTBureauData(Object other) {
        if (other == null) {
            return null;
        } else if (TBureauData.class.isAssignableFrom(other.getClass())) {
            return (TBureauData)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TBureauDataImpl result_ = new TBureauDataImpl();
            result_.setCreditScore((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("CreditScore"));
            result_.setBankrupt((Boolean)((com.gs.dmn.runtime.Context)other).get("Bankrupt"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTBureauData(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TBureauData.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("CreditScore")
    java.math.BigDecimal getCreditScore();

    @com.fasterxml.jackson.annotation.JsonGetter("Bankrupt")
    Boolean getBankrupt();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("creditScore", getCreditScore());
        context.put("bankrupt", getBankrupt());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TBureauData other = (TBureauData) o;
        if (this.getBankrupt() != null ? !this.getBankrupt().equals(other.getBankrupt()) : other.getBankrupt() != null) return false;
        if (this.getCreditScore() != null ? !this.getCreditScore().equals(other.getCreditScore()) : other.getCreditScore() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getBankrupt() != null ? this.getBankrupt().hashCode() : 0);
        result = 31 * result + (this.getCreditScore() != null ? this.getCreditScore().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Bankrupt=" + getBankrupt());
        result_.append(", CreditScore=" + getCreditScore());
        result_.append("}");
        return result_.toString();
    }
}
