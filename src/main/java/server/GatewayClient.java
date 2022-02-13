package server;

import config.Config;
import config.Service;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GatewayClient implements AutoCloseable {

    private final CloseableHttpClient client;
    private final String targetAddress;
    private CloseableHttpResponse response;

    static String tryPostIssue(Service service, String route, String payload){
        try(GatewayClient client = new GatewayClient(service, route)){
            return client.issuePost(payload);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static String tryGetIssue(Service service, String route){
        try(GatewayClient client = new GatewayClient(service, route)){
            return client.issueGet();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GatewayClient(Service service, String route){
        this.client = HttpClients.createDefault();
        this.targetAddress = Config.getInstance().getServerAddress(service) + route;
    }

    private String issuePost(String payload) throws IOException {
        HttpPost post = new HttpPost(targetAddress);
        post.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    private String issueGet() throws IOException {
        response = client.execute(new HttpGet(targetAddress));
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public void close() throws Exception {
        response.close();
        client.close();
    }
}
