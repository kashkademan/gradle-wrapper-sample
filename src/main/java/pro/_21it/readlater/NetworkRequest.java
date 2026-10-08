package pro._21it.readlater;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NetworkRequest {
    public static URI buildSearchUri(String baseUrl, String query) {
        String encodedQuery  = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return URI.create(baseUrl + "/search?q=" + encodedQuery);
    }

    public static URI buildDetailsUri(String baseUrl, String externalId) {
        return URI.create(baseUrl + "/books/" + externalId);
    }
    
    public static HttpRequest buildGetRequest(URI uri) {
        return HttpRequest.newBuilder()
            .uri(uri)
            .header("Accept", "application/json")
            .header("User-Agent", "readlater-starter/1.0")
            .timeout(Duration.ofSeconds(9))
            .GET()
            .build();
    }

    public static HttpRequest buildPostRequest(URI uri) {
        String json = """
            {
                "query": "clean code",
                "limit": 5
            }
        """;
        return HttpRequest.newBuilder()
            .uri(uri)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(3))
            .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
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
