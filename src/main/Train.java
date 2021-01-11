package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Train {
    /**
     * Stores the relationship of each vertex
     * */
    private final Map<String, Map<String, Integer>> adjacencyTable;
    /**
     * Number of vertices
     * */
    private int vertices;
    /**
     * All vertex
     * */
    private final Set<String> vertexes;



    /**
     * Initializes the relationship between vertices
     * @param graph -> graph input
     */
    public Train(String graph) {
        vertexes = new HashSet<>();
        adjacencyTable = new HashMap<>();
        if (null == graph || graph.length() <=0 ){
            return;
        }
        String[] vertexLengths = graph.split(",");
        for (String vertexLength : vertexLengths) {
            if (vertexLength.length() != 3) {
                continue;
            }
            String s = String.valueOf(vertexLength.charAt(0));
            String t = String.valueOf(vertexLength.charAt(1));
            int w = vertexLength.charAt(2) - '0';
            Map<String, Integer> sEdgeMap = adjacencyTable.computeIfAbsent(s, k -> new HashMap<>());
            sEdgeMap.put(t, w);
            if (vertexes.add(s)) {
                this.vertices++;
            }
            if (vertexes.add(t)) {
                this.vertices++;
            }
        }
    }

    /**
     * Calculates the distance of the path which is composed of multiple vertices
     * @param vertexes -> all vertex collections
     * @return
     * -> if there is no path return -1
     * -> else distance of the path
     */
    public int findDistOfPath(String[] vertexes) {
        if (null == vertexes || vertexes.length <= 0) {
            return -1;
        }
        String firstVertex = vertexes[0];
        int dist = 0;
        for (int i = 1; i < vertexes.length; i++) {
            String secondVertex = vertexes[i];
            Map<String, Integer> edgeMap = adjacencyTable.get(firstVertex);
            if (null != edgeMap && edgeMap.containsKey(secondVertex)) {
                dist += edgeMap.get(secondVertex);
            } else {
                return -1;
            }
            firstVertex = secondVertex;
        }
        return dist;
    }










}
