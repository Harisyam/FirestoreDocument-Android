package ro.alexmamo.firestore_document.values;

import java.util.Map;

import ro.alexmamo.firestore_document.MapContent;

public class MapValue {
    public int getSize(Map<String, Object> map) {
        int mapSize = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            int entrySize = new MapContent().getEntrySize(entry);
            mapSize += entrySize;
        }
        return mapSize;
    }
}