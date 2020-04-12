package ro.alexmamo.firestore_document.values;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class ArrayValue {
    public int getSize(List list) {
        int arrayValueSize = 0;
        for (Object obj : list) {
            if (obj == null) {
                arrayValueSize += new NullValue().getSize();
            } else if (obj instanceof String) {
                String s = (String) obj;
                arrayValueSize += new StringValue().getSize(s);
            } else if (obj instanceof Long || obj instanceof Double) {
                arrayValueSize += new NumberValue().getSize();
            } else if (obj instanceof Boolean){
                arrayValueSize += new BooleanValue().getSize();
            } else if (obj instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) obj;
                arrayValueSize += new MapValue().getSize(map);
            } else if (obj instanceof Timestamp) {
                arrayValueSize += new TimestampValue().getSize();
            } else if (obj instanceof GeoPoint) {
                arrayValueSize += new GeoPointValue().getSize();
            } else if (obj instanceof DocumentReference) {
                String documentPath = ((DocumentReference) obj).getPath();
                arrayValueSize += new DocumentReferenceValue().getSize(documentPath);
            }
        }
        return arrayValueSize;
    }
}