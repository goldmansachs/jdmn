package com.gs.dmn.generated.tck.cl3_0004_lending_singleton;

public abstract class AbstractTest0004Lending extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public void execute() {
        long count = 1000000000L;
        for (int i = 0; i< count; i++) {
            applyDecision();
        }
    }

    private void applyDecision() {
        long start = System.currentTimeMillis();

        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TApplicantData applicantData = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TApplicantDataImpl(number("35"), "EMPLOYED", true, "M", new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TRequestedProduct requestedProduct = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TBureauData bureauData = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TBureauDataImpl(false, number("649"));
        String supportingDocuments = "YES";

        // Check Strategy
        checkValues("BUREAU", makeStrategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check Routing
        checkValues("ACCEPT", makeRouting().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));

        long end = System.currentTimeMillis();
        System.out.printf("Took %d ms%n", end - start);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    protected abstract Routing makeRouting();

    protected abstract Strategy makeStrategy();
}
