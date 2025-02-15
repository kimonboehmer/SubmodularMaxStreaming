import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Algorithm {
    private final String functionType;
    private int firstB;
    private int lastB;
    private final int k;
    private LinkedList<ElementSet> phi;
    private int maxValue;
    private final double epsilon;
    private final String fileName;
    public Algorithm(String functionType, String fileName, double epsilon, int k){
        this.functionType = functionType;
        this.k = k;
        this.epsilon = epsilon;
        this.fileName = fileName;
        maxValue = 0;
        phi = null;
        firstB = Integer.MIN_VALUE;
        lastB = Integer.MIN_VALUE;
    }
    public void stream() {
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
                for (int i = 0; i < newFirst - firstB; i++) phi.removeFirst();
                for (int i = 0; i < lastB - newLast; i++) phi.add(SubmodularFunction.emptySet(functionType));
            }
            firstB = newFirst;
            lastB = newLast;
        }
        int i = firstB;
        for (ElementSet phiI : phi){
            if (phiI.cardinality() < k && marginalContribution(e, phiI) >= ((Math.pow(1 + epsilon, i) / 2 ) - phiI.value()) / (k - phiI.cardinality())){
                phiI.union(e);
            }
            i++;
        }
    }
    public int marginalContribution(ElementSet element, ElementSet set){
        return element.union(set).value() - set.value();
    }

}
