package ro.alexmamo.firestore_document;

import java.util.Map;

import static ro.alexmamo.firestore_document.utils.Constants.DOCUMENT_CONTENT_ADDITIONAL_BYTES;

public class MapContent {
    public int getSize(Map<String, Object> data) {
        int totalSize = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            int entrySize = getEntrySize(entry);
            totalSize += entrySize;
        }
        return totalSize + DOCUMENT_CONTENT_ADDITIONAL_BYTES;
    }

    public int getEntrySize(Map.Entry<String, Object> entry) {
        int entryKeySize = getEntryKeySize(entry);
        int entryValueSize = getEntryValueSize(entry);
        return entryKeySize + entryValueSize;
    }

    private int getEntryKeySize(Map.Entry<String, Object> entry) {
        String entryKey = entry.getKey();
        EntryName entryName = new EntryName();
        return entryName.getSize(entryKey);
    }

    private int getEntryValueSize(Map.Entry<String, Object> entry) {
        Object obj = entry.getValue();
        EntryValue entryValue = new EntryValue();
        return entryValue.getSize(obj);
    }
}