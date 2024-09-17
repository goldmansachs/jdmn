
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "generateTemporalObjects"})
public class GenerateTemporalObjectsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.time.LocalDate date;
    private java.time.temporal.TemporalAccessor datetime;

    public GenerateTemporalObjectsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("date")
    public java.time.LocalDate getDate() {
        return this.date;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("date")
    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("datetime")
    public java.time.temporal.TemporalAccessor getDatetime() {
        return this.datetime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("datetime")
    public void setDatetime(java.time.temporal.TemporalAccessor datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenerateTemporalObjectsRuleOutput other = (GenerateTemporalObjectsRuleOutput) o;
        if (this.getDate() != null ? !this.getDate().equals(other.getDate()) : other.getDate() != null) return false;
        if (this.getDatetime() != null ? !this.getDatetime().equals(other.getDatetime()) : other.getDatetime() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDate() != null ? this.getDate().hashCode() : 0);
        result = 31 * result + (this.getDatetime() != null ? this.getDatetime().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", date='%s'", date));
        result_.append(String.format(", datetime='%s'", datetime));
        result_.append(")");
        return result_.toString();
    }
}
