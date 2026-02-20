package com.gs.dmn.generated.tck.cl3_0020_vacation_days.coverage.report;

import com.gs.dmn.generated.tck.cl3_0020_vacation_days.listener.AbstractTraceListenerTest;
import com.gs.dmn.runtime.coverage.report.CoverageReport;
import com.gs.dmn.runtime.coverage.report.CoverageReportGenerator;
import com.gs.dmn.runtime.coverage.report.ModelCoverageReportGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelCoverageReportGeneratorTest extends AbstractTraceListenerTest {
    private final CoverageReportGenerator generator = new ModelCoverageReportGenerator();

    @Test
    public void testOneModel() {
        CoverageReport coverageReport = calculateCoverageReport("coverage-traces/0020_vacation_days/");
        List<String> expectedTable = Arrays.asList(
                "Namespace,Model,Elements,Missed Elements,Elements Coverage,Rules,Missed Rules,Rules Coverage",
                "https://www.drools.org/kie-dmn,0020-vacation-days,5,0,100.00%,6,0,100.00%"
        );
        assertEquals(expectedTable, coverageReport.toTable().toCsvLines());
    }

    @Test
    public void testSeveralModels() {
        CoverageReport coverageReport = calculateCoverageReport("coverage-traces/0001_no_name_conflicts_one_package");
        List<String> expectedTable = Arrays.asList(
                "Namespace,Model,Elements,Missed Elements,Elements Coverage,Rules,Missed Rules,Rules Coverage",
                "http://www.provider.com/definitions/model-c,Model C,1,0,100.00%,0,0,N/A",
                "http://www.provider.com/definitions/model-b1,Model B1,2,0,100.00%,0,0,N/A",
                "http://www.provider.com/definitions/model-b2,,0,0,N/A,0,0,N/A"
        );
        assertEquals(expectedTable, coverageReport.toTable().toCsvLines());
    }

    private CoverageReport calculateCoverageReport(String tracePath) {
        File traceFolder = new File(resource(tracePath));
        CoverageReport coverageReport = generator.generateCoverageReport(traceFolder);
        return coverageReport;
    }
}