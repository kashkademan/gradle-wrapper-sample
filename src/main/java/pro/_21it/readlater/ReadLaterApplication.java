package pro._21it.readlater;

import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadLaterApplication {
    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        String json = new ObjectMapper().writeValueAsString(new Ping("UP"));
        System.out.println(json);
    }

    private static record Ping(String status) {
    };

}
