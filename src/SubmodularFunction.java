public class SubmodularFunction {
    /**
     * @param functionType specifies the kind of structure from which the submodular function is derived.
     * @return an empty element set for this structure.
     */
    public static ElementSet emptySet(String functionType){
        switch (functionType){
            case "Hyperedge": return HyperedgeSet.emptySet();
            case "Facility" : return FacilitySet.emptySet();
            default: throw new IllegalArgumentException("Unknown function type: " + functionType);
        }
    }

    /**
     * @param functionType specifies the kind of structure from which the submodular function is derived.
     * @param line string input encoding one element of the structure.
     * @param i for naming the element.
     * @return a single element of type functionType according to the input line.
     */
    public static Element readElement(String functionType, String line, int i){
        switch (functionType){
            case "Hyperedge": return HyperedgeSet.readHyperedge(line, i);
            case "Facility" : return FacilitySet.readFacility(line, i);
            default: throw new IllegalArgumentException("Unknown function type: " + functionType);
        }
    }
}
