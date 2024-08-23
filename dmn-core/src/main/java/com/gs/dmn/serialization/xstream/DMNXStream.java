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
package com.gs.dmn.serialization.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.basic.*;
import com.thoughtworks.xstream.converters.collections.*;
import com.thoughtworks.xstream.converters.extended.*;
import com.thoughtworks.xstream.converters.reflection.*;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.SelfStreamingInstanceChecker;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

public class DMNXStream extends XStream {
    public static DMNXStream from(HierarchicalStreamDriver driver, ClassLoaderReference classLoaderReference) {
        return new DMNXStream(new PureJavaReflectionProvider(), driver, classLoaderReference);
    }

    private DMNXStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoaderReference classLoaderReference) {
        super(reflectionProvider, driver, classLoaderReference, null);
    }

    @Override
    protected void setupConverters() {
        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);

        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
        registerConverter(new LongConverter(), PRIORITY_NORMAL);
        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
        registerConverter((Converter) new CharConverter(), PRIORITY_NORMAL);
        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
        registerConverter(new ByteConverter(), PRIORITY_NORMAL);
        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new StringBufferConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new LocaleConverter(), PRIORITY_NORMAL);
        registerConverter(new GregorianCalendarConverter(), PRIORITY_NORMAL);
        registerConverter(new BitSetConverter(), PRIORITY_NORMAL);
        registerConverter(new URIConverter(), PRIORITY_NORMAL);
        registerConverter(new URLConverter(), PRIORITY_NORMAL);
        registerConverter(new BigIntegerConverter(), PRIORITY_NORMAL);
        registerConverter(new BigDecimalConverter(), PRIORITY_NORMAL);

        registerConverter(new ArrayConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter(new CharArrayConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter(new MapConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter(new SingletonCollectionConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter(new SingletonMapConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter((Converter) new EncodedByteArrayConverter(), PRIORITY_NORMAL);

        registerConverter(new JavaClassConverter(getClassLoaderReference()), PRIORITY_NORMAL);
        registerConverter(new JavaMethodConverter(getClassLoaderReference()), PRIORITY_NORMAL);
        registerConverter(new JavaFieldConverter(getClassLoaderReference()), PRIORITY_NORMAL);

        registerConverter(new SelfStreamingInstanceChecker(getConverterLookup(), this), PRIORITY_NORMAL);

        registerConverter(new SerializableConverter(getMapper(), getReflectionProvider(), getClassLoaderReference()), PRIORITY_LOW);
        registerConverter(new ExternalizableConverter(getMapper(), getClassLoaderReference()), PRIORITY_LOW);

        registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
    }
}
