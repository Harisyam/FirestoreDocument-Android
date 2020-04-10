package ro.alexmamo.firestoredocumentsize;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ro.alexmamo.firestore_document.FirestoreDocument;

@SuppressWarnings({"ConstantConditions"})
public class MainActivity extends AppCompatActivity {
    private TextView docSizeTextView;
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private DocumentReference myTaskIdRef = rootRef.collection("users").document("jeff").collection("tasks").document("my_task_id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDocSizeTextView();
        displayDocSize();
    }

    private void initDocSizeTextView() {
        docSizeTextView = findViewById(R.id.doc_size_text_view);
    }

    private void displayDocSize() {
        myTaskIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    FirestoreDocument firestoreDocument = FirestoreDocument.getInstance();
                    int documentSize = firestoreDocument.getSize(document);
                    String textToDisplay = "Size: " + documentSize+ "bytes";
                    docSizeTextView.setText(textToDisplay);
                }
            }
        });
    }
}