package main.model;

public class Vertex {
    /**
     * Target vertex
     * */
    public String v;
    /**
     * Distance between the starting vertex and the target vertex
     * */
    public int dist;

    public Vertex(String v, int dist) {
        this.v = v;
        this.dist = dist;
    }
}