import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Experiments.mainExperiment("instances");
        /*Instance inst = new Instance("Hyperedge", "instances/hyperedges-house-bills.txt", 50);
        Streaming s = new Streaming(inst, 0.05);
        ElementSet answer = s.run();
        System.out.println(answer.getName());
        System.out.println(answer.value());
        System.out.println(answer.cardinality());
        System.out.println(s.memory);
        System.out.println(s.time);
        System.out.println("---------------------");
        Approx a = new Approx(inst);
        ElementSet answerApprox = a.run();
        System.out.println(answerApprox.getName());
        System.out.println(answerApprox.value());
        System.out.println(answerApprox.cardinality());
        System.out.println(a.memory);
        System.out.println(a.time);*/
    }
}