package pro._21it.readlater.config;

import java.net.URI;
import java.time.Duration;

public record CatalogConfig(
        URI baseUri,
        Duration requestTimeout,
        CatalogApiMode mode
) {}
