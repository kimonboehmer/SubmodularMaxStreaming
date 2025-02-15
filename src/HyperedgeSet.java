import java.util.HashSet;

public class HyperedgeSet implements ElementSet{
    String name;
    int cardinality;
    HashSet<Integer> groundSet;
    public HyperedgeSet(String name, HashSet<Integer> groundSet, int cardinality){
        this.name = name;
        this.groundSet = groundSet;
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

    public static ElementSet emptySet() {
        return new HyperedgeSet("", new HashSet<>(), 0);
    }

    @Override
    public ElementSet union(ElementSet b) {
        if (b instanceof HyperedgeSet) {
            HashSet<Integer> newGroundSet = new HashSet<>(groundSet);
            newGroundSet.addAll(((HyperedgeSet)b).groundSet);
            return new HyperedgeSet(getName().concat("+").concat(b.getName()), newGroundSet, cardinality + b.cardinality());
        }
        else throw new ClassCastException("Union of two different implementations of ElementSet.");
    }
    public static ElementSet readHyperedge(String hyperedge, int i){
        if (hyperedge.startsWith("%")) return null;
        HashSet<Integer> groundSet = new HashSet<>();
        String[] parts = hyperedge.split(" ");
        for (String part : parts) {
            groundSet.add(Integer.parseInt(part));
        }
        return new HyperedgeSet(String.valueOf(i), groundSet, 1);
    }
}
