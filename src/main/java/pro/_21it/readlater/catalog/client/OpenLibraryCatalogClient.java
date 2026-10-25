package pro._21it.readlater.catalog.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenLibraryCatalogClient {
    private static final Logger log = LoggerFactory.getLogger(OpenLibraryCatalogClient.class);

    private final HttpClient httpClient;

    public OpenLibraryCatalogClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> call(URI uri) throws Exception {
        // Стартуем таймер до отправки запроса, чтобы видеть полную длительность внешнего вызова
        long startedAt = System.nanoTime();

        // Первый лог: фиксируем подготовленный transport-вызов
        log.debug("Catalog HTTP request prepared, method=GET uri={}", safeUri(uri));

        // Собираем запрос отдельно: так проще добавлять заголовки/таймауты при необходимости
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();

        // Здесь может случиться исключение (timeout/IOException), поэтому этот участок — ключевая граница
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Второй лог: фиксируем результат и длительность конкретного HTTP round-trip
        long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
        log.debug("Catalog HTTP response received, method=GET uri={} statusCode={} durationMs={}", safeUri(uri), response.statusCode(), durationMs);

        return response;
    }

    private String safeUri(URI uri) {
        // Убираем query-параметры: они часто содержат пользовательский ввод или секреты
        return uri.getScheme() + "://" + uri.getHost() + uri.getPath();
    }
}
