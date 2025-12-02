/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.generated.tck.cl3_0004_lending_singleton;

import com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TApplicantData;
import com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TBureauData;
import com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TRequestedProduct;

public abstract class AbstractTest0004Lending extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public void execute() {
        long count = 1000000000L;
        for (int i = 0; i< count; i++) {
            applyDecision();
        }
    }

    private void applyDecision() {
        long start = System.currentTimeMillis();

        // Initialize input data
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TApplicantData applicantData = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TApplicantDataImpl(number("35"), "EMPLOYED", true, "M", new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TRequestedProduct requestedProduct = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TBureauData bureauData = new com.gs.dmn.generated.tck.cl3_0004_lending_singleton.type.TBureauDataImpl(false, number("649"));

        // Check Strategy
        checkValues("BUREAU", invokeStrategy(makeStrategy(), applicantData, requestedProduct));
        // Check Routing
        checkValues("ACCEPT", invokeRouting(makeRouting(), applicantData, bureauData, requestedProduct));

        long end = System.currentTimeMillis();
        System.out.printf("Took %d ms%n", end - start);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    private Object invokeStrategy(Strategy strategy, TApplicantData applicantData, TRequestedProduct requestedProduct) {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        return strategy.apply(applicantData, requestedProduct, context_);
    }

    private Object invokeRouting(Routing routing, TApplicantData applicantData, TBureauData bureauData, TRequestedProduct requestedProduct) {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        return routing.apply(applicantData, bureauData, requestedProduct, context_);
    }

    protected abstract Routing makeRouting();

    protected abstract Strategy makeStrategy();
}
