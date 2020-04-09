package ro.alexmamo.firestoredocumentsize;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ro.alexmamo.firestore_document.FirestoreDocument;


@SuppressWarnings({"ConstantConditions"})
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FirestoreDocumentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference myTaskIdRef = rootRef.collection("users").document("jeff").collection("tasks").document("my_task_id");
        myTaskIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    FirestoreDocument firestoreDocument = FirestoreDocument.getInstance();
                    int documentSize = firestoreDocument.getSize(document);
                    Log.d(TAG, "documentSize: " + documentSize);
                }
            }
        });
    }
}