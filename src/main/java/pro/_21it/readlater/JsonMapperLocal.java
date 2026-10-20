package pro._21it.readlater;

import tools.jackson.databind.ObjectMapper;

public final class JsonMapperLocal {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private JsonMapperLocal() {
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
