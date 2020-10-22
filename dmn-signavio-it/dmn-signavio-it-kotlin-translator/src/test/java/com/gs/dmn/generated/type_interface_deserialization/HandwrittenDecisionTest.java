package com.gs.dmn.generated.type_interface_deserialization;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.Assert;


public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final EsmaEquityOptionIndicator decision = new EsmaEquityOptionIndicator();

    @org.junit.Test
    public void testDeserializedDecisionInput() throws Exception {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));

        String esmaEquityOptionIndicator = decision.apply(marginEquityOptionTradableProfiles, annotationSet_);

        checkValues("Yes", esmaEquityOptionIndicator);
    }

    @org.junit.Test
    public void testSerializedDecisionInput() throws Exception {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();

        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));
        String serializedInput = com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writeValueAsString(marginEquityOptionTradableProfiles);

        String esmaEquityOptionIndicator = decision.apply(serializedInput, annotationSet_);

        checkValues("Yes", esmaEquityOptionIndicator);
    }


    @Override
    protected void applyDecision() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));

        decision.apply(marginEquityOptionTradableProfiles, annotationSet_);
    }

    private void checkValues(Object expected, Object actual) {
        Assert.assertEquals(expected == null ? null : expected.toString(), actual == null ? null : actual.toString());
    }

}
