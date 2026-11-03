package pro._21it.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public final class ListHandler {

    private ListHandler() {
    }

    public static int handleRequest(HttpExchange exchange) throws IOException {
        ReadingListResponse response = service.findAll();
        HttpJsonSender.sendJson(exchange, 200, response);
        return 200;
    }

}
