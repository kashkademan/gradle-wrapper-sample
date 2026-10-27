package pro._21it.readlater.config;

import java.util.Properties;
import java.util.Collections;
import java.util.Map;

public class AppConfigFactory {
    public static AppConfig from(Properties props) {
        return from(props, Collections.emptyMap());
    }

    public static AppConfig from(Properties props, Map<String, String> env) {
        ConfigReader r = new ConfigReader(props, env);

        CatalogConfig catalog = new CatalogConfig(
                Uris.requiredUri(r.required(ConfigKeys.CATALOG_BASE_URL, "READLATER_CATALOG_API_BASE_URL"), ConfigKeys.CATALOG_BASE_URL),
                Durations.msOrDefault(
                    r.optional(ConfigKeys.CATALOG_REQUEST_TIMEOUT_MS, "READLATER_CATALOG_API_REQUEST_TIMEOUT_MS", "2000"),
                    2000,
                    ConfigKeys.CATALOG_REQUEST_TIMEOUT_MS
                ),
                Modes.parse(
                    r.optional(ConfigKeys.CATALOG_MODE, "READLATER_CATALOG_API_MODE", "mock"),
                    CatalogApiMode.MOCK,
                    ConfigKeys.CATALOG_MODE
                )
        );

        ServerConfig server = new ServerConfig(
                r.optional(ConfigKeys.SERVER_HOST, "READLATER_SERVER_HOST", "localhost"),
                Parse.intOrDefault(
                    r.optional(ConfigKeys.SERVER_PORT, "READLATER_SERVER_PORT", "8080"),
                    8080,
                    ConfigKeys.SERVER_PORT
                )
        );

        return new AppConfig(
                r.optional(ConfigKeys.APP_NAME, "READLATER_APP_NAME", "readlater"),
                catalog,
                server
        );
    }
}
