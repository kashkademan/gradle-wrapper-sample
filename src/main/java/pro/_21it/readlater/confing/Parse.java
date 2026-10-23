package pro._21it.readlater.config;

public class Parse {
    public static int intOrDefault(String raw, int defaultValue, String key) {
        // Пустое/отсутствующее значение — это сигнал использовать default.
        if (raw == null || raw.isBlank()) return defaultValue;

        try {
            // Парсим строго: если в конфиге «abc», лучше упасть сейчас, чем позже.
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid int for key " + key + ": " + raw);
        }
    }
}
