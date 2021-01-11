package main;

import main.model.Vertex;

import java.util.*;

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
    public int getPathDistance(String[] vertexes) {
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

    /**
     * Finds the number of paths available that do not exceed the maximum number of stops in stations
     * @param startVertex -> Origin vertex
     * @param destinationVertex -> End vertex
     * @param maxStop -> Maximum quantity of stops in trip
     * @return -> Path count
     */
    public int getPathCountByMaxStop(String startVertex, String destinationVertex , int maxStop){
        List<Object[]> paths = getPaths(startVertex , destinationVertex);
        int count = 0;
        if (paths.size() <= 0){
            return count;
        }

        for(Object[] path : paths){
            if (path.length <= maxStop){
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the shortest distance between two vertices by Dijkstra algorithm
     * @param startVertex -> Origin vertex
     * @param destinationVertex -> End vertex
     * @return
     * -> Distance between the two vertices given
     */
    public int findShortestDist(String startVertex, String destinationVertex) {
        Map<String, Vertex> parentMap = new HashMap<>(this.vertices);//store the vertex and distance closest to each vertex
        for (String vertex : vertexes) {
            parentMap.put(vertex, new Vertex(startVertex, Integer.MAX_VALUE));
        }
        parentMap.put(startVertex, null);
        Queue<Vertex> priorityQueue = new PriorityQueue<>((Comparator) (o1, o2) -> {
            Vertex v1 = (Vertex) o1;
            Vertex v2 = (Vertex) o2;
            return v1.dist - v2.dist;
        });
        priorityQueue.add(new Vertex(startVertex, 0));
        Set<String> visited = new HashSet<>(this.vertices);
        while (!priorityQueue.isEmpty()) {
            Vertex minVertex = priorityQueue.poll();
            if (!visited.add(minVertex.v)) {
                continue;
            }
            Map<String, Integer> edgeMap = adjacencyTable.get(minVertex.v);
            for (Map.Entry<String, Integer> entry : edgeMap.entrySet()) {
                int dist = minVertex.dist + entry.getValue();
                if (null != parentMap.get(entry.getKey()) &&
                        dist < parentMap.get(entry.getKey()).dist) {//The distance from the starting vertex is smaller than the previous one. The previous vertex of the updated shortest path is the vertex just dequeued from the priority queue.
                    parentMap.get(entry.getKey()).dist = dist;
                    parentMap.get(entry.getKey()).v = minVertex.v;
                }

                priorityQueue.add(new Vertex(entry.getKey(), dist));
            }
        }
        if (!startVertex.equals(destinationVertex)){
            return parentMap.get(destinationVertex).dist;
        }else{
            return calculateDist(startVertex , parentMap);
        }
    }

    /**
     * Calculates the same shortest distance as the startVertex and end vertices
     * Decomposes the problem into the distance between the starting vertex and the vertex closest to it,
     * plus the:
     * nearest vertex being the starting vertex of the starting vertex,
     * the shortest distance from the starting vertex to the ending vertex
     * @param startVertex -> Origin vertex
     * @param parentMap -> The vertex and distance closest to each vertex
     * @return -> Distance
     */
    private int calculateDist(String startVertex, Map<String, Vertex> parentMap) {
        int dist = Integer.MAX_VALUE;
        for (Map.Entry<String, Vertex> entry : parentMap.entrySet()) {
            if (entry.getValue() != null
                    && entry.getValue().v.equals(startVertex)
                    && entry.getValue().dist < dist){
                String next = entry.getKey();//First find the vertex closest to the target vertex
                int curDist = entry.getValue().dist + findShortestDist(next , startVertex);
                if (curDist < dist){
                    dist = curDist;
                }
            }
        }
        return dist;
    }

    /**
     * Finds all path between two vertices
     * @param startVertex -> Origin vertex
     * @param destinationVertex -> End vertex
     * @return -> All paths for both vertices
     */
    private List<Object[]> getPaths(String startVertex, String destinationVertex){
        Stack<String> stack = new Stack<>();
        List<Object[]> paths = new LinkedList<>();
        Set<String> visited = new HashSet<>(this.vertices);
        deepFirstSearch( startVertex , destinationVertex , null, stack , paths , visited);
        return paths;
    }

    /**
     * Searches for traversal maps by depth first
     * @param index -> start index
     * @param destinationVertex -> End vertex
     * @param prev -> Last index
     * @param stack -> Stack for storage
     * @param paths List of all paths
     * @param visited -> All the sets already visited
     */
    private void deepFirstSearch(String index,String destinationVertex ,String prev , Stack<String> stack, List<Object[]> paths,Set<String> visited) {
        stack.push(index);
        if (index.equals(destinationVertex) && prev != null){
            paths.add(stack.toArray());
            stack.pop();
        }else{
            Map<String, Integer> edgeMap = adjacencyTable.get(index);
            if (null != edgeMap && edgeMap.size() > 0) {
                for (Map.Entry<String, Integer> entry : edgeMap.entrySet()) {
                    if (!stack.contains(entry.getKey()) || !visited.contains(entry.getKey())){
                        deepFirstSearch(entry.getKey(), destinationVertex , index , stack , paths , visited);
                    }
                }
                visited.add(stack.pop());
            }
        }
    }










}
