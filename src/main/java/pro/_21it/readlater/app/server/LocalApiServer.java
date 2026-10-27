package pro._21it.readlater.app.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import pro._21it.readlater.config.ServerConfig;

public class LocalApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalApiServer.class);

    public HttpServer start(ServerConfig serverConfig) throws IOException {
        InetSocketAddress address = new InetSocketAddress(serverConfig.host(), serverConfig.port());

        HttpServer server = HttpServer.create(address, 0);
        server.start();

        // Регистрируем endpoint /health и прокидываем config в обработчик
        server.createContext("/", new ReadingListHandler());

        LOGGER.info("Local API started: http://{}:{}", serverConfig.host(), serverConfig.port());
        LOGGER.info("Press Ctrl+C to stop the server.");
        LOGGER.info("Launch mode: SERVER");

        return server;
    }

}
