package pro._21it.readlater.readinglist.http;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import tools.jackson.databind.json.JsonMapper;
import pro._21it.readlater.ProviderJsonReader;

public final class HttpJsonSender {

    private final static JsonMapper JSON_MAPPER = ProviderJsonReader.getMapper();

    private HttpJsonSender() {
    }

    public static void sendJson(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] bytes = JSON_MAPPER.writeValueAsBytes(body);

        // Явно указываем текстовый ответ в UTF-8, чтобы клиент корректно интерпретировал тело.
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        if (status == 201) {
            exchange.getResponseHeaders().set("Location", "/api/v1/reading-list/" + status);
        };

        // Перед отправкой тела нужно сначала объявить статус и длину ответа.
        exchange.sendResponseHeaders(status, bytes.length);

        // Response body обязательно закрываем через try-with-resources.
        // Это гарантирует, что ответ будет корректно завершён и ресурсы освободятся.
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bytes);
        }

        exchange.close();
    }
}
