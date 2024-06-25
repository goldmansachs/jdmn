package com.gs.dmn.generated.other.decision_table_with_annotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@javax.annotation.Generated(value = {"junit.ftl", "decision-table-with-annotations.dmn"})
public class HandWrittenDecisionTableWithAnnotationsTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        com.gs.dmn.generated.other.decision_table_with_annotations.type.TA structA = new com.gs.dmn.generated.other.decision_table_with_annotations.type.TAImpl("A", number("5"));

        // Check 'priceGt10'
        checkValues(Boolean.FALSE, new PriceGt10().apply(structA, context_));
        List<String> actualAnnotations = context_.getAnnotations().stream().map(a -> a.toString()).collect(Collectors.toList());
        List<String> expectedAnnotations = Arrays.asList(
                "Annotation('priceGt10', 2, 'Price 5 is <= 0')",
                "Annotation('priceGt10', 2, 'Since this is a CDATA section I can use all sorts of reserved characters like > < \" and & or write things like <foo></bar> but my document is still well formed!')"
        );
        assertEquals(expectedAnnotations, actualAnnotations);
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        com.gs.dmn.generated.other.decision_table_with_annotations.type.TA structA = new com.gs.dmn.generated.other.decision_table_with_annotations.type.TAImpl("A", number("11"));

        // Check 'priceGt10'
        checkValues(Boolean.TRUE, new PriceGt10().apply(structA, context_));
        List<String> actualAnnotations = context_.getAnnotations().stream().map(a -> a.toString()).collect(Collectors.toList());
        List<String> expectedAnnotations = Arrays.asList(
                "Annotation('priceGt10', 1, 'Logging')",
                "Annotation('priceGt10', 1, 'Price  11  is >= 0')"
        );
        assertEquals(expectedAnnotations, actualAnnotations);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
