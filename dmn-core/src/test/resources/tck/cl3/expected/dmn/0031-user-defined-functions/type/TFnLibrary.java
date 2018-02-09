package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tFnLibrary"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TFnLibrary extends com.gs.dmn.runtime.DMNType {
    static TFnLibrary toTFnLibrary(Object other) {
        if (other == null) {
            return null;
        } else if (TFnLibrary.class.isAssignableFrom(other.getClass())) {
            return (TFnLibrary)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TFnLibraryImpl result_ = new TFnLibraryImpl();
            result_.setSumFn((Object)((com.gs.dmn.runtime.Context)other).get("sumFn"));
            result_.setSubFn((Object)((com.gs.dmn.runtime.Context)other).get("subFn"));
            result_.setMultiplyFn((Object)((com.gs.dmn.runtime.Context)other).get("multiplyFn"));
            result_.setDivideFn((Object)((com.gs.dmn.runtime.Context)other).get("divideFn"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTFnLibrary(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TFnLibrary.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sumFn")
    Object getSumFn();

    @com.fasterxml.jackson.annotation.JsonGetter("subFn")
    Object getSubFn();

    @com.fasterxml.jackson.annotation.JsonGetter("multiplyFn")
    Object getMultiplyFn();

    @com.fasterxml.jackson.annotation.JsonGetter("divideFn")
    Object getDivideFn();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("sumFn", getSumFn());
        context.put("subFn", getSubFn());
        context.put("multiplyFn", getMultiplyFn());
        context.put("divideFn", getDivideFn());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFnLibrary other = (TFnLibrary) o;
        if (this.getDivideFn() != null ? !this.getDivideFn().equals(other.getDivideFn()) : other.getDivideFn() != null) return false;
        if (this.getMultiplyFn() != null ? !this.getMultiplyFn().equals(other.getMultiplyFn()) : other.getMultiplyFn() != null) return false;
        if (this.getSubFn() != null ? !this.getSubFn().equals(other.getSubFn()) : other.getSubFn() != null) return false;
        if (this.getSumFn() != null ? !this.getSumFn().equals(other.getSumFn()) : other.getSumFn() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDivideFn() != null ? this.getDivideFn().hashCode() : 0);
        result = 31 * result + (this.getMultiplyFn() != null ? this.getMultiplyFn().hashCode() : 0);
        result = 31 * result + (this.getSubFn() != null ? this.getSubFn().hashCode() : 0);
        result = 31 * result + (this.getSumFn() != null ? this.getSumFn().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("divideFn=" + getDivideFn());
        result_.append(", multiplyFn=" + getMultiplyFn());
        result_.append(", subFn=" + getSubFn());
        result_.append(", sumFn=" + getSumFn());
        result_.append("}");
        return result_.toString();
    }
}
