public interface ElementSet {
    String getName();
    int value();
    int cardinality();
    ElementSet createUnion(ElementSet b);
    void union(ElementSet b);
}
