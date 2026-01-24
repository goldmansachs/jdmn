package com.gs.dmn.generated.other.context_api;

import com.gs.dmn.generated.other.context_api.type.Country;
import com.gs.dmn.generated.other.context_api.type.Person;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplyContextTest {
    @Test
    public void testApplyContext() {
        // Make context
        Context context = makeContext();

        // Convert to Decision inputs
        DInput_ input = toInput(context);

        // Evaluate decision
        D decision = new D();
        List<Country> countries = decision.applyPojo(input, ExecutionContextBuilder.executionContext().build());

        // Check result
        assertEquals(2, countries.size());
        assertEquals("USA", countries.get(0).getName());
        assertEquals("UK", countries.get(1).getName());
    }

    private Context makeContext() {
        Context decisionContext = new Context("decision")
                .add("date", LocalDate.now())
                .add("person", new Context("for person")
                        .add("name", "John Doe")
                        .add("addresses",
                                Arrays.asList(
                                    new Context("for address1")
                                            .add("street", "123 Main St")
                                            .add("no", 24)
                                            .add("country", new Context().add("name", "USA")),
                                    new Context("for address2")
                                            .add("street", "456 Elm St")
                                            .add("no", 36)
                                            .add("country", new Context().add("name", "UK"))
                                    ))
                        .add("aliases", Arrays.asList("JD", "Johnny")));
        return decisionContext;
    }

    private DInput_ toInput(Context context) {
        DInput_ input = new DInput_();
        input.setDate((LocalDate) context.get("date"));
        input.setPerson(Person.toPerson(context.get("person")));
        return input;
    }

}
