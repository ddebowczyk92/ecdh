package pl.mkoi.test;


import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.util.model.FiniteField;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Polynomial;

import java.util.*;

public class FiniteFieldOperations {

    private final static Logger log = Logger.getLogger(FiniteFieldOperations.class);

    @Test
    public void finiteFieldOperationTest() {

        long m = 4;

        Polynomial irreducible;
        GeneratorPolynomial generator;


        irreducible = Polynomial.createIrreducible(m);
        generator = GeneratorPolynomial.findGenerator(irreducible);

        while (!generator.toBinaryString().equals("10")) {
            irreducible = Polynomial.createIrreducible(m);
            generator = GeneratorPolynomial.findGenerator(irreducible);
        }

        log.debug("irreducible " + irreducible.toBinaryString());

        FiniteField finiteField = new FiniteField(generator, (int) m);


        List<Map.Entry<Long, Polynomial>> list = new LinkedList<>(generator.getGeneratorPowers().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Long, Polynomial>>() {
            @Override
            public int compare(Map.Entry<Long, Polynomial> o1, Map.Entry<Long, Polynomial> o2) {
                if (o2.getKey() > o1.getKey()) {
                    return -1;
                } else if (o2.getKey() < o1.getKey()) {
                    return 1;
                } else return 0;
            }
        });

        for (Map.Entry<Long, Polynomial> polynomialLongEntry : list) {
            log.debug(polynomialLongEntry.getValue().toString() + " " + polynomialLongEntry.getValue().toBinaryString());
        }

        int firstElement = 9;
        int secondElement = 7;

        FiniteField.Element el1 = new FiniteField.Element((long) firstElement, list.get(firstElement).getValue());
        FiniteField.Element el2 = new FiniteField.Element((long) secondElement, list.get(secondElement).getValue());

        FiniteField.Element result = finiteField.multiplyElements(el1, el2);

        assert result.getPolynomial().equals(list.get(0).getKey()) && result.getOrderNumber().equals(list.get(0).getValue());

        result = finiteField.divideElements(el1, el2);

        assert result.getPolynomial().equals(list.get(2).getKey()) && result.getOrderNumber().equals(list.get(2).getValue());
    }

}
