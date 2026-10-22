package pro._21it.readlater.readinglist.dto;

import pro._21it.readlater.readinglist.domain.ReadingStatus;

public record ReadingItemResponse(
        // Идентификатор ресурса, задаётся сервером
        long id,
        // Текущее значение title
        String title,
        // Текущее значение author
        String author,
        // Текущее значение status
        ReadingStatus status,
        // Может быть null, если внешний id не задан
        String externalId,
        // Может быть null, если комментария нет
        String comment
) {
    // Response DTO обычно стабилен: домен можно рефакторить, контракт — сохранять.
}
