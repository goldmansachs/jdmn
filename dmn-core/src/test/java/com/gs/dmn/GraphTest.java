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
package com.gs.dmn;

import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest {
    @Test
    void testAddNode() {
        Graph<Integer> graph = new Graph<>();
        graph.addNode(1);
        assertEquals(List.of(1), graph.getNodes());
    }

    @Test
    void testAddNodes() {
        Graph<Integer> graph = new Graph<>();
        graph.addNodes(List.of(1, 2));
        assertEquals(List.of(1, 2), graph.getNodes());
    }

    @Test
    void testAddEdge() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        assertEquals(List.of(1, 2), graph.getNodes());
    }

    @Test
    void testAddEdges() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2, 3));
        assertEquals(List.of(1, 2, 3), graph.getNodes());
    }

    @Test
    void testGetEdges() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2, 3));
        assertEquals(List.of(new Pair<>(1, List.of(2, 3))), graph.getEdges());
    }

    @Test
    void testGetParents() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2, 3));
        assertEquals(List.of(), graph.getParents(1));
        assertEquals(List.of(1), graph.getParents(2));
        assertEquals(List.of(1), graph.getParents(3));
    }

    @Test
    void testGetChildren() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2, 3));
        assertEquals(List.of(2, 3), graph.getChildren(1));
        assertEquals(List.of(), graph.getChildren(2));
        assertEquals(List.of(), graph.getChildren(3));
    }

    @Test
    void testGetDescendants() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2, 3));
        graph.addEdges(2, List.of(4, 5));
        graph.addEdges(3, List.of(6, 7));
        // Order might be different
        assertEquals(Set.of(2, 3, 4, 5, 6, 7), Set.copyOf(graph.getDescendants(1)));
    }

    @Test
    void testFindRootNodes() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdges(1, List.of(2));
        graph.addEdges(2, List.of(4, 5));
        graph.addEdges(3, List.of(6, 7));
        // Order might be different
        assertEquals(List.of(1, 3), graph.findRootNodes());
    }

    @Test
    void testFindCyclesWhenNoCycles() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        assertEquals(List.of(), graph.findCycles());
    }

    @Test
    void testFindCyclesWhenOneCycle() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        assertEquals(List.of(List.of(1, 2, 3, 1)), graph.findCycles());
    }

    @Test
    void testFindCyclesWhenMultipleCycle() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Cycle 1-2-3-1
        graph.addEdge(3, 4);
        graph.addEdge(4, 2); // Cycle 2-3-4-2

        assertEquals(List.of(List.of(1, 2, 3, 1), List.of(2, 3, 4, 2)), graph.findCycles());
    }

    @Test
    void testFindCyclesWhenSelfLoop() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 1);

        assertEquals(List.of(List.of(1, 1)), graph.findCycles());
    }

    @Test
    void testPrintNodes() throws IOException {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Cycle 1-2-3-1
        graph.addEdge(3, 4);
        graph.addEdge(4, 2); // Cycle 2-3-4-2

        StringWriter writer = new StringWriter();
        graph.printNodes(writer, this::nodeInfo);
        String expectedNodes = "1\n2\n3\n4\n";
        assertEquals(expectedNodes, writer.toString());
    }

    @Test
    void testPrintTree() throws IOException {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Cycle 1-2-3-1
        graph.addEdge(3, 4);
        graph.addEdge(4, 2); // Cycle 2-3-4-2

        StringWriter writer = new StringWriter();
        graph.printBF(1, writer, this::nodeInfo);
        String expectedNodes =
                """
                        1
                        \t2
                        \t\t3
                        \t\t\t1
                        \t\t\t4
                        \t\t\t\t2
                        """;
        assertEquals(expectedNodes, writer.toString());
    }

    private String nodeInfo(Integer node) {
        return "" + node;
    }
}