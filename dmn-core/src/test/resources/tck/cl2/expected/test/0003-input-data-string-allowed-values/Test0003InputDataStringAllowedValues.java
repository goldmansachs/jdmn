
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0003-input-data-string-allowed-values.dmn"})
public class Test0003InputDataStringAllowedValues extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String employmentStatus = "EMPLOYED";

        // Check EmploymentStatusStatement
        String employmentStatusStatementOutput = new EmploymentStatusStatement().apply(employmentStatus, annotationSet_, eventListener_, externalExecutor_);
        checkValues("You are EMPLOYED", employmentStatusStatementOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
