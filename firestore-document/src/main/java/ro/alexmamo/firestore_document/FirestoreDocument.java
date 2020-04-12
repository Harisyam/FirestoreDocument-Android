package ro.alexmamo.firestore_document;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ro.alexmamo.firestore_document.Constants.ONE_KILOBYTE;
import static ro.alexmamo.firestore_document.Constants.ONE_MEBIBYTE;

@SuppressWarnings({"ConstantConditions", "unchecked"})
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
     * @param collectionTask The task returned by a collection query. Eg: myRef.collection("params").get().addOnCompleteListener( task -> {...} )
     * @param bytes The size of the documents we want to fetch in this query.
     * @return List of documentSnapshot containing documents less than the input bytes given.
     *
     * Important: This query does not save reads of documents, it filters documents which size is less than the bytes given.
     */
    public List<DocumentSnapshot> getDocumentsLessThan(Task<QuerySnapshot> collectionTask, int bytes){
        List<DocumentSnapshot> docList = new ArrayList();
        for(DocumentSnapshot document : collectionTask.getResult().getDocuments()){
            if(getSize(document) < bytes){
                docList.add(document);
            }
        }
        return docList;
    }

    /**
     * @param document The document that we want to know if its less than 1Mebibyte
     * @return True if the document is less than 1048576 bytes else false.
     */
    public boolean isDocumentSizeLessThanOneMebibyte(DocumentSnapshot document) {
        return getSize(document) < ONE_MEBIBYTE;
    }

    /**
     * @param document The document we want to convert its size to Kbytes
     * @return size in Kilobytes
     */
    public double getDocumentSizeInKilobytes(DocumentSnapshot document) {
        return (double) getSize(document) / ONE_KILOBYTE;
    }

    public int getNumberOfProperties(DocumentSnapshot document) {
        return document.getData().size();
    }
}