package pro._21it.readlater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;

public class ReadLaterApplication {
    private final static String BASE_URL = "https://openlibrary.org";
    private final static Integer QUERY_LIMIT = 2;
    private final static String QUERY = "clean code";
    private final static String EXTERNAL_ID = "OL12345M";

    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        CatalogCommand catalogCommand = new CommandRouter().run(args);
        logger.info("Command: {}", catalogCommand);

        URI searchUri = NetworkRequest.buildSearchUri(BASE_URL, QUERY, QUERY_LIMIT);
        URI detailsUri = NetworkRequest.buildDetailsUri(BASE_URL, EXTERNAL_ID);

        logger.info("Search URI: " + searchUri);
        logger.info("Details URI: " + detailsUri);
    }

    public static boolean is2xx(int status) {
        return status >= 200 && status <= 200;
    };

}
