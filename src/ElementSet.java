public interface ElementSet {
    String getName();
    int value();
    int cardinality();
    ElementSet union(ElementSet b);
}
