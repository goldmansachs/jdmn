package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.PersonImpl.class)
public interface Person extends com.gs.dmn.runtime.DMNType {
    static Person toPerson(Object other) {
        if (other == null) {
            return null;
        } else if (Person.class.isAssignableFrom(other.getClass())) {
            return (Person)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            PersonImpl result_ = new PersonImpl();
            Object firstName = ((com.gs.dmn.runtime.Context)other).get("firstName", "firstName");
            result_.setFirstName((String)firstName);
            Object lastName = ((com.gs.dmn.runtime.Context)other).get("lastName", "lastName");
            result_.setLastName((String)lastName);
            Object id = ((com.gs.dmn.runtime.Context)other).get("id", "id");
            result_.setId((java.lang.Number)id);
            Object gender = ((com.gs.dmn.runtime.Context)other).get("gender", "gender");
            result_.setGender((String)gender);
            Object dateOfBirth = ((com.gs.dmn.runtime.Context)other).get("dateOfBirth", "dateOfBirth");
            result_.setDateOfBirth((java.time.LocalDate)dateOfBirth);
            Object timeOfBirth = ((com.gs.dmn.runtime.Context)other).get("timeOfBirth", "timeOfBirth");
            result_.setTimeOfBirth((java.time.temporal.TemporalAccessor)timeOfBirth);
            Object dateTimeOfBirth = ((com.gs.dmn.runtime.Context)other).get("dateTimeOfBirth", "dateTimeOfBirth");
            result_.setDateTimeOfBirth((java.time.temporal.TemporalAccessor)dateTimeOfBirth);
            Object list = ((com.gs.dmn.runtime.Context)other).get("list", "list");
            result_.setList((List<String>)list);
            Object married = ((com.gs.dmn.runtime.Context)other).get("married", "married");
            result_.setMarried((Boolean)married);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toPerson(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Person.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("firstName")
    String getFirstName();

    @com.fasterxml.jackson.annotation.JsonGetter("lastName")
    String getLastName();

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    java.lang.Number getId();

    @com.fasterxml.jackson.annotation.JsonGetter("gender")
    String getGender();

    @com.fasterxml.jackson.annotation.JsonGetter("dateOfBirth")
    java.time.LocalDate getDateOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("timeOfBirth")
    java.time.temporal.TemporalAccessor getTimeOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeOfBirth")
    java.time.temporal.TemporalAccessor getDateTimeOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("list")
    List<String> getList();

    @com.fasterxml.jackson.annotation.JsonGetter("married")
    Boolean getMarried();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("firstName", getFirstName());
        context.put("lastName", getLastName());
        context.put("id", getId());
        context.put("gender", getGender());
        context.put("dateOfBirth", getDateOfBirth());
        context.put("timeOfBirth", getTimeOfBirth());
        context.put("dateTimeOfBirth", getDateTimeOfBirth());
        context.put("list", getList());
        context.put("married", getMarried());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person other = (Person) o;
        if (this.getDateOfBirth() != null ? !this.getDateOfBirth().equals(other.getDateOfBirth()) : other.getDateOfBirth() != null) return false;
        if (this.getDateTimeOfBirth() != null ? !this.getDateTimeOfBirth().equals(other.getDateTimeOfBirth()) : other.getDateTimeOfBirth() != null) return false;
        if (this.getFirstName() != null ? !this.getFirstName().equals(other.getFirstName()) : other.getFirstName() != null) return false;
        if (this.getGender() != null ? !this.getGender().equals(other.getGender()) : other.getGender() != null) return false;
        if (this.getId() != null ? !this.getId().equals(other.getId()) : other.getId() != null) return false;
        if (this.getLastName() != null ? !this.getLastName().equals(other.getLastName()) : other.getLastName() != null) return false;
        if (this.getList() != null ? !this.getList().equals(other.getList()) : other.getList() != null) return false;
        if (this.getMarried() != null ? !this.getMarried().equals(other.getMarried()) : other.getMarried() != null) return false;
        if (this.getTimeOfBirth() != null ? !this.getTimeOfBirth().equals(other.getTimeOfBirth()) : other.getTimeOfBirth() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDateOfBirth() != null ? this.getDateOfBirth().hashCode() : 0);
        result = 31 * result + (this.getDateTimeOfBirth() != null ? this.getDateTimeOfBirth().hashCode() : 0);
        result = 31 * result + (this.getFirstName() != null ? this.getFirstName().hashCode() : 0);
        result = 31 * result + (this.getGender() != null ? this.getGender().hashCode() : 0);
        result = 31 * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (this.getLastName() != null ? this.getLastName().hashCode() : 0);
        result = 31 * result + (this.getList() != null ? this.getList().hashCode() : 0);
        result = 31 * result + (this.getMarried() != null ? this.getMarried().hashCode() : 0);
        result = 31 * result + (this.getTimeOfBirth() != null ? this.getTimeOfBirth().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("dateOfBirth=" + getDateOfBirth());
        result_.append(", dateTimeOfBirth=" + getDateTimeOfBirth());
        result_.append(", firstName=" + getFirstName());
        result_.append(", gender=" + getGender());
        result_.append(", id=" + getId());
        result_.append(", lastName=" + getLastName());
        result_.append(", list=" + getList());
        result_.append(", married=" + getMarried());
        result_.append(", timeOfBirth=" + getTimeOfBirth());
        result_.append("}");
        return result_.toString();
    }
}
