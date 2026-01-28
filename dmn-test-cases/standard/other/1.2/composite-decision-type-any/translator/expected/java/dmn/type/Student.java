package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Student"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.StudentImpl.class)
public interface Student extends com.gs.dmn.runtime.DMNType {
    static Student toStudent(Object other) {
        if (other == null) {
            return null;
        } else if (Student.class.isAssignableFrom(other.getClass())) {
            return (Student)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            StudentImpl result_ = new StudentImpl();
            Object age = ((com.gs.dmn.runtime.Context)other).get("age");
            result_.setAge((java.lang.Number)age);
            Object classification = ((com.gs.dmn.runtime.Context)other).get("classification");
            result_.setClassification((String)classification);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toStudent(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Student.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    java.lang.Number getAge();

    @com.fasterxml.jackson.annotation.JsonGetter("classification")
    String getClassification();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("age", getAge());
        context.add("classification", getClassification());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student other = (Student) o;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getClassification() != null ? !this.getClassification().equals(other.getClassification()) : other.getClassification() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getClassification() != null ? this.getClassification().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("age=" + getAge());
        result_.append(", classification=" + getClassification());
        result_.append("}");
        return result_.toString();
    }
}
