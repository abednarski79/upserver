package eu.appbucket.upserver;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Properties;

public class Probe {

    private String probeUrl;
    private String probeMatcher;

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
        String output = readData();
        validateData(output);
    }

    private String readData() throws Exception {
        String output = new String();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(probeUrl);
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity myEntity = response.getEntity();
        try {
            output = EntityUtils.toString(myEntity);
        } finally {
            response.close();
        }
        return output;
    }

    private void validateData(String validateString) throws Exception {
        if(validateString == null) {
            throw new Exception("Response is empty");
        }
        // String regex = "\\{\"databaseCurrentDate\":\\d+,\"serverCurrentDate\":\\d+\\}";
        if(!validateString.matches(probeMatcher)) {
            throw new Exception("Unexpected response.");
        }
    }
}
