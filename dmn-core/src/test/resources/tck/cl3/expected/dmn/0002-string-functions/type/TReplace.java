package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tReplace"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TReplace extends com.gs.dmn.runtime.DMNType {
    static TReplace toTReplace(Object other) {
        if (other == null) {
            return null;
        } else if (TReplace.class.isAssignableFrom(other.getClass())) {
            return (TReplace)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TReplaceImpl result_ = new TReplaceImpl();
            result_.setAao((String)((com.gs.dmn.runtime.Context)other).get("Aao"));
            result_.setAanplusStarstar((String)((com.gs.dmn.runtime.Context)other).get("AanplusStarstar"));
            result_.setEncloseVowels((String)((com.gs.dmn.runtime.Context)other).get("encloseVowels"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTReplace(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TReplace.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Aao")
    String getAao();

    @com.fasterxml.jackson.annotation.JsonGetter("AanplusStarstar")
    String getAanplusStarstar();

    @com.fasterxml.jackson.annotation.JsonGetter("encloseVowels")
    String getEncloseVowels();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("aao", getAao());
        context.put("aanplusStarstar", getAanplusStarstar());
        context.put("encloseVowels", getEncloseVowels());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TReplace other = (TReplace) o;
        if (this.getAanplusStarstar() != null ? !this.getAanplusStarstar().equals(other.getAanplusStarstar()) : other.getAanplusStarstar() != null) return false;
        if (this.getAao() != null ? !this.getAao().equals(other.getAao()) : other.getAao() != null) return false;
        if (this.getEncloseVowels() != null ? !this.getEncloseVowels().equals(other.getEncloseVowels()) : other.getEncloseVowels() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAanplusStarstar() != null ? this.getAanplusStarstar().hashCode() : 0);
        result = 31 * result + (this.getAao() != null ? this.getAao().hashCode() : 0);
        result = 31 * result + (this.getEncloseVowels() != null ? this.getEncloseVowels().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("AanplusStarstar=" + getAanplusStarstar());
        result_.append(", Aao=" + getAao());
        result_.append(", encloseVowels=" + getEncloseVowels());
        result_.append("}");
        return result_.toString();
    }
}
