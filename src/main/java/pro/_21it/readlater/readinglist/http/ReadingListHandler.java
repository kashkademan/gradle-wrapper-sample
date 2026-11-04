package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.readinglist.service.ReadingListService;
import pro._21it.readlater.common.error.ErrorResponses;

final public class ReadingListHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(ReadingListHandler.class);
    private final ReadingListService service;

    public ReadingListHandler(ReadingListService service) {
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
        String path = Helper.normalizedPath(exchange.getRequestURI().getPath());
        
        // 2) Сначала самые точные совпадения
        if ("GET".equals(method) && "/health".equals(path)) {
            return HealthHandler.handleRequest(exchange);
        }

        if ("GET".equals(method) && "/api/v1/reading-list".equals(path)) {
            return ListHandler.handleRequest(exchange, service);
        }

        // 3) Потом более общий маршрут с переменным сегментом
        if ("GET".equals(method) && path.startsWith("/api/v1/reading-list/")) {
            return ByIdHandler.handleRequest(exchange, service);
        }

        // 4) Проверка пост маршрута
        if ("POST".equals(method) && path.startsWith("/api/v1/reading-list")) {
            return CreateReadingListHandler.handleRequest(exchange);
        }

        // 5) Ветка по умолчанию: ничего не подошло
        HttpJsonSender.sendJson(exchange, 404, ErrorResponses.routeNotFound(path));
        return 404;
    }

}
