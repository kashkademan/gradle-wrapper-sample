package pro._21it.readlater;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class NetworkRequest {
    public static URI buildSearchUri(String baseUrl, String query) {
        String encodedQuery  = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return URI.create(baseUrl + "/search?q=" + encodedQuery);
    }

    public static URI buildDetailsUri(String baseUrl, String externalId) {
        return URI.create(baseUrl + "/books/" + externalId);
    }
    
    public static HttpRequest buildSearchGetRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
            .header("Accept", "application/json")
            .header("User-Agent", "readlater-starter/1.0")
            .GET()
            .build();
    }

    // public HttpResponse<String> getJson() throws Exception {
    //     HttpRequest request = HttpRequest.newBuilder(uri)
    //         .header("Accept", "application/json")
    //         .GET()
    //         .build();

    //     return client.send(request, HttpResponse.BodyHandlers.ofString());
    // }

}
