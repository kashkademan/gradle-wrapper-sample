package pro._21it.readlater;

import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.JacksonException;

public class ProviderJsonReader {
    private final JsonMapper mapper = JsonMapper.builder()
            .build();

    public <T> T read(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JacksonException e) {
            throw new IllegalStateException("Provider JSON is not compatible with DTO: " + type.getSimpleName(),e);
        }
    }
}
