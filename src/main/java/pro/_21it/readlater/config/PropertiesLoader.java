package pro._21it.readlater.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {

    // Загружаем application.properties из classpath и возвращаем Properties
    public Properties load() {
        Properties props = new Properties();

        // try-with-resources гарантирует закрытие InputStream
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

            // Явно проверяем отсутствие ресурса, чтобы ошибка была понятной
            if (input == null) {
                throw new IllegalStateException("application.properties not found");
            }

            // Загружаем пары ключ=значение
            props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            return props;

        } catch (Exception e) {
            // Оборачиваем в понятную ошибку старта приложения
            throw new IllegalStateException("Failed to load application.properties", e);
        }
    }
}
