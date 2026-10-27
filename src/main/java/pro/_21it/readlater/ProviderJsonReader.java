package pro._21it.readlater;

import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.JacksonException;

public final class ProviderJsonReader {
    private final static JsonMapper mapper = JsonMapper.builder()
            .build();

    public static JsonMapper getMapper() {
        return mapper;
    }

    public static <T> T read(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JacksonException e) {
            throw new IllegalStateException("Provider JSON is not compatible with DTO: " + type.getSimpleName(),e);
        }
    }
}
