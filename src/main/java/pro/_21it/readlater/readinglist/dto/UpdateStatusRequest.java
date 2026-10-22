package pro._21it.readlater.readinglist.dto;

import pro._21it.readlater.readinglist.domain.ReadingStatus;

public record UpdateStatusRequest(
        // Меняем только статус и ничего больше
        ReadingStatus status
) {
    // Этот DTO хорошо подходит для PATCH /reading-list/{id}/status
}
