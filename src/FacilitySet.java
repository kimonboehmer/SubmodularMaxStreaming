import java.util.Arrays;

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
        for (int i = 0; i < weights.length; i++) sum += Math.max(0, f.weights[i] - weights[i]); // element only helps if the weight is better than the max until now
        return sum;
    }

    @Override
    public void union(ElementSet b) {
        FacilitySet f = (FacilitySet)b;
        if (weights.length == 0) weights = new int[f.weights.length]; // update the weight array size in case we had an empty set
        for (int i = 0; i < weights.length; i++){ // find the max weight for each ground element
            if (f.weights[i] > weights[i]) weights[i] = f.weights[i];
        }

    }
    public FacilitySet(String name, int[] weights, int cardinality) {
        this.name = name;
        this.weights = weights;
        this.cardinality = cardinality;
    }
    public static Facility readFacility(String weightString, int i){
        if (weightString.startsWith("%")) return null; // comment line
        String[] weights = weightString.split(" ");
        return new Facility(String.valueOf(i),Arrays.stream(weights).mapToInt(Integer::parseInt).toArray(), 1);
    }

    /**
     * @return an empty set. The size of the weight array cannot be known in advance,
     * but this is not an issue since it will be corrected with the arrival of the first facility.
     */
    public static ElementSet emptySet() {
        return new FacilitySet("", new int[0], 0);
    }

}
