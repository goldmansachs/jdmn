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
package com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache;

import com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.proto.AdjudicationRequest;
import com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.proto.Monthly;
import com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.type.TApplicantData;
import com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.type.TApplicantDataImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConversionForComplexTypesTest {
    @Test
    public void testDefaultValues() {
        AdjudicationRequest request = AdjudicationRequest.newBuilder().build();
        assertNotNull(request.getApplicantData());
        assertNotNull(request.getBureauData());
        assertEquals("", request.getSupportingDocuments());
    }

    @Test
    public void testConvertMethodsWhenNull() {
        com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.proto.TApplicantData protoApplicantData = TApplicantData.toProto((TApplicantData) null);
        assertNotNull(null, protoApplicantData);
        assertEquals(0, protoApplicantData.getAge(), 0.0001);
    }

    @Test
    public void testConvertMethodsWhenMissingProperties() {
        TApplicantData applicantData = new TApplicantDataImpl();
        com.gs.dmn.generated.proto.cl3_0004_lending_proto_cache.proto.TApplicantData protoApplicantData = TApplicantData.toProto(applicantData);
        assertNotNull(protoApplicantData);
        assertEquals(0, protoApplicantData.getAge(), 0.001);
        assertEquals("", protoApplicantData.getEmploymentStatus());
        assertEquals(false, protoApplicantData.getExistingCustomer());
        Monthly protoMonthly = protoApplicantData.getMonthly();
        assertNotNull(protoMonthly);
        assertEquals(0, protoMonthly.getIncome(), 0.001);
    }
}
