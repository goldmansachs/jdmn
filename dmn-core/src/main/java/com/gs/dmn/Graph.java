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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Graph<T> {
    private final Set<T> nodes = new LinkedHashSet<>();
    private final Map<T, Set<T>> parentToChildren = new LinkedHashMap<>();

    public void addNode(T node) {
        if (node != null) {
            this.nodes.add(node);
        }
    }

    public void addNodes(List<T> nodes) {
        if (nodes == null) {
            return;
        }

        for (T node : nodes) {
            addNode(node);
        }
    }

    public void addEdge(T parent, T child) {
        if (parent == null || child == null) {
            return;
        }

        // Add nodes also if not present
        this.addNode(parent);
        this.addNode(child);

        // Add parent-child pair
        this.parentToChildren.putIfAbsent(parent, new LinkedHashSet<>());
        this.parentToChildren.get(parent).add(child);
    }

    public void addEdges(T parent, List<T> children) {
        if (parent == null || children == null) {
            return;
        }

        for (T child : children) {
            addEdge(parent, child);
        }
    }

    public List<T> getNodes() {
        return List.copyOf(nodes);
    }

    public List<Pair<T, List<T>>> getEdges() {
        List<Pair<T, List<T>>> edges = new ArrayList<>();
        for (Map.Entry<T, Set<T>> entry : this.parentToChildren.entrySet()) {
            edges.add(new Pair<>(entry.getKey(), List.copyOf(entry.getValue())));
        }
        return edges;
    }

    public List<T> getParents(T node) {
        List<T> parents = new ArrayList<>();
        for (Map.Entry<T, Set<T>> entry : this.parentToChildren.entrySet()) {
            if (entry.getValue().contains(node)) {
                parents.add(entry.getKey());
            }
        }
        return parents;
    }

    public List<T> getChildren(T node) {
        return new ArrayList<>(this.parentToChildren.getOrDefault(node, Set.of()));
    }

    public List<T> getDescendants(T node) {
        Set<T> descendants = new LinkedHashSet<>();
        Set<T> visited = new LinkedHashSet<>();
        collectDescendantsDFS(node, visited, descendants);
        return new ArrayList<>(descendants);
    }

    private void collectDescendantsDFS(T node, Set<T> visited, Set<T> descendants) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);

        for (T child: getChildren(node)) {
            descendants.add(child);
            collectDescendantsDFS(child, visited, descendants);
        }
    }

    public List<T> findRootNodes() {
        List<T> rootNodes = new ArrayList<>();
        for (T node : nodes) {
            if (getParents(node).isEmpty()) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    public List<List<T>> findCycles() {
        List<List<T>> cycles = new ArrayList<>();
        Set<T> visited = new LinkedHashSet<>();
        for (T node : getNodes()) {
            collectCyclesDFS(node, visited, new Stack<>(), cycles);
        }
        return cycles;
    }

    private void collectCyclesDFS(T node, Set<T> visited, Stack<T> stack, List<List<T>> cycles) {
        // Check for cycles that include the node
        if (stack.contains(node)) {
            // Cycle detected
            List<T> cycle = new ArrayList<>(stack.subList(stack.indexOf(node), stack.size()));
            cycle.add(node);
            cycles.add(cycle);
        }

        if (visited.contains(node)) {
            return;
        }
        visited.add(node);

        stack.push(node);
        for (T child: getChildren(node)) {
            collectCyclesDFS(child, visited, stack, cycles);
        }
        stack.pop();
    }

    public void printNodes(Writer writer, Function<T, String> nodeInfo) throws IOException {
        for (T node : nodes) {
            writer.write(nodeInfo.apply(node) + "\n");
        }
    }

    public void printBF(T rootNode, StringWriter writer, Function<T, String> nodeInfo) {
        printBF(rootNode, writer, nodeInfo, x -> true);
    }

    public void printBF(T rootNode, StringWriter writer, Function<T, String> nodeInfo, Predicate<T> filter) {
        Set<T> visited = new LinkedHashSet<>();
        if (filter.test(rootNode)) {
            printBF(rootNode, writer, nodeInfo, filter, 0, visited);
        }
    }

    private void printBF(T node, StringWriter writer, Function<T, String> nodeInfo, Predicate<T> filter, int level, Set<T> visited) {
        String indent = "\t".repeat(level);
        writer.write(indent + nodeInfo.apply(node) + "\n");
        if (!visited.contains(node)) {
            visited.add(node);
            for (T child : getChildren(node)) {
                if (filter.test(child)) {
                    printBF(child, writer, nodeInfo, filter, level + 1, visited);
                }
            }
        }
    }
}
