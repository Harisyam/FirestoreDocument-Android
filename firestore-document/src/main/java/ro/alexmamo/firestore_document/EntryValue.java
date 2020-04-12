package ro.alexmamo.firestore_document;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Map;

import ro.alexmamo.firestore_document.values.ArrayValue;
import ro.alexmamo.firestore_document.values.BooleanValue;
import ro.alexmamo.firestore_document.values.DocumentReferenceValue;
import ro.alexmamo.firestore_document.values.GeoPointValue;
import ro.alexmamo.firestore_document.values.MapValue;
import ro.alexmamo.firestore_document.values.NullValue;
import ro.alexmamo.firestore_document.values.NumberValue;
import ro.alexmamo.firestore_document.values.StringValue;
import ro.alexmamo.firestore_document.values.TimestampValue;

@SuppressWarnings({"unchecked"})
class EntryValue {
    int getSize(Object obj)  {
        if (obj == null) {
            return new NullValue().getSize();
        }

        int propertyValueSize = 0;
        if (obj instanceof String) {
            String value = (String) obj;
            propertyValueSize = new StringValue().getSize(value);
        } else if (obj instanceof Long || obj instanceof Double) {
            propertyValueSize = new NumberValue().getSize();
        } else if (obj instanceof Boolean){
            propertyValueSize = new BooleanValue().getSize();
        } else if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            propertyValueSize = new MapValue().getSize(map);
        } else if (obj instanceof List) {
            List list = ((List) obj);
            propertyValueSize = new ArrayValue().getSize(list);
        } else if (obj instanceof Timestamp) {
            propertyValueSize = new TimestampValue().getSize();
        } else if (obj instanceof GeoPoint) {
            propertyValueSize = new GeoPointValue().getSize();
        } else if (obj instanceof DocumentReference) {
            String documentPath = ((DocumentReference) obj).getPath();
            propertyValueSize = new DocumentReferenceValue().getSize(documentPath);
        }
        return propertyValueSize;
    }
}