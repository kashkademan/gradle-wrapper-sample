package pro._21it.readlater.config;

import java.net.URI;

public class Uris {
    public static URI requiredUri(String raw, String key) {
        try {
            // Парсим URI на старте, чтобы дальше в коде не возиться со строками.
            URI uri = URI.create(raw.trim());

            // Минимальная проверка: без схемы (http/https) это почти наверняка ошибка.
            if (uri.getScheme() == null) throw new ConfigException(key + " must have scheme");

            return uri;
        } catch (IllegalArgumentException e) {
            // Перехватываем стандартное исключение и заворачиваем в понятное конфиг-ошибку.
            throw new ConfigException("Invalid URI for key " + key + ": " + raw);
        }
    }
}
