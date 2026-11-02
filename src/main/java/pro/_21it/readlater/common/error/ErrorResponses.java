package pro._21it.readlater.common.error;

import java.util.List;

// Небольшой helper поверх канонических ErrorResponse + ErrorCode.
// Цель — убрать копипасту, а не придумать новую "систему ошибок".
public final class ErrorResponses {
    private ErrorResponses() {
        // Запрещаем создавать экземпляры: используем только static-методы.
    }

    public static ErrorResponse invalidId(String rawId) {
        return new ErrorResponse(
                ErrorCode.INVALID_ID.name(),
                "Id must be a positive number",
                List.of(rawId)
        );
    }
    
    public static ErrorResponse itemNotFound(String rawId) {
        return new ErrorResponse(
                ErrorCode.ITEM_NOT_FOUND.name(),
                "Reading list item not found",
                List.of(rawId)
        );
    }
    
    public static ErrorResponse invalidStatus(String rawId) {
        return new ErrorResponse(
                ErrorCode.INVALID_STATUS.name(),
                "Uknown stauts. Allowed: PLANNED, IN_PROGRES, FINISHED.",
                List.of(rawId)
        );
    }
    
    public static ErrorResponse routeNotFound(String rawId) {
        return new ErrorResponse(
                ErrorCode.ROUTE_NOT_FOUND.name(),
                "TOute not found",
                List.of(rawId)
        );
    }
    
    public static ErrorResponse internalError(String rawId) {
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
