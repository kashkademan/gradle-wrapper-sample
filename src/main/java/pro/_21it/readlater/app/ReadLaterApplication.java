package pro._21it.readlater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro._21it.readlater.config.AppConfig;
import pro._21it.readlater.config.AppConfigFactory;
import pro._21it.readlater.config.PropertiesLoader;

public final class ReadLaterApplication {
    private static final Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);

    public static void main(String[] args) {
        AppConfig config = AppConfigFactory.from(new PropertiesLoader().load(), System.getenv());
        
        LaunchCommand cmd;
        try {
            cmd = LaunchCommandParser.parse(args);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return;
        }

        logger.info("Readlater Starter is running");

        ReadLaterRunner runner = new ReadLaterRunner(config);
        runner.run(cmd);
    }
}
