
public class Facility extends FacilitySet implements Element{
    public Facility(String name, int[] weights, int cardinality) {
        super(name, weights, cardinality);
        this.cardinality = 1;
    }

}
