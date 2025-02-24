package datastructures;

import java.util.HashSet;

public class Hyperedge extends HyperedgeSet implements Element{
    public Hyperedge(String name, HashSet<String> groundSet, int cardinality) {
        super(name, groundSet, cardinality);
        this.cardinality = 1;
    }
}
