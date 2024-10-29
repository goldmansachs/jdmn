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
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;

//
// Allen's algebra https://en.wikipedia.org/wiki/Allen%27s_interval_algebra
//
// CQL https://cql.hl7.org/09-b-cqlreference.html#interval-operators-3
//
public class MixedRangeLib extends DefaultRangeLib {
    @Override
    protected StandardFEELLib<?, ?, ?, ?, ?> getFeelLib() {
        return MixedJavaTimeFEELLib.INSTANCE;
    }
}
