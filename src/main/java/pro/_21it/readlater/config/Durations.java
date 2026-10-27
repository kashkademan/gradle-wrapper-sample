package pro._21it.readlater.config;

import java.time.Duration;

public class Durations {
    public static Duration msOrDefault(String raw, long defaultMs, String key) {
        // Сначала читаем число (миллисекунды) с дефолтом...
        int ms = Parse.intOrDefault(raw, (int) defaultMs, key);

        // ...потом валидируем, чтобы не получить «минус-таймаут».
        if (ms < 0) throw new ConfigException(key + " must be >= 0, got: " + ms);

        // И только здесь превращаем миллисекунды в Duration.
        return Duration.ofMillis(ms);
    }
}
