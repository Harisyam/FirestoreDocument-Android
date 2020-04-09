package ro.alexmamo.firestore_document;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Map;

import static ro.alexmamo.firestore_document.Constants.ADDITIONAL_BYTE;
import static ro.alexmamo.firestore_document.Constants.BOOLEAN_SIZE;
import static ro.alexmamo.firestore_document.Constants.DOCUMENT_CONTENT_ADDITIONAL_BYTES;
import static ro.alexmamo.firestore_document.Constants.DOCUMENT_NAME_ADDITIONAL_BYTES;
import static ro.alexmamo.firestore_document.Constants.GEO_POINT_SIZE;
import static ro.alexmamo.firestore_document.Constants.NULL_SIZE;
import static ro.alexmamo.firestore_document.Constants.NUMBER_SIZE;
import static ro.alexmamo.firestore_document.Constants.REGEX;
import static ro.alexmamo.firestore_document.Constants.TAG;
import static ro.alexmamo.firestore_document.Constants.TIMESTAMP_SIZE;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class FirestoreDocument {
    private static FirestoreDocument instance;

    private FirestoreDocument() {}

    public synchronized static FirestoreDocument getInstance() {
        if (instance == null) {
            instance = new FirestoreDocument();
        }
        return instance;
    }

    public int getSize(DocumentSnapshot document) {
        int documentSize = 0;

        String documentPath = document.getReference().getPath();
        int documentNameSize = getDocumentNameSize(documentPath);
        documentSize += documentNameSize;
        Log.d(TAG, "documentNameSize: " + documentNameSize);

        Map<String, Object> data = document.getData();
        int documentContentSize = getDocumentContentSize(data);
        documentSize += documentContentSize;
        Log.d(TAG, "documentContentSize: " + documentContentSize);

        return documentSize;
    }

    private int getDocumentNameSize(String path) {
        int documentNameSize = 0;
        String[] names = path.split(REGEX);
        for (String name : names) {
            documentNameSize += name.length() + ADDITIONAL_BYTE;
        }
        return documentNameSize + DOCUMENT_NAME_ADDITIONAL_BYTES;
    }

    private int getDocumentContentSize(Map<String, Object> data) {
        int totalSize = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            int entrySize = getEntrySize(entry);
            totalSize += entrySize;
        }
        return totalSize + DOCUMENT_CONTENT_ADDITIONAL_BYTES;
    }

    private int getEntrySize(Map.Entry<String, Object> entry) {
        String entryName = entry.getKey();
        int entryNameSize = getEntryNameSize(entryName);

        Object entryValue = entry.getValue();
        int entryValueSize = getEntryValueSize(entryValue);

        return entryNameSize + entryValueSize;
    }

    private int getEntryNameSize(String propertyName) {
        return propertyName.length() + ADDITIONAL_BYTE;
    }

    private int getEntryValueSize(Object propertyValue) {
        if (propertyValue == null) {
            return getNullValueSize();
        }

        int propertyValueSize = 0;
        if (propertyValue instanceof String) {
            String value = (String) propertyValue;
            propertyValueSize = getStringValueSize(value);
        } else if (propertyValue instanceof Long || propertyValue instanceof Double) {
            propertyValueSize = getNumberValueSize();
        } else if (propertyValue instanceof Boolean){
            propertyValueSize = getBooleanValueSize();
        } else if (propertyValue instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) propertyValue;
            propertyValueSize = getMapValueSize(map);
        } else if (propertyValue instanceof List) {
            List list = ((List) propertyValue);
            propertyValueSize = getArrayValueSize(list);
        } else if (propertyValue instanceof Timestamp) {
            propertyValueSize = getTimestampValueSize();
        } else if (propertyValue instanceof GeoPoint) {
            propertyValueSize = getGeoPointValueSize();
        } else if (propertyValue instanceof DocumentReference) {
            String documentPath = ((DocumentReference) propertyValue).getPath();
            propertyValueSize = getDocumentReferenceValueSize(documentPath);
        }
        return propertyValueSize;
    }

    private int getNullValueSize() {
        return NULL_SIZE;
    }

    private int getStringValueSize(String value) {
        return value.length() + ADDITIONAL_BYTE;
    }

    private int getNumberValueSize() {
        return NUMBER_SIZE;
    }

    private int getBooleanValueSize() {
        return BOOLEAN_SIZE;
    }

    private int getMapValueSize(Map<String, Object> map) {
        int mapSize = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            int entrySize = getEntrySize(entry);
            mapSize += entrySize;
        }
        return mapSize;
    }

    private int getArrayValueSize(List list) {
        int arrayValueSize = 0;
        for (Object obj : list) {
            if (obj == null) {
                arrayValueSize += getNullValueSize();
            } else if (obj instanceof String) {
                String s = (String) obj;
                arrayValueSize += getStringValueSize(s);
            } else if (obj instanceof Long || obj instanceof Double) {
                arrayValueSize += getNumberValueSize();
            } else if (obj instanceof Boolean){
                arrayValueSize += getBooleanValueSize();
            } else if (obj instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) obj;
                arrayValueSize += getMapValueSize(map);
            } else if (obj instanceof Timestamp) {
                arrayValueSize += getTimestampValueSize();
            } else if (obj instanceof GeoPoint) {
                arrayValueSize += getGeoPointValueSize();
            } else if (obj instanceof DocumentReference) {
                String documentPath = ((DocumentReference) obj).getPath();
                arrayValueSize += getDocumentReferenceValueSize(documentPath);
            }
        }
        return arrayValueSize;
    }

    private int getTimestampValueSize() {
        return TIMESTAMP_SIZE;
    }

    private int getGeoPointValueSize() {
        return GEO_POINT_SIZE;
    }

    private int getDocumentReferenceValueSize(String documentPath) {
        return getDocumentNameSize(documentPath);
    }
}