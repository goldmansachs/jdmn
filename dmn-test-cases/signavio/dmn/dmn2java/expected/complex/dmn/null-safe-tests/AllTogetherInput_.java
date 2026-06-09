
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "allTogether"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AllTogetherInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanA;
    private Boolean booleanB;
    private List<Boolean> booleanList;
    private java.time.LocalDate date;
    private java.time.temporal.TemporalAccessor dateTime;
    private java.lang.Number numberA;
    private java.lang.Number numberB;
    private List<java.lang.Number> numberList;
    private String string;
    private List<String> stringList;
    private java.time.temporal.TemporalAccessor time;

    public AllTogetherInput_() {
    }

    public AllTogetherInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanA = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanA");
            setBooleanA((Boolean)booleanA);
            Object booleanB = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanB");
            setBooleanB((Boolean)booleanB);
            Object booleanList = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanList");
            setBooleanList((List<Boolean>)booleanList);
            Object date = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#date");
            setDate((java.time.LocalDate)date);
            Object dateTime = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#dateTime");
            setDateTime((java.time.temporal.TemporalAccessor)dateTime);
            Object numberA = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberA");
            setNumberA((java.lang.Number)numberA);
            Object numberB = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberB");
            setNumberB((java.lang.Number)numberB);
            Object numberList = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberList");
            setNumberList((List<java.lang.Number>)numberList);
            Object string = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#string");
            setString((String)string);
            Object stringList = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#stringList");
            setStringList((List<String>)stringList);
            Object time = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#time");
            setTime((java.time.temporal.TemporalAccessor)time);
        }
    }

    public Boolean getBooleanA() {
        return this.booleanA;
    }

    public void setBooleanA(Boolean booleanA) {
        this.booleanA = booleanA;
    }

    public Boolean getBooleanB() {
        return this.booleanB;
    }

    public void setBooleanB(Boolean booleanB) {
        this.booleanB = booleanB;
    }

    public List<Boolean> getBooleanList() {
        return this.booleanList;
    }

    public void setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

    public java.time.LocalDate getDate() {
        return this.date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public java.time.temporal.TemporalAccessor getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(java.time.temporal.TemporalAccessor dateTime) {
        this.dateTime = dateTime;
    }

    public java.lang.Number getNumberA() {
        return this.numberA;
    }

    public void setNumberA(java.lang.Number numberA) {
        this.numberA = numberA;
    }

    public java.lang.Number getNumberB() {
        return this.numberB;
    }

    public void setNumberB(java.lang.Number numberB) {
        this.numberB = numberB;
    }

    public List<java.lang.Number> getNumberList() {
        return this.numberList;
    }

    public void setNumberList(List<java.lang.Number> numberList) {
        this.numberList = numberList;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

}
