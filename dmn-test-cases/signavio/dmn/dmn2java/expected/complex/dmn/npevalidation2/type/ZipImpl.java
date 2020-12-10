package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipImpl implements Zip {
        private String names;
        private java.math.BigDecimal ages;
        private java.math.BigDecimal dateDiffs;
        private java.math.BigDecimal dateTimeDiffs;
        private java.math.BigDecimal temporalUnits;
        private String agesListDescription;

    public ZipImpl() {
    }

    public ZipImpl(java.math.BigDecimal ages, String agesListDescription, java.math.BigDecimal dateDiffs, java.math.BigDecimal dateTimeDiffs, String names, java.math.BigDecimal temporalUnits) {
        this.setAges(ages);
        this.setAgesListDescription(agesListDescription);
        this.setDateDiffs(dateDiffs);
        this.setDateTimeDiffs(dateTimeDiffs);
        this.setNames(names);
        this.setTemporalUnits(temporalUnits);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ages")
    public java.math.BigDecimal getAges() {
        return this.ages;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("ages")
    public void setAges(java.math.BigDecimal ages) {
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
    public java.math.BigDecimal getDateDiffs() {
        return this.dateDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateDiffs")
    public void setDateDiffs(java.math.BigDecimal dateDiffs) {
        this.dateDiffs = dateDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiffs")
    public java.math.BigDecimal getDateTimeDiffs() {
        return this.dateTimeDiffs;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeDiffs")
    public void setDateTimeDiffs(java.math.BigDecimal dateTimeDiffs) {
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
    public java.math.BigDecimal getTemporalUnits() {
        return this.temporalUnits;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("temporalUnits")
    public void setTemporalUnits(java.math.BigDecimal temporalUnits) {
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
