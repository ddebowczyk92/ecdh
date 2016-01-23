package pl.mkoi.generator;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.crypto.model.*;
import pl.mkoi.ecdh.crypto.util.PointGenerator;
import pl.mkoi.ecdh.event.CurveCalculatedEvent;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DomainParametersJob implements Runnable {

    private final static Logger log = Logger.getLogger(DomainParametersJob.class);
    private int m;
    private Polynomial a, b;

    public DomainParametersJob(int m, Polynomial a, Polynomial b) {
        this.m = m;
        this.a = a;
        this.b = b;
    }


    @Override
    public void run() {
        Polynomial irreducible = Polynomial.createIrreducible(m);
        GeneratorPolynomial generatorPolynomial = GeneratorPolynomial.findGenerator(irreducible);
        FiniteField finiteField = new FiniteField(generatorPolynomial, m, irreducible);

        EllipticCurve curve = new EllipticCurve(a, b, finiteField, irreducible);
        Point generatorPoint = PointGenerator.generatePoint(curve, generatorPolynomial);
        curve.setGeneratorPoint(generatorPoint);

        log.info("Curve generated:" + curve);

        AppContext.getInstance().postEvent(new CurveCalculatedEvent(curve));
    }
}
