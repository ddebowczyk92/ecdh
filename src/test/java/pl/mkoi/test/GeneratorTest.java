package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Polynomial;

/**
 * Created by DominikD on 2015-12-25.
 */
public class GeneratorTest {

    private final static Logger log = Logger.getLogger(GeneratorTest.class);

    @Test
    public void generator() {
        Polynomial irreducible = Polynomial.createIrreducible(4L);
        log.debug("irreducible " + irreducible.toBinaryString());
        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
        log.info("Generator: " + generator.toBinaryString() + " for polynomial: " + irreducible.toBinaryString());
        log.info("Generator storage size: " + generator.getGeneratorPowers().size());
    }
}
