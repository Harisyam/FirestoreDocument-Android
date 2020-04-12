package ro.alexmamo.firestore_document;

import static ro.alexmamo.firestore_document.utils.Constants.ADDITIONAL_BYTE;
import static ro.alexmamo.firestore_document.utils.Constants.DOCUMENT_NAME_ADDITIONAL_BYTES;
import static ro.alexmamo.firestore_document.utils.Constants.REGEX;

public class DocumentName {
    public int getSize(String documentPath) {
        int documentNameSize = 0;
        String[] names = documentPath.split(REGEX);
        for (String name : names) {
            documentNameSize += name.length() + ADDITIONAL_BYTE;
        }
        return documentNameSize + DOCUMENT_NAME_ADDITIONAL_BYTES;
    }
}