package ro.alexmamo.firestore_document;

public interface Constants {
    String TAG = "FirestoreDocumentTag";
    String REGEX = "/";
    int ADDITIONAL_BYTE = 1;
    int DOCUMENT_NAME_ADDITIONAL_BYTES = 16;
    int DOCUMENT_CONTENT_ADDITIONAL_BYTES = 32;
    int NULL_SIZE = 1;
    int NUMBER_SIZE = 8;
    int BOOLEAN_SIZE = 1;
    int TIMESTAMP_SIZE = 8;
    int GEO_POINT_SIZE = 16;
    int ONE_MEBIBYTE = 1_048_576;
    int ONE_KILOBYTE = 1_024;
}