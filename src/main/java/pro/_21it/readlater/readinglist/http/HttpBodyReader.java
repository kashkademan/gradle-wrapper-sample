package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class HttpBodyReader {

    private HttpBodyReader() {
    }

    public static String readBody(HttpExchange exchange) throws IOException {
        try (var is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
