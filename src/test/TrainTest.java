package test;


import main.Train;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TrainTest {

    private Train graph;

    @Before
    public void initGraph() {
        String str = readInput("src/resource/input.txt");
        graph = new Train(str);
    }

    public String readInput(String fileName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("readFromFile file not found");
        } catch (IOException e) {
            System.out.println("readFromFile io exception," + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("reader close exception," + e.getMessage());
                }
            }
        }
        return sb.toString();
    }

    @Test
    public void one() {
        String path = "A-B-C";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(9, result);
    }

    @Test
    public void two() {
        String path = "A-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(5, result);
    }

    @Test
    public void three() {
        String path = "A-D-C";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(13, result);
    }

    @Test
    public void four() {
        String path = "A-E-B-C-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(22, result);
    }

    @Test
    public void five() {
        String path = "A-E-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(-1, result);
    }

    @Test
    public void six() {
        int result = graph.getPathCountByMaxStop("C", "C", 4);
        Assert.assertEquals(2, result);
    }

    @Test
    public void seven() {
        int result = graph.getPathCountByMaxStop("A", "C", 4);
        Assert.assertEquals(3, result);
    }
}
