import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Experiments {
    private static File[] listFiles(String directory){
        return new File(directory).listFiles();
    }

    /**
     * computes the data needed for the experiments.
     * @param directory the directory where all instances are stored.
     * @throws IOException in case there is a read/write error.
     */
    public static void mainExperiment(String directory) throws IOException {
        final String FUNCTION_TYPE = "Hyperedge"; // structure type of the input
        final String OUTPUT_FILE = "results.csv"; // file to which the results will be written
        final double[] EPSILON_VALUES = new double[]{1,0.5,0.1,0.05,0.01,0.005}; // all considered epsilon-values for the streaming algorithm
        final int[] K_VALUES = new int[]{10,20,50,100,200}; // all considered values for k
        StringBuilder sb = new StringBuilder();
        for (File file : listFiles(directory)) for (int k : K_VALUES){
            Instance instance = new Instance(FUNCTION_TYPE, file.getCanonicalPath(), k);
            System.out.printf("Processing instance %s with k=%d.\n", file.getName(), k);
            LinkedList<int[]> data = new LinkedList<>();
            Approx a = new Approx(instance);
            ElementSet ea = a.run();
            data.add(new int[]{ea.value(),ea.cardinality(),a.time,a.memory});
            for (double eps : EPSILON_VALUES) {
                Streaming s = new Streaming(instance, eps);
                ElementSet es = s.run();
                data.add(new int[]{es.value(),es.cardinality(),s.time,s.memory});
            }
            for (int i = 0; i < 4; i++) {
                for (int[] oneData : data){
                    sb.append(oneData[i]).append(";");
                }
            }
            sb.append("\n");
        }
        Files.writeString(Paths.get(OUTPUT_FILE), sb.toString());
    }
}
