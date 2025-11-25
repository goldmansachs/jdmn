package com.gs.dmn.runtime.listener;

import com.gs.dmn.runtime.annotation.DRGElementKind;
import com.gs.dmn.runtime.annotation.ExpressionKind;
import com.gs.dmn.runtime.annotation.HitPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractListenerTest {
    protected EventListener listener;

    @Test
    public void testEvents() {
        DRGElement element = new DRGElement("namespace", "name", "label", DRGElementKind.DECISION, ExpressionKind.LITERAL_EXPRESSION, HitPolicy.UNIQUE, 5);
        Rule rule = new Rule(1, "annotation");
        Arguments arguments = new Arguments();

        listener.startDRGElement(element, arguments);
        listener.endDRGElement(element, arguments, null, 0);
        listener.startRule(element, rule);
        listener.matchRule(element, rule);
        listener.endRule(element, rule, null);
        listener.matchColumn(rule, 1, null);

        assertTrue(true);
    }

    @Test
    public void testEventsWithNull() {
        listener.startDRGElement(null, null);
        listener.endDRGElement(null, null, null, 0);
        listener.startRule(null, null);
        listener.matchRule(null, null);
        listener.endRule(null, null, null);
        listener.matchColumn(null, 0, null);

        assertTrue(true);
    }
}
