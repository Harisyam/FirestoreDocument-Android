package ro.alexmamo.firestore_document;

import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import static ro.alexmamo.firestore_document.utils.Constants.*;
import static ro.alexmamo.firestore_document.utils.Constants.EQUAL_TO;
import static ro.alexmamo.firestore_document.utils.Constants.GREATER_THAN;
import static ro.alexmamo.firestore_document.utils.Constants.GREATER_THAN_OR_EQUAL_TO;
import static ro.alexmamo.firestore_document.utils.Constants.LESS_THAN;
import static ro.alexmamo.firestore_document.utils.Constants.LESS_THAN_OR_EQUAL_TO;
import static ro.alexmamo.firestore_document.utils.Constants.ONE_KILOBYTE;
import static ro.alexmamo.firestore_document.utils.Constants.ONE_MEBIBYTE;
import static ro.alexmamo.firestore_document.utils.Messages.HALF_SIZE_MESSAGE;

@SuppressWarnings({"ConstantConditions"})
public class FirestoreDocument{
    private static FirestoreDocument instance;

    private FirestoreDocument() {}

    public synchronized static FirestoreDocument getInstance() {
        if (instance == null) {
            instance = new FirestoreDocument();
        }
        return instance;
    }

    public int getSize(DocumentSnapshot document) {
        int documentNameSize = getDocumentNameSize(document);
        int mapContentSize = getMapContentSize(document);
        displayWarningMessage(documentNameSize + mapContentSize);
        return documentNameSize + mapContentSize;
    }

    private int getDocumentNameSize(DocumentSnapshot document) {
        String documentPath = document.getReference().getPath();
        DocumentName documentName = new DocumentName();
        return documentName.getSize(documentPath);
    }

    private int getMapContentSize(DocumentSnapshot document) {
        Map<String, Object> data = document.getData();
        MapContent mapContent = new MapContent();
        return mapContent.getSize(data);
    }

    /**
     * @param docSize The document size to check against the maximum quota, this will fire from getSize(DocumentSnapshot document) if the size of the current document is near or about to hit the limit by the Firebase quota
     */
    private void displayWarningMessage(int docSize) {
        if(docSize >= HALF_MEBIBYTE && docSize < ONE_MEBIBYTE){
            Log.d(TAG, HALF_SIZE_MESSAGE);
        }
    }

    /**
     * @param collectionTask The task returned by a collection query. Eg: productsRef.collection("products").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents less than the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is less than the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsLessThan(Task<QuerySnapshot> collectionTask, int bytes){
        DocumentList documentList = new DocumentList();
        return documentList.getDocumentsListByQuery(collectionTask, bytes, LESS_THAN);
    }

    /**
     * @param collectionTask The task returned by a collection query. Eg: productsRef.collection("products").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents greater than the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is greater than the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsGreaterThan(Task<QuerySnapshot> collectionTask, int bytes){
        DocumentList documentList = new DocumentList();
        return documentList.getDocumentsListByQuery(collectionTask, bytes, GREATER_THAN);
    }

    /**
     * @param collectionTask The task returned by a collection query. Eg: productsRef.collection("products").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents less and equal than the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is less than equal to the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsLessEqualThan(Task<QuerySnapshot> collectionTask, int bytes){
        DocumentList documentList = new DocumentList();
        return documentList.getDocumentsListByQuery(collectionTask, bytes, LESS_THAN_OR_EQUAL_TO);
    }

    /**
     * @param collectionTask The task returned by a collection query. Eg: productsRef.collection("products").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents greater and equal than the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is greater than equal to the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsGreaterEqualThan(Task<QuerySnapshot> collectionTask, int bytes){
        DocumentList documentList = new DocumentList();
        return documentList.getDocumentsListByQuery(collectionTask, bytes, GREATER_THAN_OR_EQUAL_TO);
    }

    /**
     * @param collectionTask The task returned by a collection query. Eg: productsRef.collection("products").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents equal to the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is equal to the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsEqualTo(Task<QuerySnapshot> collectionTask, int bytes){
        DocumentList documentList = new DocumentList();
        return documentList.getDocumentsListByQuery(collectionTask, bytes, EQUAL_TO);
    }

    /**
     * @param document The document that we want to know if its less than 1 Mebibyte
     * @return True if the document is less than 1,048,576 bytes else false.
     */
    public boolean isDocumentSizeLessThanOneMebibyte(DocumentSnapshot document) {
        return getSize(document) < ONE_MEBIBYTE;
    }

    /**
     * @param document The document we want to convert its size to Kilobytes
     * @return size in Kilobytes
     */
    public double getDocumentSizeInKilobytes(DocumentSnapshot document) {
        return (double) getSize(document) / ONE_KILOBYTE;
    }

    public int getNumberOfProperties(DocumentSnapshot document) {
        return document.getData().size();
    }
}