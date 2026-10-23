package pro._21it.readlater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;
import pro._21it.readlater.config.PropertiesLoader;

public final class ReadLaterApplication {
    private static final Properties PROPS = new PropertiesLoader().load();

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");
        logger.info("The base-url: {}", PROPS.getProperty("catalog.api.base-url"));
    }
}
