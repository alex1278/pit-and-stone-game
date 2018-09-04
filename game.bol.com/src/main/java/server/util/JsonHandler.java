package server.util;

import com.google.gson.Gson;

public abstract class JsonHandler {

    private final Gson gson;

    public JsonHandler() {
        this.gson = new Gson();
    }

    protected String serialize(Object object) {
        return gson.toJson(object);
    }

    protected <T> T deserialize(String string, Class<T> object) {
        return gson.fromJson(string, object);
    }
}
