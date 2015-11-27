package eu.appbucket.upserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurator {

    private String path;

    public Configurator(String path) {
        this.path=path;
    }

    public Properties read() throws Exception {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            // load a properties file
            prop.load(input);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
