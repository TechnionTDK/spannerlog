package technion.tdk.spannerlog.utils.graph;

import java.util.*;
import java.util.stream.Collectors;

public class DiGraph<T> {
    private Map<T, Set<T>> outEdges;
    private Map<T, Set<T>> inEdges;
    private Set<T> nodes;

    public DiGraph(Map<T, Set<T>> outEdges) {
        this.outEdges = outEdges;
    }

    private DiGraph() {
        outEdges = new HashMap<>();
        inEdges = new HashMap<>();
        nodes = new HashSet<>();
    }

    public static class Builder<T> {

        private DiGraph<T> diGraph = new DiGraph<>();

        public Builder addEdge(T u, T v) {
            Set<T> uOutEdges = diGraph.outEdges.getOrDefault(u, new HashSet<>());
            uOutEdges.add(v);
            diGraph.outEdges.put(u, uOutEdges);

            Set<T> vInEdges = diGraph.inEdges.getOrDefault(v, new HashSet<>());
            vInEdges.add(u);
            diGraph.inEdges.put(v, vInEdges);

            diGraph.nodes.add(u);
            diGraph.nodes.add(v);

            return this;
        }

        public Builder addNode(T u) {
            diGraph.nodes.add(u);
            return this;
        }

        public DiGraph<T> build() {
            return diGraph;
        }
    }

    /*
     * Returns a topological sort of the graph (if exists).
     *
     * Source: https://en.wikipedia.org/wiki/Topological_sorting
     */
    public List<T> sortTopologically() {

        // empty list that will contain the sorted elements
        List<T> sorting = new ArrayList<>();

        // set of all nodes with no incoming edges
        Set<T> roots = nodes.stream().filter(u -> !inEdges.containsKey(u)).collect(Collectors.toSet());

        while(!roots.isEmpty()){

            // add a root to the sorting.
            T u = roots.iterator().next();
            roots.remove(u);
            sorting.add(u);

            // For each v s.t. there exists an edge u -> v
            for (T v : outEdges.getOrDefault(u, Collections.emptySet())) {
                Set<T> vInEdges = inEdges.getOrDefault(v, Collections.emptySet());
                vInEdges.remove(u);
                if (vInEdges.isEmpty()) {
                    inEdges.remove(v);
                    if (!u.equals(v))
                        roots.add(v);
                }
            }
            outEdges.remove(u);
        }

        // check to see if all edges are removed
        if(!inEdges.isEmpty()){
            throw new CycleFoundInGraphException();
        }

        return sorting;
    }

    /*
     * Runs DFS on the graph and returns all reachable vertices from the given root.
     */
    public List<T> getReachables(T root) {

        Map<T, Boolean> visited = outEdges.keySet()
                .stream()
                .collect(Collectors.toMap(t -> t, t -> Boolean.FALSE));

        Stack<T> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            T t = stack.pop();
            if (!visited.get(t)) {
                visited.put(t, Boolean.TRUE);
                for (T s : outEdges.get(t)) {
//                    if (visited.get(s))
//                        throw new CycleFoundInGraphException();
                    stack.push(s);
                }
            }
        }

        visited.put(root, Boolean.FALSE); // We do not include the input node in the result list.
        return visited.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

