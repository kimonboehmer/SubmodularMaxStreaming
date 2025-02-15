public class SubmodularFunction {
    public static ElementSet emptySet(String functionType){
        switch (functionType){
            case "Hyperedge": return HyperedgeSet.emptySet();
            default: throw new IllegalArgumentException("Unknown function type: " + functionType);
        }
    }
}
