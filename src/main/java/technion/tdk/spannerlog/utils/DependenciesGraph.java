package technion.tdk.spannerlog.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class DependenciesGraph {
    private Map<Integer, List<Integer>> adjacencyList;

    public DependenciesGraph(Map<Integer, List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    private List<Integer> outEdges(int i) {
        return adjacencyList.get(i);
    }

    private int nVertices() {
        return adjacencyList.size();
    }

    public List<Integer> getDependencies(int startIdx) {

        Boolean[] visited = new Boolean[this.nVertices()];
        Arrays.fill(visited, Boolean.FALSE);
        Stack<Integer> s = new Stack<>();
        s.push(startIdx);
        while (!s.isEmpty()) {
            int i = s.pop();
            if (!visited[i]) {
                visited[i] = true;
                for (int j : this.outEdges(i)) {
                    if (visited[j])
                        throw new CircularDependencyException();
                    s.push(j);
                }
            }
        }
        return IntStream.range(0, visited.length)
                .filter(i -> visited[i] && i != startIdx)
                .boxed()
                .collect(Collectors.toList());
    }

    private class CircularDependencyException extends RuntimeException {
    }
}

