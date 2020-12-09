package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Applicant"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.ApplicantImpl.class)
public interface Applicant extends com.gs.dmn.runtime.DMNType {
    static Applicant toApplicant(Object other) {
        if (other == null) {
            return null;
        } else if (Applicant.class.isAssignableFrom(other.getClass())) {
            return (Applicant)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ApplicantImpl result_ = new ApplicantImpl();
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name"));
            result_.setAge((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("age"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toApplicant(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Applicant.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    java.math.BigDecimal getAge();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("name", getName());
        context.put("age", getAge());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Applicant other = (Applicant) o;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("age=" + getAge());
        result_.append(", name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
