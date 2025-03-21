
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "5417bfd1893048bc9ca18c51aa11b7f0/sid-DB470E83-401F-4E28-BCA7-8E8FA641E93F"})
public class ZipTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final Zip zip = new Zip();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        java.lang.Number day = number("25");
        java.lang.Number month = number("12");
        List<java.lang.Number> ages = asList(number("25"), number("40"), number("65"), number("80"), number("105"));
        List<String> names = asList("Fred", "Jim", "Tom", "Sarah", "Kate");
        java.lang.Number second = number("10");
        java.lang.Number hour = number("8");
        java.lang.Number minute = number("5");
        java.lang.Number year = number("2016");
        List<type.Zip> zip = this.zip.apply(ages, day, hour, minute, month, names, second, year, context_);

        checkValues(asList(new type.ZipImpl(number("25"), "not exactly 1 to 5", number("0"), number("0"), "Fred", number("12")), new type.ZipImpl(number("40"), "non of the numbers 1 to 5", number("0"), number("0"), "Jim", number("2016")), new type.ZipImpl(number("65"), null, number("0"), number("0"), "Tom", number("7")), new type.ZipImpl(number("80"), null, numericUnaryMinus(number("359")), numericUnaryMinus(number("8612")), "Sarah", number("25")), new type.ZipImpl(number("105"), null, null, null, "Kate", number("5"))), zip);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        java.lang.Number day = number("1");
        java.lang.Number month = number("2");
        List<java.lang.Number> ages = asList(number("3"), number("4"), number("5"), number("2"), number("1"));
        List<String> names = asList("John", "Amy", "Tim", "James", "Ewa");
        java.lang.Number second = number("3");
        java.lang.Number hour = number("1");
        java.lang.Number minute = number("2");
        java.lang.Number year = number("3");
        List<type.Zip> zip = this.zip.apply(ages, day, hour, minute, month, names, second, year, context_);

        checkValues(asList(new type.ZipImpl(number("3"), "not exactly 1 to 5", number("0"), number("0"), "John", number("2")), new type.ZipImpl(number("4"), "only numbers between 1 and 5", number("0"), number("0"), "Amy", number("3")), new type.ZipImpl(number("5"), "at least one number betwen 1 and 5", number("0"), number("0"), "Tim", number("6")), new type.ZipImpl(number("2"), "only numbers between 1 and 5", number("735202"), number("17644858"), "James", number("1")), new type.ZipImpl(number("1"), null, null, null, "Ewa", number("2"))), zip);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
