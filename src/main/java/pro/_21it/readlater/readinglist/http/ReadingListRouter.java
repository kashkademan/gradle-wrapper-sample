package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import pro._21it.readlater.readinglist.service.ReadingListService;
import pro._21it.readlater.common.error.ErrorResponses;

public final class ReadingListRouter {

    private ReadingListRouter() {
    }

    public static int route(HttpExchange exchange, ReadingListService service) throws IOException {
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
