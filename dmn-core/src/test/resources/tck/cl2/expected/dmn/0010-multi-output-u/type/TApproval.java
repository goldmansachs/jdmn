package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tApproval"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TApproval extends com.gs.dmn.runtime.DMNType {
    static TApproval toTApproval(Object other) {
        if (other == null) {
            return null;
        } else if (TApproval.class.isAssignableFrom(other.getClass())) {
            return (TApproval)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TApprovalImpl result_ = new TApprovalImpl();
            result_.setStatus((String)((com.gs.dmn.runtime.Context)other).get("Status"));
            result_.setRate((String)((com.gs.dmn.runtime.Context)other).get("Rate"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTApproval(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TApproval.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Status")
    String getStatus();

    @com.fasterxml.jackson.annotation.JsonGetter("Rate")
    String getRate();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("status", getStatus());
        context.put("rate", getRate());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TApproval other = (TApproval) o;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;
        if (this.getStatus() != null ? !this.getStatus().equals(other.getStatus()) : other.getStatus() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        result = 31 * result + (this.getStatus() != null ? this.getStatus().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Rate=" + getRate());
        result_.append(", Status=" + getStatus());
        result_.append("}");
        return result_.toString();
    }
}
