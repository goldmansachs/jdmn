package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipImpl implements Zip {
        private String names;
        private java.lang.Number ages;
        private java.lang.Number dateDiffs;
        private java.lang.Number dateTimeDiffs;
        private java.lang.Number temporalUnits;
        private String agesListDescription;

    public ZipImpl() {
    }

    public ZipImpl(java.lang.Number ages, String agesListDescription, java.lang.Number dateDiffs, java.lang.Number dateTimeDiffs, String names, java.lang.Number temporalUnits) {
        this.setAges(ages);
        this.setAgesListDescription(agesListDescription);
        this.setDateDiffs(dateDiffs);
        this.setDateTimeDiffs(dateTimeDiffs);
        this.setNames(names);
        this.setTemporalUnits(temporalUnits);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ages")
    public java.lang.Number getAges() {
        return this.ages;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("ages")
    public void setAges(java.lang.Number ages) {
        this.ages = ages;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("agesListDescription")
    public String getAgesListDescription() {
        return this.agesListDescription;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("agesListDescription")
    public void setAgesListDescription(String agesListDescription) {
        this.agesListDescription = agesListDescription;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateDiffs")
    public java.lang.Number getDateDiffs() {
        return this.dateDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateDiffs")
    public void setDateDiffs(java.lang.Number dateDiffs) {
        this.dateDiffs = dateDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiffs")
    public java.lang.Number getDateTimeDiffs() {
        return this.dateTimeDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeDiffs")
    public void setDateTimeDiffs(java.lang.Number dateTimeDiffs) {
        this.dateTimeDiffs = dateTimeDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("names")
    public String getNames() {
        return this.names;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("names")
    public void setNames(String names) {
        this.names = names;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("temporalUnits")
    public java.lang.Number getTemporalUnits() {
        return this.temporalUnits;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("temporalUnits")
    public void setTemporalUnits(java.lang.Number temporalUnits) {
        this.temporalUnits = temporalUnits;
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
