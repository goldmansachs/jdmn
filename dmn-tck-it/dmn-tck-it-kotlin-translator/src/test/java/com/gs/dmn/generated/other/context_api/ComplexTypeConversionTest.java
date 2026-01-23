package com.gs.dmn.generated.other.context_api;

import com.gs.dmn.generated.other.context_api.type.Person;
import com.gs.dmn.runtime.Context;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexTypeConversionTest {
    @Test
    public void testToPerson() {
        // Make person context
        Context personContext = new Context();
        personContext.add("name", "John Doe");
        List<Context> addressesContexts = Arrays.asList(
                new Context()
                        .add("street", "123 Main St")
                        .add("no", 24)
                        .add("country", new Context().add("name", "USA")),
                new Context()
                        .add("street", "456 Elm St")
                        .add("no", 36)
                        .add("country", new Context().add("name", "UK"))
        );
        personContext.add("addresses", addressesContexts);
        personContext.add("aliases", Arrays.asList("JD", "Johnny"));

        // Convert to Person
        Person person = Person.toPerson(personContext);

        // Check properties
        assertEquals("John Doe", person.getName());

        assertEquals(2, person.getAddresses().size());
        assertEquals("123 Main St", person.getAddresses().get(0).getStreet());
        assertEquals(24, person.getAddresses().get(0).getNo());
        assertEquals("USA", person.getAddresses().get(0).getCountry().getName());
        assertEquals("456 Elm St", person.getAddresses().get(1).getStreet());
        assertEquals(36, person.getAddresses().get(1).getNo());
        assertEquals("UK", person.getAddresses().get(1).getCountry().getName());

        assertEquals(2, person.getAliases().size());
        assertEquals("JD", person.getAliases().get(0));
        assertEquals("Johnny", person.getAliases().get(1));
    }
}
