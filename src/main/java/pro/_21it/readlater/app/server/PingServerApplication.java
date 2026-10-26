package pro._21it.readlater.app.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import pro._21it.readlater.config.ServerConfig;

public class PingServerApplication {
    private static final Logger LOGGER = Logger.getLogger(PingServerApplication.class.getName());

    public static void startServer(ServerConfig serverConfig) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(serverConfig.host(), serverConfig.port()), 0);
        server.createContext("/health", PingServerApplication::handlePing);
        server.createContext("/info", PingServerApplication::handleInfo);
        server.start();
        
        LOGGER.info(() -> "Server started, host: " + serverConfig.host() + ", port: " + serverConfig.port());
    }

    public static void handlePing(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        LOGGER.info(() -> method + " " + path);

        byte[] body = "pong".getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Context-Type", "text/plain; charset=utf-8");

        exchange.sendResponseHeaders(200, body.length);

        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(body);
        }
    }

    public static void handleInfo(HttpExchange exchange) throws IOException {
        // Метод и путь берём из exchange: именно он содержит входящий запрос.
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        // Логируем оба значения, чтобы сразу видеть, какой запрос пришёл.
        LOGGER.info("method=" + method + ", path=" + path);

        byte[] body = "INFO OK".getBytes(StandardCharsets.UTF_8);

        // Заголовок ответа нужно выставить до отправки тела.
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(200, body.length);

        // Поток ответа обязательно закрываем, чтобы корректно завершить обмен.
        try (var output = exchange.getResponseBody()) {
            output.write(body);
        }
    }
}
