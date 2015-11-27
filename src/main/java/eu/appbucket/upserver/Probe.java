package eu.appbucket.upserver;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Pattern;

public class Probe {

    public static void probe() throws Exception {
        String output = request();
        validate(output);
    }

    private static String request() throws Exception {
        String output = new String();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://api.prod.queue.appbucket.eu/ping");
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity myEntity = response.getEntity();
        try {
            output = EntityUtils.toString(myEntity);
        } finally {
            response.close();
        }
        return output;
    }

    private static void validate(String validateString) throws Exception {
        if(validateString == null) {
            throw new Exception("Response is empty");
        }
        String regex = "\\{\"databaseCurrentDate\":\\d+,\"serverCurrentDate\":\\d+\\}";
        if(!validateString.matches(regex)) {
            throw new Exception("Unexpected response.");
        }
    }
}
