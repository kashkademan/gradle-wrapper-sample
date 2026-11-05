package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public final class Helper {

    public Helper() {
    }

    public static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    public static Map<String, String> parseQuery(String query) {
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

    public static boolean isJsonContentType(HttpExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        // null — нормальный вариант для запросов без body
        return contentType != null && contentType.startsWith("application/json");
    }

    public static void requireJsonContentType(HttpExchange exchange) {
        // Проверка нужна только там, где реально ожидается JSON-тело
        if (!isJsonContentType(exchange)) {
            throw new IllegalArgumentException("Content-Type должен быть application/json");
        }
    }

    public static String normalizedPath(String path) {
        return (path.endsWith("/") && path.length() > 1) ? path.substring(0, path.length() - 1) : path;
    }

    public static long validateAndReturnId(String path) {
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

        return id;
    }
}
