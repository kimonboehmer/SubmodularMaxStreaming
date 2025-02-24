import java.util.Arrays;
import java.util.HashSet;

public class FacilitySet implements ElementSet{
    String name;
    int cardinality;
    int[] weights;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int value() {
        int sum = 0;
        for (int weight : weights) sum += weight;
        return sum;
    }

    @Override
    public int cardinality() {
        return cardinality;
    }

    @Override
    public int marginalContribution(Element element) {
        int sum = 0;
        FacilitySet f = (FacilitySet) element;
        for (int i = 0; i < weights.length; i++) sum += Math.max(0, f.weights[i] - weights[i]);
        return sum;
    }

    @Override
    public ElementSet createUnion(ElementSet b) {
        FacilitySet f = (FacilitySet)b;
        int[] newWeights = new int[f.weights.length];
        for (int i = 0; i < weights.length; i++) {
            newWeights[i] = Math.max(f.weights[i], weights[i]);
        }
        return new FacilitySet(name + "+" + f.name, newWeights, cardinality + f.cardinality);
    }

    @Override
    public void union(ElementSet b) {
        FacilitySet f = (FacilitySet)b;
        for (int i = 0; i < weights.length; i++){
            if (f.weights[i] > weights[i]) weights[i] = f.weights[i];
        }

    }
    public FacilitySet(String name, int[] weights, int cardinality) {
        this.name = name;
        this.weights = weights;
        this.cardinality = cardinality;
    }
    public static Facility readFacility(String weightString, int i){
        if (weightString.startsWith("%")) return null;
        String[] weights = weightString.split(" ");
        return new Facility(String.valueOf(i),Arrays.stream(weights).mapToInt(Integer::parseInt).toArray(), 1);
    }
    public static ElementSet emptySet() {
        return new FacilitySet("", new int[10], 0);
    }

}
