package eu.appbucket.upserver;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        logger.info("Starting...");
        String propertiesFile = args[0];
        Configurator configurator = new Configurator(propertiesFile);
        Properties properties;
        try {
            properties = configurator.read();
        } catch (Exception e) {
            logger.error("Can't read properties file.", e);
            return;
        }

        Mailer mailer;
        try {
            mailer = new Mailer(properties);
        } catch (Exception e) {
            logger.error("Mailer configuration error.", e);
            return;
        }

        Probe probe;
        try {
            probe = new Probe(properties);
        } catch (Exception e) {
            logger.error("Probe configuration error.", e);
            return;
        }

        boolean doSentMail = false;
        try {
            probe.probe();
        } catch (Exception e) {
            logger.error("Probe issue.", e);
            doSentMail = true;
        }

        if(doSentMail) {
            try {
                mailer.sentMail();
            } catch (Exception e) {
                logger.error("Mailer issue.", e);
            }
        }

        logger.info("Finished.");
    }
}
