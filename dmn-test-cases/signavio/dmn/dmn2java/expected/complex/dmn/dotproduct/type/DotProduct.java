package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "dotProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.DotProductImpl.class)
public interface DotProduct extends com.gs.dmn.runtime.DMNType {
    static DotProduct toDotProduct(Object other) {
        if (other == null) {
            return null;
        } else if (DotProduct.class.isAssignableFrom(other.getClass())) {
            return (DotProduct)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            DotProductImpl result_ = new DotProductImpl();
            result_.setDotProduct2((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("dotProduct2", "DotProduct"));
            result_.setOutputMessage((String)((com.gs.dmn.runtime.Context)other).get("outputMessage", "Output Message"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toDotProduct(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), DotProduct.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DotProduct")
    java.math.BigDecimal getDotProduct2();

    @com.fasterxml.jackson.annotation.JsonGetter("Output Message")
    String getOutputMessage();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("dotProduct2", getDotProduct2());
        context.put("outputMessage", getOutputMessage());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DotProduct other = (DotProduct) o;
        if (this.getDotProduct2() != null ? !this.getDotProduct2().equals(other.getDotProduct2()) : other.getDotProduct2() != null) return false;
        if (this.getOutputMessage() != null ? !this.getOutputMessage().equals(other.getOutputMessage()) : other.getOutputMessage() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDotProduct2() != null ? this.getDotProduct2().hashCode() : 0);
        result = 31 * result + (this.getOutputMessage() != null ? this.getOutputMessage().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("DotProduct=" + getDotProduct2());
        result_.append(", Output Message=" + getOutputMessage());
        result_.append("}");
        return result_.toString();
    }
}
