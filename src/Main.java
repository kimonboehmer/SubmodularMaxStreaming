
public class Main {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        Streaming s = new Streaming("Hyperedge", "instances/restaurant-reviews.hgf", 0.05, 30);
        ElementSet answer = s.stream();
        System.out.println(answer.getName());
        System.out.println(answer.value());
        System.out.println(answer.cardinality());
    }
}