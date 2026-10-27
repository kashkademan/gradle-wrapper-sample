package pro._21it.readlater.config;

public class Modes {
    public static CatalogApiMode parse(String raw, CatalogApiMode def, String key) {
        if (raw == null || raw.isBlank()) return def;

        return switch(raw.trim().toLowerCase()) {
            case "real" -> CatalogApiMode.REAL;
            case "mock" -> CatalogApiMode.MOCK;
            default -> throw new ConfigException("Invalid " + key + ": " + raw);
        };
    }
}
