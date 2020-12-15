package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tApproval"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TApprovalImpl implements TApproval {
        private String status;
        private String rate;

    public TApprovalImpl() {
    }

    public TApprovalImpl(String rate, String status) {
        this.setRate(rate);
        this.setStatus(status);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Rate")
    public String getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Rate")
    public void setRate(String rate) {
        this.rate = rate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Status")
    public String getStatus() {
        return this.status;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Status")
    public void setStatus(String status) {
        this.status = status;
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
