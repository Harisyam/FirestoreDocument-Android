package ro.alexmamo.firestore_document.utils;

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
    int HALF_MEBIBYTE = 524288;
    int ONE_KILOBYTE = 1_024;
    String EQUAL_TO = "==";
    String GREATER_THAN = ">";
    String GREATER_THAN_OR_EQUAL_TO = ">=";
    String LESS_THAN = "<";
    String LESS_THAN_OR_EQUAL_TO = "<=";
}