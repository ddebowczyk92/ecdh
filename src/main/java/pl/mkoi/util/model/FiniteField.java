package pl.mkoi.util.model;

import java.util.Map;

public class FiniteField {
    private final double m;
    private final GeneratorPolynomial generator;

    private FiniteField() {
        m = 0;
        generator = null;
    }

    public FiniteField(GeneratorPolynomial generator, int m) {
        this.generator = generator;
        this.m = StrictMath.pow(2, m) - 1;
    }


    public Element addElements(Element a, Element b) {
        Polynomial resultPoly = a.polynomial.xor(b.polynomial);
        return new Element(generator.getGeneratorPowers().inverse().get(resultPoly), resultPoly);
    }

    public Element addElements(Element a, Polynomial b) {
        Polynomial resultPoly = a.polynomial.xor(b);
        return new Element(generator.getGeneratorPowers().inverse().get(resultPoly), resultPoly);
    }

    public Element subtractElements(Element a, Element b) {
        return addElements(a, b);
    }

    public Element multiplyElements(Element a, Element b) {
        if (a.getPolynomial().getDegree() <= 0 || b.getPolynomial().getDegree() <= 0)
            return new Element(0L, Polynomial.createFromLong(0L));

        long newIndex = (long) ((a.orderNumber + b.orderNumber) % m);
        return new Element(newIndex, generator.getGeneratorPowers().get(newIndex));
    }

    public Element powerElement(Element a, int power) {
        if (a.getPolynomial().getDegree() == 0) {
            return a;
        } else {
            long newIndex = (long) (a.getOrderNumber() * power % m);
            return new Element(newIndex, generator.getGeneratorPowers().get(newIndex));
        }
    }

    public Element divideElements(Element a, Element b) {
        long newIndex = (long) ((a.orderNumber - b.orderNumber) % m);

        if (newIndex < 0) newIndex += m;

        return new Element(newIndex, generator.getGeneratorPowers().get(newIndex));
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

        public Element(Map.Entry<Long, Polynomial> polynomialLongEntry) {
            this.orderNumber = polynomialLongEntry.getKey();
            this.polynomial = polynomialLongEntry.getValue();
        }

        public Polynomial getPolynomial() {
            return polynomial;
        }

        public Long getOrderNumber() {
            return orderNumber;
        }

        @Override
        public String toString() {
            return "Finite field element g^" + orderNumber + " " + polynomial.toBinaryString();
        }
    }
}
