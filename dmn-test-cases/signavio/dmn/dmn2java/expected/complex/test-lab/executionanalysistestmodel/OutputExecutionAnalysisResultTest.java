
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "afb776a3dcf84f12b17e44405f5c80c5/sid-7B2CED2F-3433-4517-AAB4-08447E0A3C6C"})
public class OutputExecutionAnalysisResultTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final OutputExecutionAnalysisResult outputExecutionAnalysisResult = new OutputExecutionAnalysisResult();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal inputValue = number("0");
        List<String> outputExecutionAnalysisResult = this.outputExecutionAnalysisResult.apply(inputValue, annotationSet_);

        checkValues(asList(), outputExecutionAnalysisResult);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal inputValue = number("2");
        List<String> outputExecutionAnalysisResult = this.outputExecutionAnalysisResult.apply(inputValue, annotationSet_);

        checkValues(asList("Output1", "Output2"), outputExecutionAnalysisResult);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal inputValue = number("5");
        List<String> outputExecutionAnalysisResult = this.outputExecutionAnalysisResult.apply(inputValue, annotationSet_);

        checkValues(asList("Output1", "Output2", "Output3", "Output4", "Output5"), outputExecutionAnalysisResult);
    }

    @org.junit.Test
    public void testCase4() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal inputValue = number("8");
        List<String> outputExecutionAnalysisResult = this.outputExecutionAnalysisResult.apply(inputValue, annotationSet_);

        checkValues(asList("Output1", "Output2", "Output3", "Output4", "Output5", "Output6", "Output7", "Output8"), outputExecutionAnalysisResult);
    }

    @org.junit.Test
    public void testCase5() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal inputValue = number("10");
        List<String> outputExecutionAnalysisResult = this.outputExecutionAnalysisResult.apply(inputValue, annotationSet_);

        checkValues(asList("Output1", "Output2", "Output3", "Output4", "Output5", "Output6", "Output7", "Output8", "Output9", "Output10"), outputExecutionAnalysisResult);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
