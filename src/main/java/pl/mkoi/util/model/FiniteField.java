package pl.mkoi.util.model;

public class FiniteField {
    private final double m;
    private final GeneratorPolynomial generator;

    private FiniteField() {
        m = 0;
        generator = null;
    }

    public FiniteField(GeneratorPolynomial generator, int m) {
        this.generator = generator;
        this.m = StrictMath.pow(2, m);

        Polynomial temp = generator;
    }

    public Element getElement(int orderNumber) {
        return null;
    }

    public Element addElements(Element a, Element b) {
        Polynomial resultPoly = a.polynomial.xor(b.polynomial);
        return new Element(generator.getGeneratorPowers().get(resultPoly), resultPoly);
    }

    public Element subtractElements(Element a, Element b) {
        Polynomial resultPoly = a.polynomial.xor(b.polynomial);
        return new Element(generator.getGeneratorPowers().get(resultPoly), resultPoly);
    }

    public Element multiplyElements(Element a, Element b) {
        long newIndex = (long) ((a.orderNumber + b.orderNumber) % m);
        return new Element(newIndex, generator.getGeneratorPowers().inverse().get(newIndex));
    }

    public Element divideElements(Element a, Element b) {
        long newIndex = (long) ((a.orderNumber - b.orderNumber) % m);
        return new Element(newIndex, generator.getGeneratorPowers().inverse().get(newIndex));
    }


    public static class Element {
        private Polynomial polynomial;
        private Long orderNumber;

        private Element() {

        }

        public Element(Long orderNumber, Polynomial polynomial) {
            this.orderNumber = orderNumber;
            this.polynomial = polynomial;
        }

        public Polynomial getPolynomial() {
            return polynomial;
        }

        public Long getOrderNumber() {
            return orderNumber;
        }
    }
}
