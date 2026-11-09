package pro._21it.readlater.readinglist.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pro._21it.readlater.readinglist.dto.UpdateStatusRequest;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.ProviderJsonReader;
import pro._21it.readlater.readinglist.service.ReadingListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PatchReadingListHandler {
    private final static Logger logger = LoggerFactory.getLogger(PatchReadingListHandler.class);
    // Роут ровно под PATCH статуса: только число в {id} и строгое окончание /status
    private static final Pattern STATUS_PATCH_ROUTE = Pattern.compile("^/api/v1/reading-list/(\\d+)/status$");

    // Общая форма маршрута: один сегмент на месте {id}, даже если он кривой
    private static final Pattern STATUS_PATCH_SHAPE = Pattern.compile("^/api/v1/reading-list/([^/]+)/status$");

    private PatchReadingListHandler() {
    }

    public static int handleRequest(HttpExchange exchange, ReadingListService service) throws IOException {
        String path = exchange.getRequestURI().getPath();
        
        OptionalLong idOpt = tryExtractIdForStatusPatch(path);
        if (idOpt.isEmpty()) {
            exchange.sendResponseHeaders(400, -1);
            exchange.close();
            return 400;
        }
        
        Helper.requireJsonContentType(exchange);
        String body = HttpBodyReader.readBody(exchange);
        if (body.isBlank()) {
            throw new IllegalArgumentException("The body is mandatory");
        }

        UpdateStatusRequest request = ProviderJsonReader.read(body, UpdateStatusRequest.class);
        ReadingItemResponse response = service.patchReadingListItem(idOpt.getAsLong(), request);
        HttpJsonSender.sendJson(exchange, 200, response);
        return 200;
    }

    private boolean isStatusPatchPath(String path) {
        return STATUS_PATCH_SHAPE.matcher(path).matches();
    }

    private static OptionalLong tryExtractIdForStatusPatch(String path) {
        Matcher m = STATUS_PATCH_ROUTE.matcher(path);
        if (!m.matches()) return OptionalLong.empty();

        return OptionalLong.of(Long.parseLong(m.group(1)));
    }
}

