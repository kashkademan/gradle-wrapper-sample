package pro._21it.readlater.config;

import java.util.Properties;

public class ConfigReader {
    private final Properties props;

    public ConfigReader(Properties props) {
        this.props = props;
    }

    public String required(String key) {
        String prop = props.getProperty(key);
        if (prop == null || prop.isBlank()) throw new ConfigException("Missing value for key: " + key);

        return prop.trim();
    }

    public String optional(String key, String defaultValue) {
        String prop = props.getProperty(key);
        if (prop == null || prop.isBlank()) return defaultValue;

        return prop.trim();
    }
}
