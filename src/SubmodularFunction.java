public class SubmodularFunction {
    public static ElementSet emptySet(String functionType){
        switch (functionType){
            case "Hyperedge": return HyperedgeSet.emptySet();
            case "Facility" : return FacilitySet.emptySet();
            default: throw new IllegalArgumentException("Unknown function type: " + functionType);
        }
    }
    public static Element readElement(String functionType, String line, int i){
        switch (functionType){
            case "Hyperedge": return HyperedgeSet.readHyperedge(line, i);
            case "Facility" : return FacilitySet.readFacility(line, i);
            default: throw new IllegalArgumentException("Unknown function type: " + functionType);
        }
    }
}
