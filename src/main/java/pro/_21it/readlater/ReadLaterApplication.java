package pro._21it.readlater;

import pro._21it.readlater.NetworkRequest;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class ReadLaterApplication {
    private final static String STRING_URL = "https://catalog.example/search?q=clean%20code&limit=5";

    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        String json = new ObjectMapper().writeValueAsString(new Ping("UP"));
        System.out.println(json);

        NetworkRequest networkRequest = new NetworkRequest(STRING_URL);
        networkRequest.printUri();        
    }

    private static record Ping(String status) {
    };
}
