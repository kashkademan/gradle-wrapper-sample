package pro._21it.readlater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.io.IOException;
import java.time.Duration;

import pro._21it.readlater.dto.provider.*;

public class ReadLaterApplication {
    private final static String BASE_URL = "https://openlibrary.org";
    private final static Integer QUERY_LIMIT = 2;
    private final static String QUERY = "clean code";
    private final static String EXTERNAL_ID = "OL12345M";
    private final static HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(9))
            .build();
    private final static ProviderJsonReader jsonReader = new ProviderJsonReader();

    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        URI searchUri = NetworkRequest.buildSearchUri(BASE_URL, QUERY, QUERY_LIMIT);
        URI detailsUri = NetworkRequest.buildDetailsUri(BASE_URL, EXTERNAL_ID);

        logger.info("Search URI: " + searchUri);
        logger.info("Details URI: " + detailsUri);

        try {
            HttpResponse<String> response = client.send(
                    NetworkRequest.buildGetRequest(searchUri),
                    HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            logger.info(is2xx(status) ? "SUCCESS: " + status : "ERROR: " + status);
            ProviderSearchResponseDto responseDto = jsonReader.read(response.body(), ProviderSearchResponseDto.class);
            logger.info("response dto: " + responseDto);
        } catch (HttpTimeoutException e) {
            logger.error("REQUEST_TIMEOUT");
        } catch (IOException e) {
            logger.error("IO_ERROR");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("INTERRUPTED");
        }
    }

    public static boolean is2xx(int status) {
        return status >= 200 && status <= 200;
    };
}
