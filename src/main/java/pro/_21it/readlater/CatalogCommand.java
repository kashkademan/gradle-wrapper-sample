package pro._21it.readlater;

public record CatalogCommand(
        CatalogCommandType type,
        String value
) {};
