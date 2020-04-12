package ro.alexmamo.firestore_document.values;

import ro.alexmamo.firestore_document.DocumentName;

public class DocumentReferenceValue {
    public int getSize(String documentPath) {
        return new DocumentName().getSize(documentPath);
    }
}