package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tApplicantData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TApplicantDataImpl.class)
public interface TApplicantData extends com.gs.dmn.runtime.DMNType {
    static TApplicantData toTApplicantData(Object other) {
        if (other == null) {
            return null;
        } else if (TApplicantData.class.isAssignableFrom(other.getClass())) {
            return (TApplicantData)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TApplicantDataImpl result_ = new TApplicantDataImpl();
            result_.setMonthly((type.Monthly)((com.gs.dmn.runtime.Context)other).get("Monthly"));
            result_.setAge((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Age"));
            result_.setExistingCustomer((Boolean)((com.gs.dmn.runtime.Context)other).get("ExistingCustomer"));
            result_.setMaritalStatus((String)((com.gs.dmn.runtime.Context)other).get("MaritalStatus"));
            result_.setEmploymentStatus((String)((com.gs.dmn.runtime.Context)other).get("EmploymentStatus"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTApplicantData(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else if (other instanceof proto.TApplicantData) {
            TApplicantDataImpl result_ = new TApplicantDataImpl();
            result_.setMonthly(type.Monthly.toMonthly(((proto.TApplicantData) other).getMonthly()));
            result_.setAge(java.math.BigDecimal.valueOf(((proto.TApplicantData) other).getAge()));
            result_.setExistingCustomer(((proto.TApplicantData) other).getExistingCustomer());
            result_.setMaritalStatus(((proto.TApplicantData) other).getMaritalStatus());
            result_.setEmploymentStatus(((proto.TApplicantData) other).getEmploymentStatus());
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TApplicantData.class.getSimpleName()));
        }
    }

    static proto.TApplicantData toProto(TApplicantData other) {
        proto.TApplicantData.Builder result_ = proto.TApplicantData.newBuilder();
        if (other != null) {
            proto.Monthly monthlyProto_ = type.Monthly.toProto(((TApplicantData) other).getMonthly());
            if (monthlyProto_ != null) {
                result_.setMonthly(monthlyProto_);
            }
            Double ageProto_ = (((TApplicantData) other).getAge() == null ? 0.0 : ((TApplicantData) other).getAge().doubleValue());
            result_.setAge(ageProto_);
            Boolean existingCustomerProto_ = (((TApplicantData) other).getExistingCustomer() == null ? false : ((TApplicantData) other).getExistingCustomer());
            result_.setExistingCustomer(existingCustomerProto_);
            String maritalStatusProto_ = (((TApplicantData) other).getMaritalStatus() == null ? "" : ((TApplicantData) other).getMaritalStatus());
            result_.setMaritalStatus(maritalStatusProto_);
            String employmentStatusProto_ = (((TApplicantData) other).getEmploymentStatus() == null ? "" : ((TApplicantData) other).getEmploymentStatus());
            result_.setEmploymentStatus(employmentStatusProto_);
        }
        return result_.build();
    }

    static List<proto.TApplicantData> toProto(List<TApplicantData> other) {
        if (other == null) {
            return null;
        } else {
            return other.stream().map(o -> toProto(o)).collect(java.util.stream.Collectors.toList());
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Monthly")
    type.Monthly getMonthly();

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    java.math.BigDecimal getAge();

    @com.fasterxml.jackson.annotation.JsonGetter("ExistingCustomer")
    Boolean getExistingCustomer();

    @com.fasterxml.jackson.annotation.JsonGetter("MaritalStatus")
    String getMaritalStatus();

    @com.fasterxml.jackson.annotation.JsonGetter("EmploymentStatus")
    String getEmploymentStatus();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("monthly", getMonthly());
        context.put("age", getAge());
        context.put("existingCustomer", getExistingCustomer());
        context.put("maritalStatus", getMaritalStatus());
        context.put("employmentStatus", getEmploymentStatus());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TApplicantData other = (TApplicantData) o;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getEmploymentStatus() != null ? !this.getEmploymentStatus().equals(other.getEmploymentStatus()) : other.getEmploymentStatus() != null) return false;
        if (this.getExistingCustomer() != null ? !this.getExistingCustomer().equals(other.getExistingCustomer()) : other.getExistingCustomer() != null) return false;
        if (this.getMaritalStatus() != null ? !this.getMaritalStatus().equals(other.getMaritalStatus()) : other.getMaritalStatus() != null) return false;
        if (this.getMonthly() != null ? !this.getMonthly().equals(other.getMonthly()) : other.getMonthly() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getEmploymentStatus() != null ? this.getEmploymentStatus().hashCode() : 0);
        result = 31 * result + (this.getExistingCustomer() != null ? this.getExistingCustomer().hashCode() : 0);
        result = 31 * result + (this.getMaritalStatus() != null ? this.getMaritalStatus().hashCode() : 0);
        result = 31 * result + (this.getMonthly() != null ? this.getMonthly().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Age=" + getAge());
        result_.append(", EmploymentStatus=" + getEmploymentStatus());
        result_.append(", ExistingCustomer=" + getExistingCustomer());
        result_.append(", MaritalStatus=" + getMaritalStatus());
        result_.append(", Monthly=" + getMonthly());
        result_.append("}");
        return result_.toString();
    }
}
