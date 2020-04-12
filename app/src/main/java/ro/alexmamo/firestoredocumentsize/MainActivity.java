package ro.alexmamo.firestoredocumentsize;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ro.alexmamo.firestore_document.FirestoreDocument;

import static ro.alexmamo.firestore_document.Constants.*;

@SuppressWarnings({"ConstantConditions"})
public class MainActivity extends AppCompatActivity {
    private TextView docSizeTextView;
    private FirestoreDocument firestoreDocument = FirestoreDocument.getInstance();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private DocumentReference myTaskIdRef = rootRef.collection("users").document("jeff").collection("tasks").document("my_task_id");
    private CollectionReference productsRef = rootRef.collection("products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDocSizeTextView();
        displayDocSize();
        getAllDocumentsLessThan(150);
    }

    private void initDocSizeTextView() {
        docSizeTextView = findViewById(R.id.doc_size_text_view);
    }

    private void displayDocSize() {
        myTaskIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    int documentSize = firestoreDocument.getSize(document);
                    String textToDisplay = "Size: " + documentSize + " bytes";
                    docSizeTextView.setText(textToDisplay);
                }
            }
        });
    }

    private void getAllDocumentsLessThan(int bytes) {
        productsRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<DocumentSnapshot> docSnapList = firestoreDocument.getDocumentsLessThan(task, bytes);
                Log.d(TAG, "docSnapList: " + docSnapList.size());
            }
        });
    }
}