package main;

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
