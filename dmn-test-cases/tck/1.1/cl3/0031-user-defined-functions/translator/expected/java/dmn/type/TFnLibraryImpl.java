package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnLibrary"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnLibraryImpl implements TFnLibrary {
        private Object sumFn;
        private Object subFn;
        private Object multiplyFn;
        private Object divideFn;

    public TFnLibraryImpl() {
    }

    public TFnLibraryImpl(Object divideFn, Object multiplyFn, Object subFn, Object sumFn) {
        this.setDivideFn(divideFn);
        this.setMultiplyFn(multiplyFn);
        this.setSubFn(subFn);
        this.setSumFn(sumFn);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("divideFn")
    public Object getDivideFn() {
        return this.divideFn;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("divideFn")
    public void setDivideFn(Object divideFn) {
        this.divideFn = divideFn;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("multiplyFn")
    public Object getMultiplyFn() {
        return this.multiplyFn;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("multiplyFn")
    public void setMultiplyFn(Object multiplyFn) {
        this.multiplyFn = multiplyFn;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subFn")
    public Object getSubFn() {
        return this.subFn;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("subFn")
    public void setSubFn(Object subFn) {
        this.subFn = subFn;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sumFn")
    public Object getSumFn() {
        return this.sumFn;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("sumFn")
    public void setSumFn(Object sumFn) {
        this.sumFn = sumFn;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
