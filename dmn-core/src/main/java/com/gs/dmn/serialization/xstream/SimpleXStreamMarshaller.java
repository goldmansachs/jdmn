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

import java.io.*;
import java.net.URL;

public interface SimpleXStreamMarshaller {
    default Object unmarshal(String input) {
        return unmarshal(new StringReader(input));
    }

    default Object unmarshal(File input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            logError("Error unmarshalling {} from file '{}'.", artifactName(), input.getAbsolutePath(), e);
        }
        return null;
    }

    default Object unmarshal(URL input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            logError("Error unmarshalling {} from url '{}'.", artifactName(), input, e);
        }
        return null;
    }

    
    default Object unmarshal(InputStream input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            logError("Error unmarshalling {} from input stream.", artifactName(), e);
        }
        return null;
    }


    default Object unmarshal(Reader input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            logError("Error unmarshalling {} from reader.", artifactName(), e);
        }
        return null;
    }

    String marshal(Object o);

    default void marshal(Object o, File output) {
        try (FileWriter fileWriter = new FileWriter(output)) {
            marshal(o, fileWriter);
        } catch (IOException e) {
            logError("Error marshalling {} to file '{}'.", artifactName(), output.getAbsolutePath(), e);
        }
    }

    default void marshal(Object o, OutputStream output) {
        try (OutputStreamWriter streamWriter = new OutputStreamWriter(output)) {
            marshal(o, streamWriter);
        } catch (Exception e) {
            logError("Error marshalling {} to output stream.", artifactName(), e);
        }
    }

    default void marshal(Object o, Writer output) {
        try {
            output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            output.write(marshal(o));
        } catch (Exception e) {
            logError("Error marshalling {} to writer.", artifactName(), e);
        }
    }

    XStream newXStream();

    void logError(String message, Object argument1, Exception exception);

    void logError(String message, Object argument1, Object argument2, Exception exception);

    String artifactName();
}
