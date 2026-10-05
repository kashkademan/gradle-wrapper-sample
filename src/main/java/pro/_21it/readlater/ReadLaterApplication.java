package pro._21it.readlater;

import pro._21it.readlater.dto.*;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class ReadLaterApplication {
    public static void main(String[] args) {
        ConsoleBanner.print();

        Logger logger = LoggerFactory.getLogger(ReadLaterApplication.class);
        logger.info("Readlater Starter is running");

        String json = new ObjectMapper().writeValueAsString(new Ping("UP"));
        System.out.println(json);

        System.out.println(searchResponse);
        System.out.println(detailedBook);
    }

    private static record Ping(String status) {
    };

    // DTOs check
    private static CatalogSearchItemResponse catalogSearchItemResponseFirst = new CatalogSearchItemResponse(
        "OML001X",
        "Three Body Problem",
        "Lu"
    );

    private static CatalogSearchItemResponse catalogSearchItemResponseSecond = new CatalogSearchItemResponse(
        "OML002X",
        "Dark Forest",
        "Lu"
    );

    private static CatalogBookDetailsResponse detailedBook = new CatalogBookDetailsResponse(
        "OML001X",
        "Tree Body Problem",
        "Lu",
        ""
    );

    private static CatalogSearchResponse searchResponse = new CatalogSearchResponse(
        List.of(
            catalogSearchItemResponseFirst,
            catalogSearchItemResponseSecond
        ),
        2
    );
}
