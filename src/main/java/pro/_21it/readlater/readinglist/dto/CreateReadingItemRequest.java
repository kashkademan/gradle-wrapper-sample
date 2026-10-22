package pro._21it.readlater.readinglist.dto;

import pro._21it.readlater.readinglist.domain.ReadingStatus;

public record CreateReadingItemRequest(
        // Название книги/статьи, которое вводит пользователь
        String title,
        // Автор в свободной форме (для учебного проекта достаточно строки)
        String author,
        // Статус чтения в момент создания (например, PLANNED)
        ReadingStatus status,
        // Внешний идентификатор (может отсутствовать)
        String externalId,
        // Комментарий пользователя (может отсутствовать)
        String comment
) {
    // Важно: id здесь нет — при создании он обычно генерируется на сервере.
}
