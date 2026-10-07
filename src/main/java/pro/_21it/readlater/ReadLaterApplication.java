package pro._21it.readlater;

import pro._21it.readlater.NetworkRequest;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.net.http.HttpResponse;

public class ReadLaterApplication {
    private final static String STRING_URL = "https://openlibrary.org/search.json?q=";
    private static String query = "";
    private static HttpResponse<String> response;

    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        String json = new ObjectMapper().writeValueAsString(new Ping("UP"));
        System.out.println(json);

        try {
            if(args.length == 0) {
                logger.info("No parameters. The request is stopped.");
                return;
            } else {
                query = String.join("%20", args);
                NetworkRequest networkRequest = new NetworkRequest(STRING_URL + query);
                response = networkRequest.getJson();

                logger.info("Status: " + response.statusCode());
                logger.info("Headers: " + response.headers().firstValue("content-type"));
                logger.info("Result: " + response.body()
                        .substring(0, Math.min(response.body().length(), 50)));
            }
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
    }

    private static record Ping(String status) {
    };
}
