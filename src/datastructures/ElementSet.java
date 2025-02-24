package datastructures;

/**
 * Interface for a set X of elements in the domain of a submodular function.
 */
public interface ElementSet {
    /**
     * @return name of all elements in X.
     */
    String getName();

    /**
     * @return f(X)
     */
    int value();

    /**
     * @return |X|
     */
    int cardinality();

    /**
     * This function can be derived with the help of value() and union(), but this method allows for more efficient implementations.
     * @param element the new element which is not in X.
     * @return f(X+element)-f(X).
     */
    int marginalContribution(Element element);

    /**
     * @param b the second element set.
     * Effect: X is set to X + b.
     */
    void union(ElementSet b);
}
