package pro._21it.readlater.config;

import java.util.Properties;
import java.util.Collections;
import java.util.Map;

public class ConfigReader {
    private final Properties props;
    private final Map<String, String> env;

    public ConfigReader(Properties props) {
        this.props = props;
        this.env = Collections.emptyMap();
    }

    public ConfigReader(Properties props, Map<String, String> env) {
        this.props = props;
        this.env = env;
    }

    public String required(String key) {
        String prop = props.getProperty(key);
        if (prop == null || prop.isBlank()) throw new ConfigException("Missing value for key: " + key);

        return prop.trim();
    }

    public String required(String key, String envKey) {
        String value = choose(key, envKey, null);
        if (value == null) {
            String suffix = envKey == null ? "" : " (env: " + envKey + ")";
            throw new ConfigException("Missing key: " + key + suffix);
        }

        return value;
    }

    public String optional(String key, String defaultValue) {
        String prop = props.getProperty(key);
        if (prop == null || prop.isBlank()) return defaultValue;

        return prop.trim();
    }

    public String optional(String key, String envKey, String defaultValue) {
        String value = choose(key, envKey, defaultValue);
        return value == null ? defaultValue : value;
    }

    public String choose(String key, String envKey, String defaultValue) {
        if (envKey != null) {
            String envValue = env.get(envKey);
            if (envValue != null && !envValue.isBlank()) return envValue.trim();
        }

        String fileValue = props.getProperty(key);
        if (fileValue != null && !fileValue.isBlank()) return fileValue.trim();

        return defaultValue;
    }
}
