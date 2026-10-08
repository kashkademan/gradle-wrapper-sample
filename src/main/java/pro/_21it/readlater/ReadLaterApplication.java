package pro._21it.readlater;

import pro._21it.readlater.NetworkRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.io.IOException;
import java.time.Duration;

public class ReadLaterApplication {
    private final static String STRING_URL = "https://openlibrary.org/search.json?q=";
    private final static String BASE_URL = "https://openlibrary.org";
    private final static String QUERY = "java & spring";
    private final static String EXTERNAL_ID = "OL12345M";
    private final static HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(9))
        .build();
    private static String query = "";
    private static HttpResponse<String> response;

    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        String json = new ObjectMapper().writeValueAsString(new Ping("UP"));
        System.out.println(json);

        URI searchUri = NetworkRequest.buildSearchUri(BASE_URL, QUERY);
        URI detailsUri = NetworkRequest.buildDetailsUri(BASE_URL, EXTERNAL_ID);

        logger.info("Search URI: " + searchUri);
        logger.info("Details URI: " + detailsUri);

        try {
            HttpResponse<String> response = client.send(NetworkRequest.buildGetRequest(searchUri), HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            logger.info(is2xx(status) ? "SUCCESS: " + status : "ERROR: " + status);
        } catch (HttpTimeoutException e) {
            logger.error("REQUEST_TIMEOUT");
        } catch (IOException e) {
            logger.error("IO_ERROR");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("INTERRUPTED");
        }
    }

    private static record Ping(String status) {
    };

    public static boolean is2xx(int status) {
        return status >= 200 && status <=200;
    };
}
