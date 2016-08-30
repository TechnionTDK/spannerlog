package technion.tdk.spannerlog.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

class CircularDependencyException extends RuntimeException {}

//public class DependenciesGraph {
//    private Map<Integer, List<Integer>> adjacencyList;
//
//    public DependenciesGraph(Map<Integer, List<Integer>> adjacencyList) {
//        this.adjacencyList = adjacencyList;
//    }
//
//    private List<Integer> outEdges(int i) {
//        return adjacencyList.get(i);
//    }
//
//    private int nVertices() {
//        return adjacencyList.size();
//    }
//
//    public List<Integer> getDependencies(int startIdx) {
//
//        Boolean[] visited = new Boolean[this.nVertices()];
//        Arrays.fill(visited, Boolean.FALSE);
//        Stack<Integer> s = new Stack<>();
//        s.push(startIdx);
//        while (!s.isEmpty()) {
//            int i = s.pop();
//            if (!visited[i]) {
//                visited[i] = true;
//                for (int j : this.outEdges(i)) {
//                    if (visited[j])
//                        throw new CircularDependencyException();
//                    s.push(j);
//                }
//            }
//        }
//        return IntStream.range(0, visited.length)
//                .filter(i -> visited[i] && i != startIdx)
//                .boxed()
//                .collect(Collectors.toList());
//    }
//
//    private class CircularDependencyException extends RuntimeException {
//    }
//}

