package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import pro._21it.readlater.dto.CreateReadingItemRequest;
import pro._21it.readlater.ProviderJsonReader;

public final class CreateReadingListHandler {

    private CreateReadingListHandler() {
    }

    public static int handleRequest(HttpExchange exchange) throws IOException {
        Helper.requireJsonContentType(exchange);
        String body = HttpBodyReader.readBody(exchange);
        if (body.isBlank()) {
            throw new IllegalArgumentException("The body is mandatory");
        }
        CreateReadingItemRequest request = ProviderJsonReader.read(body, CreateReadingItemRequest.class);
        CreatedResponse response = new CreatedResponse(request.toString());
        HttpJsonSender.sendJson(exchange, 201, response);
        return 201;
    }
}
