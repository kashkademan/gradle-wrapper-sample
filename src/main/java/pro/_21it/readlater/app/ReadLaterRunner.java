package pro._21it.readlater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.databind.json.JsonMapper;
import pro._21it.readlater.config.AppConfig;
import pro._21it.readlater.app.server.LocalApiServer;
import pro._21it.readlater.ProviderJsonReader;
import pro._21it.readlater.readinglist.http.ReadingListHandler;
import pro._21it.readlater.readinglist.service.ReadingListService;
import pro._21it.readlater.readinglist.service.ReadingListServiceInMemoryImpl;

public class ReadLaterRunner {
    private final AppConfig config;
    private final Logger logger = LoggerFactory.getLogger(ReadLaterRunner.class);
    private final JsonMapper jsonMapper = ProviderJsonReader.getMapper();
    private final ReadingListService service = new ReadingListServiceInMemoryImpl();
    private final HttpHandler readingListHandler = new ReadingListHandler(jsonMapper, service);

    public ReadLaterRunner(AppConfig config) {
        this.config = config;
        // здесь собираем сервисы и контроллеры — то, что раньше делал ApplicationBootstrap
    }

    public void run(LaunchCommand cmd) {
        switch (cmd) {
            case ServerCommand s -> startServer(s);
            case CatalogSearchCommand c -> searchCatalog(c);
            case CatalogDetailsCommand c -> showDetails(c);
        }
    }

    private void startServer(LaunchCommand command) {
        logger.debug(command.summary());
        try {
            HttpServer localServer = new LocalApiServer().start(config.server(), readingListHandler);
        } catch (Exception e) {
            logger.error("The error while statring a server: {}", e.getMessage(), e);
        };
    };

    private void searchCatalog(LaunchCommand command) {
        logger.info(command.summary());
    };

    private void showDetails(LaunchCommand command) {
        logger.info(command.summary());
    };
}
