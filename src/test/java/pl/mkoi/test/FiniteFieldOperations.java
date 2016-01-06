package pl.mkoi.test;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.model.FiniteField;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Polynomial;

import java.util.*;

public class FiniteFieldOperations {

    private final static Logger log = Logger.getLogger(FiniteFieldOperations.class);

    @Test
    public void finiteFieldOperationTest() {

        long m = 16;

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


        int firstElement = 9;
        int secondElement = 7;

        FiniteField.Element element1 = new FiniteField.Element((long) firstElement, list.get(firstElement).getValue());
        FiniteField.Element element2 = new FiniteField.Element((long) secondElement, list.get(secondElement).getValue());
        FiniteField.Element result = finiteField.multiplyElements(element1, element2);

        Assert.assertTrue(result.getPolynomial().equals(list.get(0).getValue()) && result.getOrderNumber().equals(list.get(0).getKey()));


        result = finiteField.divideElements(element1, element2);

        Assert.assertTrue(result.getPolynomial().equals(list.get(2).getValue()) && result.getOrderNumber().equals(list.get(2).getKey()));

        FiniteField.Element zeroresult = finiteField.addElements(element1, FiniteField.ZERO_ELEMENT);
        Assert.assertTrue(zeroresult.equals(element1));

        zeroresult = finiteField.addElements(FiniteField.ZERO_ELEMENT, element1);
        Assert.assertTrue(zeroresult.equals(element1));

        zeroresult = finiteField.addElements(element1, element1);
        Assert.assertTrue(zeroresult.equals(FiniteField.ZERO_ELEMENT));

        zeroresult = finiteField.multiplyElements(element1, FiniteField.ZERO_ELEMENT);
        Assert.assertTrue(zeroresult.equals(FiniteField.ZERO_ELEMENT));

        zeroresult = finiteField.multiplyElements(FiniteField.ZERO_ELEMENT, element1);
        Assert.assertTrue(zeroresult.equals(FiniteField.ZERO_ELEMENT));

        zeroresult = finiteField.divideElements(FiniteField.ZERO_ELEMENT, element1);
        Assert.assertTrue(zeroresult.equals(FiniteField.ZERO_ELEMENT));


    }

}
