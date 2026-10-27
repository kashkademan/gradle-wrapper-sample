package pro._21it.readlater.config;

public record AppConfig(
        String appName,
        CatalogConfig catalog,
        ServerConfig server
) {}
