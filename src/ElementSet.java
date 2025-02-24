public interface ElementSet {
    String getName();
    int value();
    int cardinality();
    int marginalContribution(Element element);
    ElementSet createUnion(ElementSet b);
    void union(ElementSet b);
}
