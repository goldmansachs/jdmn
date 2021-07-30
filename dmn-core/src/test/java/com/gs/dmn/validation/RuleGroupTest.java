package com.gs.dmn.validation;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RuleGroupTest {
    @Test
    public void testIsEmpty() {
        assertTrue(new RuleGroup().isEmpty());
        assertFalse(new RuleGroup(Arrays.asList(1, 2)).isEmpty());
    }

    @Test
    public void testSerialize() {
        assertEquals("[]", new RuleGroup().serialize());
        // Indexes start from 1 - user friendly
        assertEquals("[2, 3]", new RuleGroup(Arrays.asList(1, 2)).serialize());
    }

    @Test
    public void testMinusNode() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(3));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(4));

        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).minus(3));
        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).minus(4));
    }

    @Test
    public void testMinusList() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(Arrays.asList(3)));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).minus(Arrays.asList(4)));

        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).minus(Arrays.asList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).minus(Arrays.asList(4)));
    }

    @Test
    public void union() {
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3)), new RuleGroup(Arrays.asList(1, 2, 3)).union(3));
        assertEquals(new RuleGroup(Arrays.asList(1, 2, 3, 4)), new RuleGroup(Arrays.asList(1, 2, 3)).union(4));

        assertEquals(new RuleGroup(Arrays.asList(3)), new RuleGroup(Arrays.asList()).union(3));
        assertEquals(new RuleGroup(Arrays.asList(4)), new RuleGroup(Arrays.asList()).union(4));
    }

    @Test
    public void intersect() {
        assertEquals(new RuleGroup(Arrays.asList(3)), new RuleGroup(Arrays.asList(1, 2, 3)).intersect(Arrays.asList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList(1, 2, 3)).intersect(Arrays.asList(4)));

        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).intersect(Arrays.asList(3)));
        assertEquals(new RuleGroup(), new RuleGroup(Arrays.asList()).intersect(Arrays.asList(4)));
    }
}