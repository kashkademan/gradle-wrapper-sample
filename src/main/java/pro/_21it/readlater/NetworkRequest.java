package pro._21it.readlater;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetworkRequest {
    private final URI uri;
    private final HttpClient client = HttpClient.newHttpClient();

    public NetworkRequest(String stringUrl) {
        this.uri = URI.create(stringUrl);
    }

    public URI getUri() {
        return this.uri;
    }

    public void printUri() {
        System.out.println("Host: " + uri.getHost());
        System.out.println("Port: " + uri.getPort());
        System.out.println("Path: " + uri.getPath());
        System.out.println("Query: " + uri.getQuery());
        System.out.println("Fragment: " + uri.getFragment());
        System.out.println("Scheme: " + uri.getScheme());
        System.out.println("The row URI" + uri.toString());
    }

    public HttpResponse<String> getJson() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri)
            .header("Accept", "application/json")
            .GET()
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
