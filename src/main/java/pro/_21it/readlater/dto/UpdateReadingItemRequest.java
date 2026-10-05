package pro._21it.readlater.dto;

public record UpdateReadingItemRequest(
    String title,
    String author,
    ReadingStatus status,
    String externalId,
    String comment
) {};
