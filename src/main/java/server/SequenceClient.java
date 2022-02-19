package server;

import config.Config;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SequenceClient implements AutoCloseable {

    private final CloseableHttpClient client;
    private final String targetAddress;
    private CloseableHttpResponse response;

    public SequenceClient(Service service, String route){
        this.client = HttpClients.createDefault();
        this.targetAddress = Config.getInstance().getTargetURL(service) + route;
    }

    public String issuePost(String payload) throws IOException {
        HttpPost post = new HttpPost(targetAddress);
        post.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    public String issueGet() throws IOException {
        response = client.execute(new HttpGet(targetAddress));
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public void close() throws Exception {
        client.close();
    }
}
