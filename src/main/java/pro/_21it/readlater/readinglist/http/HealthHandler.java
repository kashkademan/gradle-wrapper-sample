package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public final class HealthHandler {

    private HealthHandler() {
    }

    public static int handleRequest(HttpExchange exchange) throws IOException {
        HealthResponse response = new HealthResponse("UP", "readlater-starter");
        HttpJsonSender.sendJson(exchange, 200, response);
        return 200;
    }

}
