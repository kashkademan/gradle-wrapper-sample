package pro._21it.readlater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;
import pro._21it.readlater.config.AppConfig;
import pro._21it.readlater.config.AppConfigFactory;
import pro._21it.readlater.config.PropertiesLoader;

public final class ReadLaterApplication {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        AppConfig config = AppConfigFactory.from(new PropertiesLoader().load());

        logger.info("Readlater Starter is running");
        logger.info("The base-url: {}", config.catalog().baseUri());
    }
}
