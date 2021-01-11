package test;


import main.Train;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrainTest {

    private Train graph;


    @Before
    public void initGraph() {
        String str = readInput("src/main/resource/input.txt");
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
    @Order(1)
    public void one() {
        String path = "A-B-C";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(9, result);
        printer(1, result);
    }

    @Test
    @Order(2)
    public void two() {
        String path = "A-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(5, result);
        printer(2, result);
    }

    @Test
    @Order(3)
    public void three() {
        String path = "A-D-C";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(13, result);
        printer(3, result);
    }

    @Test
    @Order(4)
    public void four() {
        String path = "A-E-B-C-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(22, result);
        printer(4, result);
    }

    @Test
    @Order(5)
    public void five() {
        String path = "A-E-D";
        int result = graph.getPathDistance(path.split("-"));
        Assert.assertEquals(-1, result);
        printer(5, result);
    }

    @Test
    @Order(6)
    public void six() {
        int result = graph.getPathCountByMaxStop("C", "C", 4);
        Assert.assertEquals(2, result);
        printer(6, result);
    }

    @Test
    @Order(7)
    public void seven() {
        int result = graph.getPathCountByMaxStop("A", "C", 4);
        Assert.assertEquals(3, result);
        printer(7, result);
    }

    @Test
    @Order(8)
    public void eight() {
        int result = graph.findShortestDist("A", "C");
        Assert.assertEquals(9, result);
        printer(8, result);
    }

    @Test
    @Order(9)
    public void nine() {
        int result = graph.findShortestDist("B", "B");
        Assert.assertEquals(9, result);
        printer(9, result);
    }

    @Test
    @Order(10)
    public void ten() {
        Train graph = new Train();
        int result = graph.routeCount(2, 2, 30, 0, true);
        Assert.assertEquals(7, result);
        printer(10, result);

    }

    /**
     * Prints answer
     *
     * @param result -> Test result
     */
    private void printer(int testNumber, int result) {
        if (result == -1) {
            System.out.println("Output #" + testNumber + ": NO SUCH ROUTE");
        } else {
            System.out.println("Output #" + testNumber + ": " + result);
        }
    }
}
