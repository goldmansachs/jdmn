package com.gs.dmn.generated.tck.cl3_0020_vacation_days.coverage.report;

import com.gs.dmn.generated.tck.cl3_0020_vacation_days.listener.AbstractTraceListenerTest;
import com.gs.dmn.runtime.coverage.report.CoverageReport;
import com.gs.dmn.runtime.coverage.report.CoverageReportGenerator;
import com.gs.dmn.runtime.coverage.report.ElementsCoverageReportGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementCoverageReportGeneratorTest extends AbstractTraceListenerTest {
    private final CoverageReportGenerator generator = new ElementsCoverageReportGenerator();

    @Test
    public void testOneModel() {
        CoverageReport coverageReport = calculateCoverageReport("coverage-traces/0020_vacation_days/");
        List<String> expectedTable = Arrays.asList(
                "Namespace,Element,Rules,Missed Rules,Rules Coverage",
                "https://www.drools.org/kie-dmn,Base Vacation Days,0,0,100.00%",
                "https://www.drools.org/kie-dmn,Extra days case 1,2,0,100.00%",
                "https://www.drools.org/kie-dmn,Extra days case 2,2,0,100.00%",
                "https://www.drools.org/kie-dmn,Extra days case 3,2,0,100.00%",
                "https://www.drools.org/kie-dmn,Total Vacation Days,0,0,100.00%"
        );
        assertEquals(expectedTable, coverageReport.toTable().toCsvLines());
    }

    @Test
    public void testSeveralModels() {
        CoverageReport coverageReport = calculateCoverageReport("coverage-traces/0001_no_name_conflicts_one_package");
        List<String> expectedTable = Arrays.asList(
                "Namespace,Element,Rules,Missed Rules,Rules Coverage",
                "http://www.provider.com/definitions/model-c,modelCDecisionBasedOnBs,0,0,100.00%",
                "http://www.provider.com/definitions/model-b1,evaluatingB1SayHello,0,0,100.00%",
                "http://www.provider.com/definitions/model-b1,greetThePerson,0,0,100.00%",
                "http://www.provider.com/definitions/model-b2,evaluatingB2SayHello,0,0,100.00%"
        );
        assertEquals(expectedTable, coverageReport.toTable().toCsvLines());
    }

    private CoverageReport calculateCoverageReport(String tracePath) {
        File traceFolder = new File(resource(tracePath));
        return generator.generateCoverageReport(traceFolder);
    }
}