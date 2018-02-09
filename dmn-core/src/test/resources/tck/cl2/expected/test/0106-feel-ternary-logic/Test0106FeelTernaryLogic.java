
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0106-feel-ternary-logic.dmn"})
public class Test0106FeelTernaryLogic extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = true;
        Boolean b = true;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = false;
        Boolean b = true;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = null;
        Boolean b = true;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = true;
        Boolean b = false;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = false;
        Boolean b = false;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = null;
        Boolean b = false;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase007() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = true;
        Boolean b = null;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase008() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = false;
        Boolean b = null;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionOrOutput);
    }

    @org.junit.Test
    public void testCase009() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean a = null;
        Boolean b = null;

        // Check DecisionAnd
        Boolean decisionAndOutput = new DecisionAnd().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionAndOutput);
        // Check DecisionOr
        Boolean decisionOrOutput = new DecisionOr().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decisionOrOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
