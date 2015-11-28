package eu.appbucket.upserver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.Properties;

public class Probe {

    private static final Logger logger = Logger.getLogger(App.class);
    private String probeUrl;
    private String probeMatcher;
    private String output;
    private int code;

    public Probe(Properties properties) {
        loadSetting(properties);
    }

    private void loadSetting(Properties properties)  {
        probeUrl = properties.getProperty("probe.url");
        if(probeUrl == null) {
            throw new RuntimeException("No probe url in the properties file.");
        }
        probeMatcher = properties.getProperty("probe.matcher");
        if(probeMatcher == null) {
            throw new RuntimeException("No probe matcher in the properties file.");
        }
    }

    public void probe() throws Exception {
        readData();
        validateResponse();
        validateData();
    }

    private void readData() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = probeUrl + "?upserverTimeStamp=" + System.currentTimeMillis();
        logger.info("Connecting to: " + url);
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        code = response.getStatusLine().getStatusCode();
        try {
            HttpEntity myEntity = response.getEntity();
            output = EntityUtils.toString(myEntity);
            logger.info("Output received: " + (output != null ? truncateOutput(output) : "NULL"));
        } finally {
            response.close();
        }
    }

    private String truncateOutput(String outputToTruncate) {
        if(outputToTruncate == null) {
            return null;
        }
        if(outputToTruncate.length() < 100) {
            return outputToTruncate;
        } else {
            return outputToTruncate.substring(0, 97) + "...";
        }
    }

    private void validateResponse() throws Exception {
        if(code != HttpStatus.SC_OK) {
            throw new Exception("Incorrect response code.");
        }
    }
    private void validateData() throws Exception {
        if(output == null) {
            throw new Exception("Response is empty");
        }
        if(!output.matches(probeMatcher)) {
            throw new Exception("Unexpected response.");
        }
    }
}
