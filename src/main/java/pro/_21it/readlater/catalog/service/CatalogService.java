package pro._21it.readlater.catalog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.catalog.clinet.CatalogClient;

public class CatalogService {
    // Логгер на класс: по нему удобно фильтровать логи в конфигурации
    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);
    private final CatalogClient client;

    public CatalogService(CatalogClient client) {
        // Зависимость на клиент (транспорт) внедряем извне, чтобы сервис отвечал за сценарий, а не за HTTP
        this.client = client;
    }

    public void search(String query) {
        // Важно логировать входные параметры (в разумных пределах), чтобы потом можно было воспроизвести ситуацию
        log.info("Catalog search started, query={}", query);

        // Здесь реальный вызов клиента и обработка результата
        var items = client.search(query);

        // Симметричное сообщение "закончили" помогает видеть длительность/границы операции в потоке логов
        log.info("Catalog search finished, query={}", query);
    }
}
