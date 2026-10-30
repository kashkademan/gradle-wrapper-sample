package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.net.URI;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import tools.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.ProviderJsonReader;
import pro._21it.readlater.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.service.ReadingListService;
import pro._21it.readlater.readinglist.service.ReadingListServiceInMemoryImpl;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;

final public class ReadingListHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(ReadingListHandler.class);
    private final JsonMapper JSON_MAPPER = ProviderJsonReader.getMapper();
    private final ReadingListService service = new ReadingListServiceInMemoryImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       logger.info("{} {}", exchange.getRequestMethod(), exchange.getRequestURI().getPath());
        try {
            route(exchange);
        } catch (IllegalArgumentException e) {
            logger.warn("Bad request {}", e.getMessage(), e);
        }
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

        // 4) Проверка пост маршрута
        if ("POST".equals(method) && path.startsWith("/api/v1/reading-list")) {
            handleCreateReadingList(exchange);
            return;
        }

        // 5) Ветка по умолчанию: ничего не подошло
        sendNotFound(exchange);
    }

    private void handleHealth(HttpExchange exchange) throws IOException {
        HealthResponse response = new HealthResponse("UP", "readlater-starter");
        String jsonBody = JSON_MAPPER.writeValueAsString(response);
        writeResponse(exchange, 200, jsonBody);
    }

    private void handleList(HttpExchange exchange) throws IOException {
        logger.info("We are in {}", exchange.getRequestURI().getPath());
        exchange.close();
        return;
    }

    private void handleGetByIdRoute(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        Map<String, String> queryParams = parseQuery(uri.getRawQuery());
        String status = queryParams.get("status");
        String title = queryParams.get("title");

        String path = exchange.getRequestURI().getPath();
        long id = 0L;

        List<String> pathSegments = Arrays.stream(path.split("/"))
            .filter(s -> !s.isBlank())
            .toList();

        if (pathSegments.isEmpty()) {
            throw new IllegalArgumentException("The path is empty");
        }

        String lastSegment = pathSegments.get(pathSegments.size() - 1);
        try {
            id = Long.parseLong(lastSegment);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id must be a number");
        }

        logger.info("Processing ID is {}", id);
        ReadingItemResponse response = service.findById(id);
        String jsonBody = JSON_MAPPER.writeValueAsString(response);

        writeResponse(exchange, 200, jsonBody);
    }

    private void handleCreateReadingList(HttpExchange exchange) throws IOException {
        logger.info("We are in {}", exchange.getRequestURI().getPath());

        requireJsonContentType(exchange);
        String body = readBody(exchange);
        if (body.isBlank()) {
            throw new IllegalArgumentException("The body is mandatory");
        }
        CreateReadingItemRequest request = ProviderJsonReader.read(body, CreateReadingItemRequest.class);
        CreatedResponse response = new CreatedResponse(request.toString());
        String jsonBody = JSON_MAPPER.writeValueAsString(response);
        writeResponse(exchange, 201, jsonBody);
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        NotFoundResponse response = new NotFoundResponse("The path is not found");
        String jsonBody = JSON_MAPPER.writeValueAsString(response);
        writeResponse(exchange, 404, jsonBody);
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();

        if (query == null || query.isBlank()) return result;

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = decode(parts[0]);
            String value = parts.length == 2 ? decode(parts[1]) : "";

            result.put(key, value);
        }

        return result;
    }

    private boolean isJsonContentType(HttpExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        // null — нормальный вариант для запросов без body
        return contentType != null && contentType.startsWith("application/json");
    }

    private void requireJsonContentType(HttpExchange exchange) {
        // Проверка нужна только там, где реально ожидается JSON-тело
        if (!isJsonContentType(exchange)) {
            throw new IllegalArgumentException("Content-Type должен быть application/json");
        }
    }

    private String readBody(HttpExchange exchange) throws IOException {
        try (InputStream stream = exchange.getRequestBody()) {
            byte[] bytes = stream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    private void writeResponse(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        // Явно указываем текстовый ответ в UTF-8, чтобы клиент корректно интерпретировал тело.
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        // Перед отправкой тела нужно сначала объявить статус и длину ответа.
        exchange.sendResponseHeaders(status, bytes.length);

        // Response body обязательно закрываем через try-with-resources.
        // Это гарантирует, что ответ будет корректно завершён и ресурсы освободятся.
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bytes);
        }
    }
}
