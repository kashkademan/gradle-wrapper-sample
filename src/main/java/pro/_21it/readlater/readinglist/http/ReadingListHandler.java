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
            status = ReadingListRouter.route(exchange, service);
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

}
