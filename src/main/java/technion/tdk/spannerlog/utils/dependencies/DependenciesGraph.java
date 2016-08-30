package technion.tdk.spannerlog.utils.dependencies;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class DependenciesGraph<T> {
    private Map<T, Set<T>> adjacencyMap;

    private Set<T> outEdges(T t) {
        return adjacencyMap.get(t);
    }

    public DependenciesGraph(Map<T, Set<T>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
    }

    public List<T> getDependencies(T root) {

        Map<T, Boolean> visited = adjacencyMap.keySet()
                .stream()
                .collect(Collectors.toMap(t -> t, t -> Boolean.FALSE));

        Stack<T> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            T t = stack.pop();
            if (!visited.get(t)) {
                visited.put(t, Boolean.TRUE);
                for (T s : this.outEdges(t)) {
                    if (visited.get(s))
                        throw new CircularDependencyException();
                    stack.push(s);
                }
            }
        }

        return visited.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .filter(e -> !e.getKey().equals(root))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

