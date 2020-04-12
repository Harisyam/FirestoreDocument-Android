package ro.alexmamo.firestore_document.values;

import static ro.alexmamo.firestore_document.utils.Constants.ADDITIONAL_BYTE;

public class StringValue {
    public int getSize(String value) {
        return value.length() + ADDITIONAL_BYTE;
    }
}