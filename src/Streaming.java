import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Streaming {
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
    public ElementSet stream() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null; ) {
                ElementSet es = HyperedgeSet.readHyperedge(line, i++);
                if (es == null) continue;
                processElement(es);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bestCollection();
    }
    public void processElement(ElementSet e){
        if (e.value() > maxValue) {
            maxValue = e.value();
            int newFirst = (int) Math.ceil(Math.log(maxValue) / Math.log(1 + epsilon));
            int newLast = (int) Math.floor(Math.log(2 * k * maxValue) / Math.log(1 + epsilon));
            if (firstB == Integer.MIN_VALUE){
                for (int i = newFirst; i <= newLast; i++) phi.add(SubmodularFunction.emptySet(functionType));
            }
            else {
                for (int i = 0; i < newFirst - firstB; i++) {
                    if (!phi.isEmpty()) phi.removeFirst(); else break;
                }
                for (int i = 0; i < newLast - lastB; i++) phi.add(SubmodularFunction.emptySet(functionType));
            }
            firstB = newFirst;
            lastB = newLast;
        }
        int i = firstB;
        for (ElementSet phiI : phi){
            if (phiI.cardinality() < k && HelperFunctions.marginalContribution(e, phiI) >= ((Math.pow(1 + epsilon, i) / 2 ) - phiI.value()) / (k - phiI.cardinality())){
                phiI.union(e);
            }
            i++;
        }
    }
    private ElementSet bestCollection(){
        ElementSet bestSet = SubmodularFunction.emptySet(functionType);
        for (ElementSet phiI : phi){
            if (phiI.value() > bestSet.value()) {
                bestSet = phiI;
            }
        }
        return bestSet;
    }
}
