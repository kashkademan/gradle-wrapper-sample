package pro._21it.readlater.common.error;

import java.util.List;

public class ValidationException extends RuntimeException {
    private final List<String> details;

    public ValidationException(List<String> details) {
        this.details = details;
    }

    public List<String> details() {
        return details;
    }
}
