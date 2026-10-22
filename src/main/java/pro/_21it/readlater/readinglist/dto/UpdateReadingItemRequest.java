package pro._21it.readlater.readinglist.dto;

import pro._21it.readlater.readinglist.domain.ReadingStatus;

public record UpdateReadingItemRequest(
        // Полная замена title: клиент присылает итоговое значение
        String title,
        // Полная замена author
        String author,
        // Полная замена status (даже если меняем только его — лучше иметь отдельный PATCH DTO)
        ReadingStatus status,
        // Полная замена externalId (может быть null)
        String externalId,
        // Полная замена comment (может быть null)
        String comment
) {
    // Важно: id здесь тоже нет — он приходит из path: /api/v1/reading-list/{id}
}
