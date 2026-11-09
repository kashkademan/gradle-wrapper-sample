package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import pro._21it.readlater.readinglist.service.ReadingListService;
import pro._21it.readlater.common.error.ErrorResponse;

public final class DeleteReadingListHandler {
    private final static Logger logger = LoggerFactory.getLogger(ReadingListHandler.class);

    private DeleteReadingListHandler() {
    }

    public static int handleRequest(HttpExchange exchange, ReadingListService service) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        long id = 0L;

        try {
            id = Helper.validateAndReturnId(path);
        } catch (Exception e) {
            ErrorResponse response = 
                    new ErrorResponse(
                        "NOT_ALLOWED",
                        e.getMessage(),
                        List.of()
                    );
            HttpJsonSender.sendJson(exchange, 400, response);
        }        

        logger.info("Processing ID is {}", id);

        boolean deleted = service.delete(id);

        if (!deleted) {
            NotFoundResponse errorResponse = new NotFoundResponse("Item with " + id + " is not found");
            HttpJsonSender.sendJson(exchange, 404, errorResponse);
            return 404;
        } else {
            HttpJsonSender.sendJson(exchange, 204, null);
            return 204;
        }
    }
}

