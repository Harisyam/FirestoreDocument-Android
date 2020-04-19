package ro.alexmamo.firestore_document;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import ro.alexmamo.firestore_document.utils.Constants;

@SuppressWarnings({"ConstantConditions", "unchecked"})
class DocumentList {

  private FirestoreDocument firestoreDocument = FirestoreDocument.getInstance();

  List<DocumentSnapshot> getDocumentsListByQuery(Task<QuerySnapshot> collectionTask, int bytes, String equalityOperator) {
    List<DocumentSnapshot> docList = new ArrayList();
    switch (equalityOperator) {
      case Constants.EQUAL_TO:

        for (DocumentSnapshot document : collectionTask.getResult().getDocuments()) {
          if (firestoreDocument.getSize(document) == bytes) {
            docList.add(document);
          }
        }
        break;

      case Constants.GREATER_THAN_OR_EQUAL_TO:

        for (DocumentSnapshot document : collectionTask.getResult().getDocuments()) {
          if (firestoreDocument.getSize(document) >= bytes) {
            docList.add(document);
          }
        }
        break;

      case Constants.GREATER_THAN:

        for (DocumentSnapshot document : collectionTask.getResult().getDocuments()) {
          if (firestoreDocument.getSize(document) > bytes) {
            docList.add(document);
          }
        }
        break;

      case Constants.LESS_THAN:

        for (DocumentSnapshot document : collectionTask.getResult().getDocuments()) {
          if (firestoreDocument.getSize(document) < bytes) {
            docList.add(document);
          }
        }
        break;

      case Constants.LESS_THAN_OR_EQUAL_TO:
        for (DocumentSnapshot document : collectionTask.getResult().getDocuments()) {
          if (firestoreDocument.getSize(document) <= bytes) {
            docList.add(document);
          }
        }
        break;
    }
    return docList;
  }
}
