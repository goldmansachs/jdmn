package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.ZipImpl.class)
public interface Zip extends com.gs.dmn.runtime.DMNType {
    static Zip toZip(Object other) {
        if (other == null) {
            return null;
        } else if (Zip.class.isAssignableFrom(other.getClass())) {
            return (Zip)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ZipImpl result_ = new ZipImpl();
            result_.setN((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("n", "n"));
            result_.setE((String)((com.gs.dmn.runtime.Context)other).get("e", "e"));
            result_.setT((String)((com.gs.dmn.runtime.Context)other).get("t", "t"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toZip(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Zip.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("n")
    java.math.BigDecimal getN();

    @com.fasterxml.jackson.annotation.JsonGetter("e")
    String getE();

    @com.fasterxml.jackson.annotation.JsonGetter("t")
    String getT();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("n", getN());
        context.put("e", getE());
        context.put("t", getT());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zip other = (Zip) o;
        if (this.getE() != null ? !this.getE().equals(other.getE()) : other.getE() != null) return false;
        if (this.getN() != null ? !this.getN().equals(other.getN()) : other.getN() != null) return false;
        if (this.getT() != null ? !this.getT().equals(other.getT()) : other.getT() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getE() != null ? this.getE().hashCode() : 0);
        result = 31 * result + (this.getN() != null ? this.getN().hashCode() : 0);
        result = 31 * result + (this.getT() != null ? this.getT().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("e=" + getE());
        result_.append(", n=" + getN());
        result_.append(", t=" + getT());
        result_.append("}");
        return result_.toString();
    }
}
