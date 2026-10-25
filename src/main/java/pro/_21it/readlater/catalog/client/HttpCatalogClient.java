package pro._21it.readlater.catalog.client;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCatalogClient {
    private static final Logger log = LoggerFactory.getLogger(HttpCatalogClient.class);

    public void call(URI uri) {
        // DEBUG: транспортные детали обычно нужны только при расследовании
        log.debug("Catalog HTTP request prepared, method=GET uri={}", uri);

        // ... send request ...

        // DEBUG: фиксируем статус и длительность (это самые полезные метрики при проблемах с сетью)
        log.debug("Catalog HTTP response received, method=GET uri={} statusCode={} durationMs={}", uri, 200, 137);
    }
}
