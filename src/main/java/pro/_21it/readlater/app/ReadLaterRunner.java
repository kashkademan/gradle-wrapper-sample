package pro._21it.readlater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.config.AppConfig;
import pro._21it.readlater.app.server.PingServerApplication;

public class ReadLaterRunner {
    private final AppConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ReadLaterRunner.class);

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
            PingServerApplication.startServer(config.server());
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
