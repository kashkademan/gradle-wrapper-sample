package pro._21it.readlater.common.error;

import java.util.List;

public class ErrorMapper {
    public int statusFor(Exception e) {
        if (e instanceof ValidationException) return 400;
        if (e instanceof NotFoundException) return 404;
        if (e instanceof ConflictException) return 409;

        return 500;
    }

    public ErrorResponse bodyFor(Exception ex) {
        // Валидация: отдаём список всех проблем, чтобы клиент исправил всё за один раз.
        if (ex instanceof ValidationException vex) {
            return new ErrorResponse("VALIDATION_ERROR", "Запрос не прошел проверку", vex.details());
        }

        // 404/409: подробности оставляем в логах, клиенту — стабильный контракт.
        if (ex instanceof NotFoundException) {
            return new ErrorResponse("NOT_FOUND", "Ресурс не найден", List.of());
        }
        if (ex instanceof ConflictException) {
            return new ErrorResponse("CONFLICT", "Конфликт данных", List.of());
        }

        // 500: максимально «скучный» ответ без внутренних деталей.
        return new ErrorResponse("INTERNAL_ERROR", "Внутренняя ошибка", List.of());
    }
}
