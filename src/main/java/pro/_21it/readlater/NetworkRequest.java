package pro._21it.readlater;

import java.net.URI;

public class NetworkRequest {
    private final URI uri;

    public NetworkRequest(String stringUrl) {
        this.uri = URI.create(stringUrl);
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
}
