package pl.mkoi;

import com.google.common.base.Strings;
import com.google.common.eventbus.Subscribe;
import org.apache.log4j.Logger;
import pl.mkoi.ecdh.crypto.model.Polynomial;
import pl.mkoi.ecdh.event.CurveCalculatedEvent;
import pl.mkoi.generator.DomainParametersGenerator;
import pl.mkoi.server.Server;

/**
 * Main class of server application
 */
public class AppServer {

    private static final Logger log = Logger.getLogger(Server.class);
    private static int portNumber = 455;

    public static void main(String[] args) {


        if (args.length == 0 || Strings.isNullOrEmpty(args[0])) {
            log.info("Using default port: " + portNumber);
        } else {
            try {
                String portNumberString = args[0];
                int newPortNumber = Integer.parseInt(portNumberString);
                portNumber = newPortNumber;
                log.info("Using " + newPortNumber + " port for incoming connections");
            } catch (NumberFormatException e) {
                log.info("Invalid server port number, using default one: " + portNumber);
            }
        }

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

        Server server = new Server(portNumber, 10);
        server.run();
    }
}
