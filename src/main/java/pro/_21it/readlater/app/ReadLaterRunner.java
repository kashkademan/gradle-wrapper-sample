package pro._21it.readlater.app;

import pro._21it.readlater.config.AppConfig;

public class ReadLaterRunner {
    private final AppConfig config;

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
        printToConsole(command.summary());    
    };
    private void searchCatalog(LaunchCommand command) {
        printToConsole(command.summary());    
    };
    private void showDetails(LaunchCommand command) {
        printToConsole(command.summary());    
    };

    private void printToConsole(String message) {
        System.out.println(message);
    }
}
