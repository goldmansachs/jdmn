package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "compile"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompileImpl implements Compile {
        private String nextTrafficLight;
        private java.math.BigDecimal avgOfNumbers;
        private String name;

    public CompileImpl() {
    }

    public CompileImpl(java.math.BigDecimal avgOfNumbers, String name, String nextTrafficLight) {
        this.setAvgOfNumbers(avgOfNumbers);
        this.setName(name);
        this.setNextTrafficLight(nextTrafficLight);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("avg of numbers")
    public java.math.BigDecimal getAvgOfNumbers() {
        return this.avgOfNumbers;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("avg of numbers")
    public void setAvgOfNumbers(java.math.BigDecimal avgOfNumbers) {
        this.avgOfNumbers = avgOfNumbers;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Next traffic light")
    public String getNextTrafficLight() {
        return this.nextTrafficLight;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Next traffic light")
    public void setNextTrafficLight(String nextTrafficLight) {
        this.nextTrafficLight = nextTrafficLight;
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
