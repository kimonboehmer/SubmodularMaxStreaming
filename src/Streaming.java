import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Implementation of the streaming algorithm with a (0.5-epsilon)-approximation guarantee
 */
public class Streaming {
    int memory;
    int time;
    private final String functionType;
    private int firstB;
    private int lastB;
    private final int k;
    private final LinkedList<ElementSet> phi;
    private int maxValue;
    private final double epsilon;
    private final String fileName;
    public Streaming(Instance inst, double epsilon){
        this.functionType = inst.functionType();
        this.k = inst.k();
        this.epsilon = epsilon;
        this.fileName = inst.fileName();
        maxValue = 0;
        phi = new LinkedList<>();
        firstB = Integer.MIN_VALUE;
        lastB = Integer.MIN_VALUE;
    }

    /**
     * runs the streaming algorithm, while keeping track of time and memory.
     * @return an ElementSet which represents the solution of the algorithm.
     */
    public ElementSet run() {
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int i = 0; // only for element-naming
            for (String line; (line = br.readLine()) != null; ) {
                Element es = SubmodularFunction.readElement(functionType, line, i++);
                if (es == null) continue; // this line was a comment, so we can ignore it
                processElement(es); // main procedure
                int currentMemory = memSize();
                if (currentMemory > memory) memory = currentMemory;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        time = (int) (System.currentTimeMillis() - startTime);
        return bestCollection();
    }
    public void processElement(Element e){
        if (e.value() > maxValue) { // we need to change phi
            maxValue = e.value();
            int newFirst = (int) Math.ceil(Math.log(maxValue) / Math.log(1 + epsilon)); // calculate new limits of phi
            int newLast = (int) Math.floor(Math.log(2 * k * maxValue) / Math.log(1 + epsilon));
            if (firstB == Integer.MIN_VALUE){ // for the initialization: create phi-list
                for (int i = newFirst; i <= newLast; i++) phi.add(SubmodularFunction.emptySet(functionType));
            }
            else { // for all other iterations; update phi-list
                for (int i = 0; i < newFirst - firstB; i++) {
                    if (!phi.isEmpty()) phi.removeFirst(); else break;
                }
                for (int i = 0; i < newLast - lastB; i++) phi.add(SubmodularFunction.emptySet(functionType));
            }
            firstB = newFirst; // update limits of phi
            lastB = newLast;
        }
        int i = firstB;
        for (ElementSet phiI : phi){ // update element-sets in phi according to i and the marginal contribution of e
            if (phiI.cardinality() < k && phiI.marginalContribution(e) >= ((Math.pow(1 + epsilon, i) / 2 ) - phiI.value()) / (k - phiI.cardinality())){
                phiI.union(e);
            }
            i++;
        }
    }

    /**
     * @return the best ElementSet among all ElementSets in phi.
     */
    private ElementSet bestCollection(){
        ElementSet bestSet = SubmodularFunction.emptySet(functionType);
        for (ElementSet phiI : phi){
            if (phiI.value() > bestSet.value()) {
                bestSet = phiI;
            }
        }
        return bestSet;
    }
    private int memSize(){
        int count = 0;
        for (ElementSet phiI : phi){
            count += phiI.value();
        }
        return count;
    }
}
