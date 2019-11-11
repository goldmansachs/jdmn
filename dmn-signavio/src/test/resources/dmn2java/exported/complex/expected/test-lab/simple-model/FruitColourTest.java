
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "08d5b0168ba542ffa8ea53ec655be662/sid-6AB62441-ABC4-4239-8D31-B7D3B615D77B"})
public class FruitColourTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final FruitColour fruitColour = new FruitColour();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        String fruits = "Apples";
        String fruitColour = this.fruitColour.apply(fruits, annotationSet_);

        checkValues("Red", fruitColour);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        String fruits = "Bananas";
        String fruitColour = this.fruitColour.apply(fruits, annotationSet_);

        checkValues("Yellow", fruitColour);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        String fruits = "Grapes";
        String fruitColour = this.fruitColour.apply(fruits, annotationSet_);

        checkValues("Green", fruitColour);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
