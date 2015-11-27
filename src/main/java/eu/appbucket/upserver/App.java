package eu.appbucket.upserver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        try {
            Probe.probe();
        } catch (Exception e) {
            Mailer.sentMail();
        }
    }
}
