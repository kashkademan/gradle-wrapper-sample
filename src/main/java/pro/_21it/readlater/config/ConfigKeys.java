package pro._21it.readlater.config;

public final class ConfigKeys {
    // Константы для ключей: меньше опечаток, больше автодополнения от IDE.
    public static final String APP_NAME = "app.name";
    public static final String CATALOG_BASE_URL = "catalog.api.base-url";
    public static final String CATALOG_REQUEST_TIMEOUT_MS = "catalog.api.request-timeout-ms";
    public static final String CATALOG_MODE = "catalog.api.mode";
    public static final String SERVER_HOST = "server.host";
    public static final String SERVER_PORT = "server.port";

    private ConfigKeys() {
        // Запрещаем создавать экземпляры: это чисто утилитный класс.
    }
}
