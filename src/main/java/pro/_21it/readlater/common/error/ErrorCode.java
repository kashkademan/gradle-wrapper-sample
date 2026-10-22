package pro._21it.readlater.common.error;

// Перечень стабильных кодов ошибок.
// Эти значения — часть API-контракта, менять их "по настроению" нельзя.
public enum ErrorCode {
    INVALID_REQUEST,
    READING_ITEM_NOT_FOUND,
    EXTERNAL_ID_CONFLICT,
    METHOD_NOT_ALLOWED,
    INTERNAL_ERROR
}
