
public class Main {
    public static void main(String[] args) {
        Instance inst = new Instance("Hyperedge", "instances/restaurant-reviews.hgf", 30);
        Streaming s = new Streaming(inst, 0.05);
        ElementSet answer = s.stream();
        System.out.println(answer.getName());
        System.out.println(answer.value());
        System.out.println(answer.cardinality());
        System.out.println("---------------------");
        Approx a = new Approx(inst);
        ElementSet answerApprox = a.run();
        System.out.println(answerApprox.getName());
        System.out.println(answerApprox.value());
        System.out.println(answerApprox.cardinality());
    }
}