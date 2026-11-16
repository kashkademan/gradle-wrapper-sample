package pro._21it.readlater.readinglist.validator;

import java.util.List;
import java.util.ArrayList;
import pro._21it.readlater.readinglist.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.UpdateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.UpdateStatusRequest;
import pro._21it.readlater.readinglist.domain.ReadingStatus;

public class ReadingListValidator {
    boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    List<String> validateForCreate(CreateReadingItemRequest request) {
        List<String> details = new ArrayList<>();

        // Обязательные поля для create (POST): title, author, status
        if (isBlank(request.title())) details.add("Поле title обязательно");
        if (isBlank(request.author())) details.add("Поле author обязательно");
        if (isBlank(request.status())) details.add("Поле status обязательно");
        if (request.externalId() != null && request.externalId().isBlank()) {
            details.add("Если externalId указан, он не должен быть пустым");
        }
        // Сначала убеждаемся, что статус вообще задан (иначе сообщение будет про обязательность поля)
        if (!isBlank(request.status()) && !isValidStatus(request.status())) {
            // Если статус задан, но не входит в enum, возвращаем понятное перечисление допустимых значений
            details.add("Поле status должно быть одним из: PLANNED, IN_PROGRESS, FINISHED");
        }

        return details;
    }

    List<String> validateForUpdate(UpdateReadingItemRequest request) {
        List<String> details = new ArrayList<>();

        if (isBlank(request.title())) details.add("Поле title обязательно");
        if (isBlank(request.author())) details.add("Поле author обязательно");
        if (isBlank(request.status())) details.add("Поле status обязательно");
        if (request.externalId() != null && request.externalId().isBlank()) {
            details.add("Если externalId указан, он не должен быть пустым");
        }
        // Сначала убеждаемся, что статус вообще задан (иначе сообщение будет про обязательность поля)
        if (!isBlank(request.status()) && !isValidStatus(request.status())) {
            // Если статус задан, но не входит в enum, возвращаем понятное перечисление допустимых значений
            details.add("Поле status должно быть одним из: PLANNED, IN_PROGRESS, FINISHED");
        }

        return details;
    }

    List<String> validateForStatusPatch(UpdateStatusRequest request) {
        List<String> details = new ArrayList<>();

        if (isBlank(request.status())) details.add("Поле status обязательно");
        // Сначала убеждаемся, что статус вообще задан (иначе сообщение будет про обязательность поля)
        if (!isBlank(request.status()) && !isValidStatus(request.status())) {
            // Если статус задан, но не входит в enum, возвращаем понятное перечисление допустимых значений
            details.add("Поле status должно быть одним из: PLANNED, IN_PROGRESS, FINISHED");
        }

        return details;
    }

    boolean isValidStatus(String rawStatus) {
        // Пустые значения сразу считаем невалидными: это либо "нет статуса", либо "пустышка"
        if (rawStatus == null || rawStatus.isBlank()) return false;

        try {
            ReadingStatus.valueOf(rawStatus);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
