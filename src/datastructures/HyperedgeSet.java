package datastructures;

import java.util.Arrays;
import java.util.HashSet;

public class HyperedgeSet implements ElementSet{
    String name;
    int cardinality;
    HashSet<String> groundSet;
    public HyperedgeSet(String name, HashSet<String> groundSet, int cardinality){
        this.name = name;
        this.groundSet = groundSet;
        this.cardinality = cardinality;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int value() {
        return groundSet.size();
    }

    @Override
    public int cardinality() {
        return cardinality;
    }

    @Override
    public int marginalContribution(Element element) {
        int count = 0;
        for (String s : ((Hyperedge)element).groundSet) if (!groundSet.contains(s)) count++; // element only contributes if s was  not in the groundSet already
        return count;
    }

    public static ElementSet emptySet() {
        return new HyperedgeSet("", new HashSet<>(), 0);
    }

    @Override
    public void union(ElementSet b) {
        if (b instanceof HyperedgeSet) {
            groundSet.addAll(((HyperedgeSet)b).groundSet);
            name = name.concat("+").concat(b.getName()); // arbitrary naming convention
            cardinality += b.cardinality(); // number of hyperedges in the set is simple sum
        }
        else throw new ClassCastException("Union of two different implementations of datastructures.ElementSet.");
    }
    public static Hyperedge readHyperedge(String hyperedge, int i){
        if (hyperedge.startsWith("%")) return null; // comment line
        String[] parts = hyperedge.split(" ");
        HashSet<String> groundSet = new HashSet<>(Arrays.asList(parts));
        return new Hyperedge(String.valueOf(i), groundSet, 1);
    }
}
