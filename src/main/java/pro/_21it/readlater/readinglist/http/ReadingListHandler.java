package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
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
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.common.error.ErrorResponse;
import pro._21it.readlater.common.error.ErrorResponses;

final public class ReadingListHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(ReadingListHandler.class);
    private final JsonMapper jsonMapper;
    private final ReadingListService service;

    public ReadingListHandler(JsonMapper jsonMapper, ReadingListService service) {
        this.jsonMapper = jsonMapper;
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long startedAt = System.nanoTime();
        int status = 0;

        try {
            status = route(exchange);
        } catch (IllegalArgumentException e) {
            logger.warn("Bad request {}", e.getMessage(), e);
        }

        long tookMs = (System.nanoTime() - startedAt) / 1_000_000;

        logger.info(
                "method={}, path={}, status={} tookMs={}", 
                exchange.getRequestMethod(), 
                exchange.getRequestURI().getPath(), 
                status,
                tookMs
        );
    }

    private int route(HttpExchange exchange) throws IOException {
         // 1) Достаём данные, по которым будем маршрутизировать
        String method = exchange.getRequestMethod();
        String path = normalizedPath(exchange.getRequestURI().getPath());
        
        // 2) Сначала самые точные совпадения
        if ("GET".equals(method) && "/health".equals(path)) {
            return HealthHandler.handleRequest(exchange);
        }

        if ("GET".equals(method) && "/api/v1/reading-list".equals(path)) {
            return ListHandler.handleRequest(exchange, service);
        }

        // 3) Потом более общий маршрут с переменным сегментом
        if ("GET".equals(method) && path.startsWith("/api/v1/reading-list/")) {
            return handleGetByIdRoute(exchange);
        }

        // 4) Проверка пост маршрута
        if ("POST".equals(method) && path.startsWith("/api/v1/reading-list")) {
            return handleCreateReadingList(exchange);
        }

        // 5) Ветка по умолчанию: ничего не подошло
        HttpJsonSender.sendJson(exchange, 404, ErrorResponses.routeNotFound(path));
        return 404;
    }

    private int handleGetByIdRoute(HttpExchange exchange) throws IOException {
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
            ErrorResponse response = 
                    new ErrorResponse(
                        "NOT_ALLOWED",
                        "not allowed empty witout id",
                        List.of()
                    );
            HttpJsonSender.sendJson(exchange, 400, response);
            throw new IllegalArgumentException("The path is empty");
        }

        String lastSegment = pathSegments.get(pathSegments.size() - 1);
        try {
            id = Long.parseLong(lastSegment);
        } catch (NumberFormatException e) {
            ErrorResponse response = 
                    new ErrorResponse(
                        "NOT_NUMBER",
                        "should be a nubmer",
                        List.of()
                    );
            HttpJsonSender.sendJson(exchange, 400, response);
            throw new IllegalArgumentException("id must be a number");
        }

        logger.info("Processing ID is {}", id);
        ReadingItemResponse response = service.findById(id);
        if (response == null) {
            NotFoundResponse errorResponse = new NotFoundResponse("Item with " + id + " is not found");
            HttpJsonSender.sendJson(exchange, 404, errorResponse);
            return 404;
        } else {
            HttpJsonSender.sendJson(exchange, 200, response);
            return 200;
        }
    }

    private int handleCreateReadingList(HttpExchange exchange) throws IOException {
        requireJsonContentType(exchange);
        String body = HttpBodyReader.readBody(exchange);
        if (body.isBlank()) {
            throw new IllegalArgumentException("The body is mandatory");
        }
        CreateReadingItemRequest request = ProviderJsonReader.read(body, CreateReadingItemRequest.class);
        CreatedResponse response = new CreatedResponse(request.toString());
        HttpJsonSender.sendJson(exchange, 201, response);
        return 201;
    }

    private int sendNotFound(HttpExchange exchange) throws IOException {
        NotFoundResponse response = new NotFoundResponse("The path is not found");
        HttpJsonSender.sendJson(exchange, 404, response);
        return 404;
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

    private String normalizedPath(String path) {
        return (path.endsWith("/") && path.length() > 1) ? path.substring(0, path.length() - 1) : path;
    }

}
