package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import pro._21it.readlater.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.ProviderJsonReader;
import pro._21it.readlater.readinglist.service.ReadingListService;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import pro._21it.readlater.common.error.ErrorResponse;

public final class PutReadingListHandler {
    private final static Logger logger = LoggerFactory.getLogger(PutReadingListHandler.class);

    private PutReadingListHandler() {
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

        Helper.requireJsonContentType(exchange);
        String body = HttpBodyReader.readBody(exchange);
        if (body.isBlank()) {
            throw new IllegalArgumentException("The body is mandatory");
        }

        CreateReadingItemRequest request = ProviderJsonReader.read(body, CreateReadingItemRequest.class);
        ReadingItemResponse response = service.createReadingListItem(request);
        HttpJsonSender.sendJson(exchange, 201, response);
        return 201;
    }
}

