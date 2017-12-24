package core.repository;

import java.util.HashMap;
import java.util.Map;

public class ImageRepository {

    private Map<String, String> images;

    public ImageRepository() {
        images = new HashMap<>();
    }

    public void put(String key, String value) {
        images.put(key, value);
    }

    public String get(String key) {
        return images.get(key);
    }
}
