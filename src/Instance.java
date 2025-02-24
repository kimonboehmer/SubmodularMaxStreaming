/**
 * @param functionType specifies the kind of structure from which the submodular function is derived.
 *                     Currently implemented: "Hyperedge", "Facility".
 * @param fileName relative file path to the instance.
 * @param k desired maximum solution size.
 */
public record Instance(String functionType, String fileName, int k) {
}