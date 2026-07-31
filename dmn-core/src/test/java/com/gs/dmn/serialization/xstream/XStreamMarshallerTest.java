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

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.SyntaxErrorException;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class XStreamMarshallerTest {
    private final XStreamMarshaller marshaller = new XStreamMarshaller();

    private final String dmnWithExternalEntities =
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE definitions [
              <!ENTITY xxe SYSTEM "%s">
            ]>
            
            <definitions
                xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/"
                id="definitions"
                name="test"
                namespace="test">
            
                <description>&xxe;</description>
            
            </definitions>
            """;

    private final String dmnWithExternalDTD =
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE definitions SYSTEM "%s">
            <definitions
                xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/"
                id="definitions"
                name="test"
                namespace="test">
            </definitions>
            """;

    private final String dtd = "<!ELEMENT definitions ANY>";

    @Test
    void testLocalExternalEntitiesResolution() throws Exception {
        Path secret = Files.createTempFile("xxe", ".txt");
        Files.writeString(secret, "VERY_SECRET_12345");

        String maliciousDmn = dmnWithExternalEntities.formatted(secret.toUri());

        // Check XSD validation
        SyntaxErrorException exception = assertThrows(SyntaxErrorException.class, () -> {
            marshaller.unmarshal(maliciousDmn, true);
        });
        assertTrue(exception.getMessage().contains("Validation failed due to a critical error: External Entity: Failed to read external document"));

        // Check no XSD validation
        TDefinitions def = marshaller.unmarshal(maliciousDmn, false);
        assertNull(def);
    }

    @Test
    void testLocalExternalDTD() throws Exception {
        Path secret = Files.createTempFile("candidate", ".dtd");
        Files.writeString(secret, dtd);

        String maliciousDmn = dmnWithExternalDTD.formatted(secret.toUri());

        // Check XSD validation
        SyntaxErrorException exception = assertThrows(SyntaxErrorException.class, () -> {
            marshaller.unmarshal(maliciousDmn, true);
        });
        assertTrue(exception.getMessage().contains("Validation failed due to a critical error: External DTD: Failed to read external DTD"));
        assertTrue(exception.getMessage().contains("because 'file' access is not allowed due to restriction set by the accessExternalDTD property."));

        // Check no XSD validation
        TDefinitions def = marshaller.unmarshal(maliciousDmn, false);
        assertEquals("test", def.getName());
    }

    @Test
    void testRemoteExternalDTD() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        try {
            // Start and mock server
            AtomicInteger requestCount = new AtomicInteger();
            server.createContext("/evil", exchange -> {
                requestCount.incrementAndGet();

                byte[] body = dtd.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.close();
            });
            server.start();

            // Create DMN text
            int port = server.getAddress().getPort();
            // use SYSTEM "http://127.0.0.1:" + port + "/evil"
            String url = String.format("http://127.0.0.1:%d/evil", port);
            String maliciousDmn = dmnWithExternalDTD.formatted(url);

            // Check XSD validation
            SyntaxErrorException exception = assertThrows(SyntaxErrorException.class, () -> {
                marshaller.unmarshal(maliciousDmn, true);
            });
            assertTrue(exception.getMessage().contains("Validation failed due to a critical error: External DTD: Failed to read external DTD"));
            assertTrue(exception.getMessage().contains("because 'http' access is not allowed due to restriction set by the accessExternalDTD property."));

            // Check no XSD validation
            TDefinitions def = marshaller.unmarshal(maliciousDmn, false);
            assertEquals("test", def.getName());

            // Check interactions with the server
            assertEquals(0, requestCount.get(), "Parser attempted to resolve an external entity");

        } finally {
            server.stop(0);
        }
    }
}
