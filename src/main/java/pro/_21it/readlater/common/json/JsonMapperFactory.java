package pro._21it.readlater.common.json;

import tools.jackson.databind.json.JsonMapper;

public final class JsonMapperFactory {
    private JsonMapperFactory() {
    }

    public static JsonMapper create() {
        return JsonMapper.builder().build();
    }
}
