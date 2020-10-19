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
package com.gs.dmn.feel.lib;

import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDurationType;

public class DoubleMixedJavaTimeFEELLib extends BaseMixedJavaTimeFEELLib<Double> {
    public static DoubleMixedJavaTimeFEELLib INSTANCE = new DoubleMixedJavaTimeFEELLib();

    public DoubleMixedJavaTimeFEELLib() {
        super(new DoubleNumericType(LOGGER),
                new DoubleDurationType(LOGGER, DATA_TYPE_FACTORY),
                new DoubleNumericLib()
        );
    }

    @Override
    protected Double valueOf(long number) {
        return Double.valueOf(number);
    }

    @Override
    protected int intValue(Double number) {
        return number.intValue();
    }
}
