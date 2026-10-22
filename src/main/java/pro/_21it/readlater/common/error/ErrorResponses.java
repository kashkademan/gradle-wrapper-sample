package pro._21it.readlater.common.error;

import java.util.List;

// Небольшой helper поверх канонических ErrorResponse + ErrorCode.
// Цель — убрать копипасту, а не придумать новую "систему ошибок".
public final class ErrorResponses {
    private ErrorResponses() {
        // Запрещаем создавать экземпляры: используем только static-методы.
    }

    public static ErrorResponse internalError() {
        return new ErrorResponse(
                ErrorCode.INTERNAL_ERROR.name(),
                "Внутренняя ошибка приложения",
                List.of()
        );
    }

    public static ErrorResponse invalidRequest(String message, String... details) {
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST.name(),
                message,
                List.of(details)
        );
    }
}
