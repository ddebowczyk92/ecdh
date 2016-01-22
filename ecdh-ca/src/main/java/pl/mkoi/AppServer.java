package pl.mkoi;

import com.google.common.eventbus.Subscribe;
import org.apache.log4j.Logger;
import pl.mkoi.ecdh.crypto.model.Polynomial;
import pl.mkoi.ecdh.event.CurveCalculatedEvent;
import pl.mkoi.generator.DomainParametersGenerator;
import pl.mkoi.server.Server;

/**
 * Hello world!
 */
public class AppServer {

    private static final Logger log = Logger.getLogger(Server.class);

    public static void main(String[] args) {

        AppServer appServer = new AppServer();
        AppContext.getInstance().registerListener(appServer);


        log.info("WELCOME TO ECDH CHAT SERVER");
        log.info("GENERATING CURVE FOR GIVEN PARAMETERS");

        DomainParametersGenerator generator = new DomainParametersGenerator();
        generator.runGenerator(4, Polynomial.createFromLong(5), Polynomial.createFromLong(13));


    }


    @Subscribe
    public void onCurveCalculated(final CurveCalculatedEvent event) {
        AppContext.getInstance().setCurve(event.getCurve());

        Server server = new Server(455, 10);
        server.run();
    }
}
