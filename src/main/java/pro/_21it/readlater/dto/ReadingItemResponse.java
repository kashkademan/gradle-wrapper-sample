package pro._21it.readlater.dto;

public record ReadingItemResponse(
    long id,
    String title,
    String author,
    ReadingStatus status,
    String externalId,
    String comment
) {};
