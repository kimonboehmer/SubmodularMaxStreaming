import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Approx {
    ElementSet[] elements;
    String type;
    int k;
    public Approx(Instance instance) {
        LinkedList<ElementSet> elementsList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(instance.fileName()))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null; ) {
                ElementSet es = HyperedgeSet.readHyperedge(line, i++);
                if (es == null) continue;
                elementsList.add(es);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        elements = new ElementSet[elementsList.size()];
        elements = elementsList.toArray(elements);
        this.type = instance.functionType();
        this.k = instance.k();
    }
    public ElementSet run(){
        ElementSet currentSet = SubmodularFunction.emptySet(type);
        for (int i = 0; i < k; i++){
            int best = findMax(currentSet);
            currentSet.union(elements[best]);
        }
        return currentSet;
    }
    private int findMax(ElementSet currentSet) {
        int best = 0;
        int val = HelperFunctions.marginalContribution(elements[0], currentSet);
        for (int i = 1; i < elements.length; i++) {
            int thisVal = HelperFunctions.marginalContribution(elements[i], currentSet);
            if (thisVal > val) {
                val = thisVal;
                best = i;
            }
        }
        return best;
    }
}
