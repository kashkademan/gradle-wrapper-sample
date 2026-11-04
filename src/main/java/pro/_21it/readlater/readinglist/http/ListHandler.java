package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;
import pro._21it.readlater.readinglist.service.ReadingListService;

public final class ListHandler {

    private ListHandler() {
    }

    public static int handleRequest(HttpExchange exchange, ReadingListService service) throws IOException {
        ReadingListResponse response = service.findAll();
        HttpJsonSender.sendJson(exchange, 200, response);
        return 200;
    }

}
