package pro._21it.readlater.app.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import tools.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.ProviderJsonReader;

final class ReadingListHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalApiServer.class);
    private static final JsonMapper JSON_MAPPER = ProviderJsonReader.getMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        route(exchange);
    }

    private void route(HttpExchange exchange) throws IOException {
         // 1) Достаём данные, по которым будем маршрутизировать
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        
        // 2) Сначала самые точные совпадения
        if ("GET".equals(method) && "/health".equals(path)) {
            handleHealth(exchange);
            return;
        }

        if ("GET".equals(method) && "/api/v1/reading-list".equals(path)) {
            handleList(exchange);
            return;
        }

        // 3) Потом более общий маршрут с переменным сегментом
        if ("GET".equals(method) && path.startsWith("/api/v1/reading-list/")) {
            handleGetByIdRoute(exchange);
            return;
        }

        // 4) Ветка по умолчанию: ничего не подошло
        sendNotFound(exchange);
    }

    private void handleHealth(HttpExchange exchange) throws IOException {
        // 1) Готовим DTO ответа (часть контракта)
        HealthResponse response = new HealthResponse("UP", "readlater-starter");

        // 2) Сериализуем в байты, чтобы точно знать длину ответа
        byte[] body = JSON_MAPPER.writeValueAsBytes(response);

        // 3) Выставляем заголовки ДО sendResponseHeaders
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

        // 4) Объявляем статус и длину ответа
        exchange.sendResponseHeaders(200, body.length);

        // 5) Пишем тело и обязательно закрываем поток (try-with-resources)
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body);
        }
    }

    private void handleList(HttpExchange exchange) throws IOException {
        LOGGER.info("We are in {}", exchange.getRequestURI().getPath());
        return;
    }

    private void handleGetByIdRoute(HttpExchange exchange) throws IOException {
        LOGGER.info("We are in {}", exchange.getRequestURI().getPath());
        return;
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        byte[] bodyBytes = "Route not found".getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(404, bodyBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bodyBytes);
        }
    }
}
