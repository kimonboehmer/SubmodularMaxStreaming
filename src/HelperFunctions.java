public class HelperFunctions {
    public static int marginalContribution(ElementSet element, ElementSet set){
        return element.createUnion(set).value() - set.value();
    }
}
