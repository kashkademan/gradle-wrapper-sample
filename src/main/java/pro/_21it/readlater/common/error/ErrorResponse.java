package pro._21it.readlater.common.error;

import java.util.List;

// DTO для единого формата ошибок в API.
// Важно: это именно "данные", без логики и без сеттеров.
public record ErrorResponse(
        // Стабильный код ошибки: по нему клиент может делать switch/if.
        String errorCode,
        // Короткое сообщение для человека (Postman/UI), без stack trace.
        String message,
        // Дополнительные уточнения. Всегда список (даже если пусто).
        List<String> details
) {}
