import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Approx {
    Element[] elements;
    String type;
    int k;
    int memory;
    int time;
    public Approx(Instance instance) {
        long startTime = System.currentTimeMillis();
        LinkedList<Element> elementsList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(instance.fileName()))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null; ) {
                Element es = SubmodularFunction.readElement(instance.functionType(), line, i++);
                if (es == null) continue;
                elementsList.add(es);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        elements = new Element[elementsList.size()];
        elements = elementsList.toArray(elements);
        this.type = instance.functionType();
        this.k = instance.k();
        this.memory = memSize();
        time = (int) ((int) System.currentTimeMillis() - startTime);
    }
    public ElementSet run(){
        long startTime = System.currentTimeMillis();
        ElementSet currentSet = SubmodularFunction.emptySet(type);
        for (int i = 0; i < k; i++){
            int best = findMax(currentSet);
            currentSet.union(elements[best]);
        }
        memory += currentSet.value();
        time += (int) (System.currentTimeMillis() - startTime);
        return currentSet;
    }
    private int findMax(ElementSet currentSet) {
        int best = 0;
        int val = currentSet.marginalContribution(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            int thisVal = currentSet.marginalContribution(elements[i]);
            if (thisVal > val) {
                val = thisVal;
                best = i;
            }
        }
        return best;
    }
    private int memSize() {
        int size = 0;
        for (ElementSet set : elements) {
            size += set.value();
        }
        return size;
    }
}
