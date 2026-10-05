package pro._21it.readlater.dto;

public record CatalogBookDetailsResponse (
    String externalId,
    String title,
    String author,
    String description
) {};
